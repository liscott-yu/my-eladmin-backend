package org.scott.utils;

import cn.hutool.json.JSONObject;
import org.scott.exception.BadRequestException;
import org.scott.stringConstant.StringConstant;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * project name  my-eladmin-backend-v2
 * filename  SecurityUtils
 *
 * @author liscott
 * @date 2023/1/5 11:32
 * description  获取当前用户信息
 */
public class SecurityUtils {
    /**
     * 获取系统名称
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    private static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, StringConstant.MSG_LOGIN_EXPIRED);
        }
        if(authentication.getPrincipal() instanceof  UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, StringConstant.MSG_NOT_LOGIN_INFO);
    }
}
