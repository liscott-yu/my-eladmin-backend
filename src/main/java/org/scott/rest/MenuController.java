package org.scott.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.service.MenuService;
import org.scott.service.dto.MenuDto;
import org.scott.service.mapstruct.MenuMapper;
import org.scott.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuController
 * @author liscott
 * @date 2023/1/5 13:42
 * description  TODO
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单")
    public ResponseEntity<Object> buildMenus(){
        // 获取 当前用户的id
        Long id = SecurityUtils.getCurrentUserId();
        // 获取 当前用户 能看到的 菜单列表，非树形列表
        List<MenuDto> menuDtoList = menuService.findByUser(id);
        // 把 获取的菜单列表 转化成 树形菜单列表
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        // 把 DTO 转化为 VO
        return new ResponseEntity<>(menuService.buildMenus(menuDtos),HttpStatus.OK);
    }

}
