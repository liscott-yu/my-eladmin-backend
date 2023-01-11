package org.scott.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.scott.config.SecurityProperties;
import org.scott.service.dto.JwtUserDto;
import org.scott.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * project name  my-eladmin-backend-v2
 * filename  OnlineUserService
 * @author liscott
 * @date 2023/1/11 13:55
 * description  TODO
 */
@Slf4j
@Service
public class OnlineUserService {
    private final SecurityProperties securityProperties;
    private final RedisUtils redisUtils;

    public OnlineUserService(SecurityProperties securityProperties, RedisUtils redisUtils) {
        this.securityProperties = securityProperties;
        this.redisUtils = redisUtils;
    }

    /**
     * 保存在线用户信息
     * @param jwtUserDto 在线用户
     * @param token token
     * @param request request
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {
        redisUtils.set(securityProperties.getOnlineKey() + token, jwtUserDto,
                securityProperties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 查询用户
     * @param key /
     * @return /
     */
    public JwtUserDto getOne(String key) {
        return (JwtUserDto) redisUtils.get(key);
    }
}
