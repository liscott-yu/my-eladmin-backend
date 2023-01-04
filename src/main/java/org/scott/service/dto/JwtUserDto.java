package org.scott.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * project name  my-eladmin-backend-v2
 * filename  JwtUserDto
 *
 * @author liscott
 * @date 2023/1/4 16:33
 * description  认证的主体
 */
@Getter
@AllArgsConstructor
public class JwtUserDto implements UserDetails {
    private final UserDto userDto;
    private final List<Long> dataScope;
    @JsonIgnore
    private final List<GrantedAuthority> authorities;

    public Set<String> getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
