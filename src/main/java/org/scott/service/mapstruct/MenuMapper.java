package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Menu;
import org.scott.service.dto.MenuDto;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuMapper
 * @author liscott
 * @date 2023/1/5 14:03
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {

}
