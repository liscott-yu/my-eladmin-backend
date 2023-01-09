package org.scott.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.scott.exception.BadRequestException;
import org.scott.stringConstant.StringConstant;
import org.scott.utils.enums.DataScopeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  SecurityUtils
 * @author liscott
 * @date 2023/1/5 11:32
 * description  获取当前用户信息
 */
public class SecurityUtils {
    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean (UserDetailsService.class);;
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, StringConstant.MSG_LOGIN_EXPIRED);
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, StringConstant.MSG_NOT_LOGIN_INFO);
    }

    /**
     * 获取系统用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();

        JSONObject jsonObject = new JSONObject(userDetails);
        // user
        Object user = jsonObject.get("userDto");
        JSONObject jsonObject1 = new JSONObject(user);
        return jsonObject1.get("id", Long.class);

//        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    /**
     * 获取当前用户的数据权限
     * @return List
     */
    public static List<Long> getCurrentUserDataScope() {
        // userDetails -> Object -> JSONArray -> ArrayList
        UserDetails userDetails = getCurrentUser();
        Object dataScopes = new JSONObject(userDetails).get("dataScopes");
        JSONArray array = JSONUtil.parseArray(dataScopes);
        return JSONUtil.toList(array, Long.class);
    }

    /**
     * 获取数据权限级别
     * @return 数据权限级别
     */
    public static String getDataScopeType() {
        List<Long> dataScope = getCurrentUserDataScope();
        ////dataScopes里面的每个数字代表能看到的部门，如果为空，则代表可以看到所有部门数据
        if(!CollUtil.isEmpty(dataScope)){
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }
 }
