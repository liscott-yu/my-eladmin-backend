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
        Long id = SecurityUtils.getCurrentUserId();
        List<MenuDto> menuDtoList = menuService.findByUser(id);
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        return new ResponseEntity<>(menuService.buildMenus(menuDtos),HttpStatus.OK);
    }

}
