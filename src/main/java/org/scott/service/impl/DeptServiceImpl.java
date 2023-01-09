package org.scott.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.domain.Dept;
import org.scott.repository.DeptRepository;
import org.scott.service.DeptService;
import org.scott.service.dto.DeptDto;
import org.scott.service.dto.DeptQueryCriteria;
import org.scott.service.mapstruct.DeptMapper;
import org.scott.utils.QueryHelp;
import org.scott.utils.SecurityUtils;
import org.scott.utils.enums.DataScopeEnum;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptServiceImpl
 * @author liscott
 * @date 2023/1/6 15:51
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {
    private final DeptRepository deptRepository;
    private final DeptMapper deptMapper;

    @Override
    public List<DeptDto> queryAll(DeptQueryCriteria criteria, Boolean isQuery) throws Exception {
        Sort sort = Sort.by(Sort.Direction.ASC, "deptSort");
        String dataScopeType = SecurityUtils.getDataScopeType();
        // 若是查询
        if(isQuery) {
            // 若数据权限为 ALL， 设置 pid 为 空
            if(dataScopeType.equals(DataScopeEnum.ALL.getValue())) {
                criteria.setPidIsNull(true);
            }
            List<Field> fields = Arrays.asList(criteria.getClass().getDeclaredFields());
            List<String> fieldNames = new ArrayList<String>(){{
                add("pidIsNull");
                add("enabled");
            }};
            // 遍历所有属性
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if (fieldNames.contains(field.getName())) {
                    continue;
                }
                // 若属性的条件不为空
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        List<Dept> deptList = deptRepository.findAll((root, query, criteriaBuilder) ->
                QueryHelp.getPredicate(root,criteria,criteriaBuilder), sort);
        List<DeptDto> list = deptMapper.toDto(deptList);
        //如果为空，就代表为自定义权限或者本级权限，就需要去重，不理解可以注释掉，看查询结果
        if(StringUtils.isBlank(dataScopeType)) {
            return deduplication(list);
        }
        return list;
    }

    /**
     * 去重，去掉所有子部门，留下最顶级的部门
     * @param list 部门列表
     * @return 去重后的部门列表
     */
    private List<DeptDto> deduplication(List<DeptDto> list) {
        ArrayList<DeptDto> deptDtos = new ArrayList<>();
        // 遍历部门列表
        for ( DeptDto deptDto : list) {
            boolean flag = true;
            // 再次遍历部门列表
            for ( DeptDto dto : list) {
                // 若 deptDto 有 父部门，标记flag为false
                if(dto.getId().equals(deptDto.getPid())){
                    flag = false;
                    break;
                }
            }
            if(flag) {
                // 无父部门，添加到 部门列表中
                deptDtos.add(deptDto);
            }
        }
        return deptDtos;
    }

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()) {
                        List<Dept> depts = deptRepository.findByPid(dept.getId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
