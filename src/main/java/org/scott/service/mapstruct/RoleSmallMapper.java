package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Role;
import org.scott.service.dto.small.RoleSmallDto;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleSmallMapper
 * @author liscott
 * @date 2023/1/5 14:37
 * description  TODO
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, Role> {
}
