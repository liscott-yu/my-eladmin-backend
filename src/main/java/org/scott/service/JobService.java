package org.scott.service;

import org.scott.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobService
 * @author liscott
 * @date 2023/1/9 14:13
 * description  TODO
 */
public interface JobService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable);
}
