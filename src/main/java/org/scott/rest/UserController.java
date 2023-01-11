package org.scott.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.domain.User;
import org.scott.exception.BadRequestException;
import org.scott.service.RoleService;
import org.scott.service.UserService;
import org.scott.service.dto.UserQueryCriteria;
import org.scott.service.dto.small.RoleSmallDto;
import org.scott.stringConstant.StringConstant;
import org.scott.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserController
 * @author liscott
 * @date 2023/1/6 13:21
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> queryUser(UserQueryCriteria userQueryCriteria, Pageable pageable){
        return new ResponseEntity<>(userService.queryAll(userQueryCriteria ,pageable), HttpStatus.OK);
    }

    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> createUser(@Validated @RequestBody User resources) {
        checkLevel(resources);
        // 设置新用户的默认密码为：123456
        String password = resources.getPassword() == null ? StringConstant.DEFAULT_PASSWORD : resources.getPassword();
        resources.setPassword(passwordEncoder.encode(password));
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> updateUser(@Validated (User.Update.class) @RequestBody User resources) throws Exception {

        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> deleteUser(@RequestBody Set<Long> ids) {
        // userId -> roleSmallDto -> level 当前用户角色等级
        Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId())
                .stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        for (Long id : ids){
            // 根据 id 校验 当前用户角色级别 是否 高于 要删除的用户的角色
            checkLevelById(id, currentLevel);
        }
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param id ID
     */
    private void checkLevelById(Long id, Integer currentLevel) {
        Integer optLevel = Collections.min(roleService.findByUsersId(id).stream()
                .map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        if(optLevel < currentLevel) {
            throw new BadRequestException(StringConstant.MSG_INSUFFICIENT_AUTHORITY + ",无法删除： " + userService.findById(id).getUsername());
        }
    }


    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources User
     */
    private void checkLevel(User resources) {
        // 当前登录用户的角色级别
        Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId())
                .stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        // 新用户的角色级别
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        // level 1 是 最高级别，数字越大级别越低
        if(currentLevel > optLevel){
            throw new BadRequestException(StringConstant.MSG_INSUFFICIENT_AUTHORITY + ",无法删除： " + resources.getUsername());
        }
    }

}
