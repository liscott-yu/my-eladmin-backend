package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Job;
import org.scott.service.dto.JobDto;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobMapper
 *
 * @author liscott
 * @date 2023/1/9 14:26
 * description  TODO
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<JobDto, Job> {
}
