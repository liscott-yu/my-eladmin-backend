package org.scott.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleController
 * @author liscott
 * @date 2023/1/9 16:29
 * description  TODO
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/roles")
@Api(tags = "系统：角色管理")
public class RoleController {
    private final RoleService roleService;

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('roles:list','user:add','user:edit','admin')")
    public ResponseEntity<Object> queryAllRole() {
        return new ResponseEntity<>(roleService.queryAll(), HttpStatus.OK);
    }
}
