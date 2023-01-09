package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Job;
import org.scott.repository.JobRepository;
import org.scott.service.JobService;
import org.scott.service.dto.JobQueryCriteria;
import org.scott.service.mapstruct.JobMapper;
import org.scott.utils.PageUtils;
import org.scott.utils.QueryHelp;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Map;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobServiceImpl
 *
 * @author liscott
 * @date 2023/1/9 15:19
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;


    @Override
    public Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(((root, query, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder)), (org.springframework.data.domain.Pageable) pageable);
        return PageUtils.toPage(page.map(jobMapper::toDto).getContent(), page.getTotalElements());
    }
}
