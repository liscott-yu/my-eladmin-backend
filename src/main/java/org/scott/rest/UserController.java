package org.scott.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.service.UserService;
import org.scott.service.dto.UserQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserController
 * @author liscott
 * @date 2023/1/6 13:21
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:list','admin')")
    public ResponseEntity<Object> queryUser(UserQueryCriteria userQueryCriteria, Pageable pageable){
        return new ResponseEntity<>(userService.queryAll(userQueryCriteria ,pageable), HttpStatus.OK);
    }

}
