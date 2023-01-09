package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Role;
import org.scott.service.dto.RoleDto;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleMapper
 * @author liscott
 * @date 2023/1/9 14:27
 * description  TODO
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class,DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {
}
