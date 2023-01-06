package org.scott.service;

import org.scott.service.dto.UserDto;
import org.scott.service.dto.small.RoleSmallDto;
import org.springframework.security.core.GrantedAuthority;
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

    /**
     * 获取用户权限信息列表
     * @param userDto 用户信息
     * @return 权限信息列表
     */
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto userDto);
}
