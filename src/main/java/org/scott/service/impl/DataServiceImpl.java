package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Dept;
import org.scott.service.DataService;
import org.scott.service.DeptService;
import org.scott.service.RoleService;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.small.RoleSmallDto;
import org.scott.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * project name  my-eladmin-backend-v2
 * filename  DetaServiceImpl
 * @author liscott
 * @date 2023/1/6 16:16
 * description  数据权限服务实现
 */
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final RoleService roleService;
    private final DeptService deptService;

    /**
     * 查询 部门id 列表
     * @param userDto 用户
     * @return  部门id List
     */
    @Override
    public List<Long> getDeptIds(UserDto userDto) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 查询用户角色
        List<RoleSmallDto> roleSet = roleService.findByUsersId(userDto.getId());
        // 获取对应的部门ID
        for (RoleSmallDto roleSmallDto : roleSet) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(roleSmallDto.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(userDto.getDept().getId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, roleSmallDto));
                    break;
                default:
                    return new ArrayList<>(deptIds);
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获取自定义的数据权限，也就是获取sys_roles_depts表里面配置的记录
     * @param deptIds 部门ID
     * @param role 角色
     * @return 数据权限ID集合
     */
    public Set<Long> getCustomize(Set<Long> deptIds, RoleSmallDto role){
        // 根据 sys_roles_depts 表 获取当前用户角色对应的 部门id
        Set<Dept> depts = deptService.findByRoleId(role.getId());
        for (Dept dept : depts) {
            deptIds.add(dept.getId());
            //若这个部门有下级部门，则获取它的下级部门
            //这样数据库表sys_roles_depts,只需配最高级部门的id,即可看到它下面的所有部门
            List<Dept> deptChildren = deptService.findByPid(dept.getId());
            if (deptChildren != null && deptChildren.size() != 0) {
                deptIds.addAll(deptService.getDeptChildren(deptChildren));
            }
        }
        return deptIds;
    }
}
