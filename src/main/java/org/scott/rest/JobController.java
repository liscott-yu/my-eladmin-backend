package org.scott.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.scott.service.JobService;
import org.scott.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobService
 * @author liscott
 * @date 2023/1/9 16:20
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：岗位管理")
@RequestMapping(value = "/api/job")
public class JobController {
    private final JobService jobService;

    @ApiOperation("查询岗位")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('job:list', 'user:list', 'admin')")
    public ResponseEntity<Object> queryJob(JobQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(jobService.queryAll(criteria, pageable), HttpStatus.OK);
    }
}
