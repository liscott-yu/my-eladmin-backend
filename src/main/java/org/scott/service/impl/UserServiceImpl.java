package org.scott.service.impl;

import org.scott.domain.User;
import org.scott.repository.UserRepository;
import org.scott.service.UserService;
import org.scott.service.dto.UserDto;
import org.scott.service.mapstruct.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserServiceImpl
 * @author liscott
 * @date 2023/1/5 10:04
 * description  TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto findByName(String username) {
        User user = userRepository.findByUsername(username);
        return userMapper.toDto(user);
    }
}
