package org.scott.service;

import org.scott.service.dto.small.RoleSmallDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleService
 *
 * @author liscott
 * @date 2023/1/5 14:24
 * description  TODO
 */
@Service
public interface RoleService {
    /**
     * 根据用户ID查询 角色列表
     * @param id 用户ID
     * @return /
     */
    List<RoleSmallDto> findByUsersId(Long id);
}
