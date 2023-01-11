package org.scott.config.jwt;

import lombok.RequiredArgsConstructor;
import org.scott.config.SecurityProperties;
import org.scott.service.DeptService;
import org.scott.service.impl.OnlineUserService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * project name  my-eladmin-backend-v2
 * filename  TokenConfigurer
 * @author liscott
 * @date 2023/1/11 11:40
 * description  TODO
 */
@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        TokenFilter customFilter = new TokenFilter(tokenProvider, properties, onlineUserService);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
