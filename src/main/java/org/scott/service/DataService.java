package org.scott.service;

import org.scott.service.dto.UserDto;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  DataService
 * @author liscott
 * @date 2023/1/6 16:12
 * description  数据权限 服务类
 */
public interface DataService {
    /**
     * 根据 UserDto 获取 用户数据权限
     * @param userDto 用户
     * @return 数据权限
     */
    List<Long> getDeptIds(UserDto userDto);
}
