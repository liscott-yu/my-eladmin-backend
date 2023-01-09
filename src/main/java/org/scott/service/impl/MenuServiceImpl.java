package org.scott.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import org.scott.domain.Menu;
import org.scott.domain.vo.MenuMetaVo;
import org.scott.domain.vo.MenuVo;
import org.scott.repository.MenuRepository;
import org.scott.service.MenuService;
import org.scott.service.RoleService;
import org.scott.service.dto.MenuDto;
import org.scott.service.dto.small.RoleSmallDto;
import org.scott.service.mapstruct.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liscott
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RoleService roleService;


    /**
     * 获取 当前用户 能看到的菜单列表
     * @param currentUserId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<MenuDto> findByUser(Long currentUserId) {
        // 获取当前用户的 角色列表
        List<RoleSmallDto> roles = roleService.findByUsersId(currentUserId);
        // 获取 用户id 集合
        Set<Long> roleIds = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        //根据角色查询能看到的菜单，并且菜单类型不能为2,类型为2的菜单是具体权限或者说是按钮，如用户新增、编辑、删除等；
        LinkedHashSet<Menu> menus = menuRepository.findByRoleIdsAndTypeNot(roleIds, 2);
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    /**
     * 构建菜单树
     * @param menuDtos 用户能看到的 菜单集合
     * @return 菜单列表
     */
    @Override
    public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
        // 顶层菜单列表
        List<MenuDto> trees = new ArrayList<>();
        // 子菜单id的集合， 不重复
        Set<Long> ids = new HashSet<>();
        // 遍历用户能看到的每个菜单
        for (MenuDto menuDTO : menuDtos) {
            // 若是顶级菜单，添加进 trees
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            //次遍历用户能看到的每个菜单
            for (MenuDto it : menuDtos) {
                // 找出 menuDTO 的下级菜单 it
                if (menuDTO.getId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        // 若 该用户 无权限
        if(trees.size() == 0){
            //在用户能看到的菜单集合里面筛选出所有非子菜单，意思就是一级菜单没有，就显示二级菜单，以此类推
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    /**
     * 完成从menuDTO到menuVO的转化
     * @param menuDtos 已经是菜单树
     * @return 菜单
     */
    @Override
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> trees = new LinkedList<>();
        // 遍历 菜单树
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        // 设置 菜单视图
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() : menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        menuVo.setMeta( new MenuMetaVo(menuDTO.getTitle(), !menuDTO.getCache(), menuDTO.getIcon()));
                        //获取子菜单
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        if (CollectionUtil.isNotEmpty(menuDtoList)) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                        }
                        // 添加 menuVo 到 菜单树
                        trees.add(menuVo);
                    }
                }
        );
        return trees;
    }
}
