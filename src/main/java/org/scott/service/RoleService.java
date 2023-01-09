package org.scott.service;

import org.scott.domain.Role;
import org.scott.service.dto.RoleDto;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.small.RoleSmallDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleService
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

    /**
     * 根据 角色集合 查询 角色级别
     * @param roles 角色集合
     * @return 角色级别
     */
    Integer findByRoles(Set<Role> roles);

    /**
     * 根据ID查询角色
     * @param id ID
     * @return 角色 roleDto
     */
    RoleDto findById(Long id);

    /**
     * 查询全部角色列表
     * @return 角色列表
     */
    List<RoleDto> queryAll();
}
