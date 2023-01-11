package org.scott.config;

import lombok.RequiredArgsConstructor;
import org.scott.config.jwt.TokenConfigurer;
import org.scott.config.jwt.TokenProvider;
import org.scott.service.DeptService;
import org.scott.service.impl.OnlineUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * project name  my-eladmin-backend-v2
 * filename  SecurityConfig
 * @author liscott
 * @date 2023/1/4 16:20
 * description //开启了这个，才能在方法上使用权限控制注解
 * @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
 * 由于非简单请求都要首先发送一个预检请求(request),而预检请求并不会携带认证信息，所以预检请求就有被Spring Security拦截的可能。
 * 因此通过@CrossOrigin注解或者重写addCorsMappings方法配置跨域就会失效。
 * 如果使用CorsFilter配置的跨域，只要过滤器优先级高于SpringSecurity过滤器就不会有问题，反之同样会出现问题。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;
    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/code").permitAll()
                // 放行OPTIONS请求，放行了才能把status放到data里面
                .antMatchers(HttpMethod.OPTIONS, "/*").permitAll()
                .anyRequest().authenticated()
                // cors 过滤器
                .and().cors().configurationSource(corsConfigurationSource())
                // 不创建会话
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(securityConfigurerAdapter())
                .and().csrf().disable();
    }

    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(tokenProvider, securityProperties, onlineUserService);
    }

    /**
     *  * 由于非简单请求都要首先发送一个预检请求(request),而预检请求并不会携带认证信息，
     *  所以预检请求就有被Spring Security拦截的可能。
     *  * 因此通过@CrossOrigin注解或者重写addCorsMappings方法配置跨域就会失效。
     *  * 如果使用CorsFilter配置的跨域，只要过滤器优先级高于SpringSecurity过滤器就不会有问题，反之同样会出现问题。
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


}
