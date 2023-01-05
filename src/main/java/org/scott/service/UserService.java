package org.scott.service;

import org.scott.service.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserService
 * @author liscott
 * @date 2023/1/4 18:02
 * description  TODO
 */
@Service
public interface UserService {
    /**
     * 根据用户名查询UserDto
     * @param userName 用户名
     * @return UserDto
     */
    UserDto findByName(String userName);
}
