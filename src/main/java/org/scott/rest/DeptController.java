package org.scott.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.service.DeptService;
import org.scott.service.dto.DeptDto;
import org.scott.service.dto.DeptQueryCriteria;
import org.scott.utils.PageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptController
 * @author liscott
 * @date 2023/1/9 16:13
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：部门管理")
@RequestMapping(value = "/api/dept")
public class DeptController {
    private final DeptService deptService;

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:list','dept:list','admin')")
    public ResponseEntity<Object> queryDept(DeptQueryCriteria criteria) throws  Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtils.toPage(deptDtos, deptDtos.size()), HttpStatus.OK);
    }
}
