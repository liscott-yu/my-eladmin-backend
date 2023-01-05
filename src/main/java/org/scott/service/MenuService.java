package org.scott.service;

import org.scott.domain.Menu;
import org.scott.service.dto.MenuDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuService
 * @author liscott
 * @date 2023/1/5 13:46
 * description  TODO
 */
@Service
public interface MenuService {

    /**
     * 构建菜单
     * @param menuDtos List
     * @return Object
     */
    Object buildMenus(List<MenuDto> menuDtos);

    /**
     * 构建菜单树
     * @param menuDtos List 原始数据
     * @return List
     */
    List<MenuDto> buildTree(List<MenuDto> menuDtos);

    /**
     *  根据当前用户获取菜单
     * @param currentUserId 当前用户Id
     * @return List
     */
    List<MenuDto> findByUser(Long currentUserId);
}
