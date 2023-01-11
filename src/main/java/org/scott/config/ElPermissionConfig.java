package org.scott.config;

import org.scott.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * project name  my-eladmin-backend-v2
 * filename  ElpermissionConfig
 * @author liscott
 * @date 2023/1/11 13:35
 * description  权限检查
 */
@Service(value = "el")
public class ElPermissionConfig {
    public Boolean check(String ...permissions) {
        // 获取当前用户的所有权限
        List<String> permissionList = SecurityUtils.getCurrentUser().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断 当前用户的所有权限 是否 包含接口上定义的权限
        return permissionList.contains("admin") || Arrays.stream(permissions).anyMatch(permissionList::contains);
    }
}
