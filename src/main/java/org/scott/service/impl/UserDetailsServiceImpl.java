package org.scott.service.impl;

import org.scott.service.UserService;
import org.scott.service.dto.JwtUserDto;
import org.scott.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserDetailsServiceImpl
 * @author liscott
 * @date 2023/1/4 16:28
 * description  TODO
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.findByName(username);

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("Admin");
        JwtUserDto jwtUserDto = new JwtUserDto(
                userDto,
                null,
                authorities
        );
        return jwtUserDto;
    }
}
