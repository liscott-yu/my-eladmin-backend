package org.scott.service.dto.impl;

import org.scott.service.dto.JwtUserDto;
import org.scott.service.dto.UserDto;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!"admin".equalsIgnoreCase(username)){
            throw new UsernameNotFoundException("");
        }
        UserDto user = new UserDto();
        user.setEnabled(true);
        user.setNickName("scott");
        user.setPassword("$2a$10$.a.DezMXYcnDOKxlL7JKDuRxt9VrZLyJHdjGHklNY6mo3FcTRY/GW");

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("Admin");
        JwtUserDto jwtUserDto = new JwtUserDto(
                user,
                null,
                authorities
        );
        return jwtUserDto;
    }
}
