package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.domain.Menu;
import org.scott.domain.Role;
import org.scott.repository.RoleRepository;
import org.scott.service.RoleService;
import org.scott.service.dto.RoleDto;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.small.RoleSmallDto;
import org.scott.service.mapstruct.RoleMapper;
import org.scott.service.mapstruct.RoleSmallMapper;
import org.scott.utils.ValidationUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleServiceImpl
 * @author liscott
 * @date 2023/1/5 14:26
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleSmallMapper roleSmallMapper;


    @Override
    public List<RoleSmallDto> findByUsersId(Long id) {
        Set<Role> roleSet = roleRepository.findByUserId(id);
        List<Role> roleList = new ArrayList<>(roleSet);
        return roleSmallMapper.toDto(roleList);
    }

    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto userDto) {
        Set<String> permissions = new HashSet<>();
        // 若是 admin 直接返回
        if(userDto.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        Set<Role> roleSet = roleRepository.findByUserId(userDto.getId());
        /**
         * roles.stream(): role的流 stream(role role ...)
         * .flatMap: menu的流 stream(role.getMenus()...) -> stream(menu menu ...)
         * .filter: permission的流 steam(menu.getPermission()...) -> stream(permission...)
         *  /map跟前端原理一样，把一个流变成另外一个流。
         */
        permissions = roleSet.stream()
                .flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission)
                .collect(Collectors.toSet());
        // List (string -> SimpleGrantedAuthority)
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Integer findByRoles(Set<Role> roles) {
        if (roles.size() == 0) {
            return Integer.MAX_VALUE;
        }
        // Role -> RoleDto -> Integer
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            roleDtos.add(findById(role.getId()));
        }
        return Collections.min(
                roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseGet(Role::new);
        ValidationUtils.isNull(role.getId(), "Role","id", id);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> queryAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "level");
        return roleMapper.toDto(roleRepository.findAll(sort));
    }


}
