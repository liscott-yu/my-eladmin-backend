package org.scott.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * project name  my-eladmin-backend-v2
 * filename  SecurityConfig
 * @author liscott
 * @date 2023/1/4 16:20
 * description  TODO
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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
                .and().csrf().disable();
    }
}
