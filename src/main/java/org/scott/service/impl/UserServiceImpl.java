package org.scott.service.impl;

import cn.hutool.core.util.PageUtil;
import org.scott.domain.User;
import org.scott.repository.UserRepository;
import org.scott.service.UserService;
import org.scott.service.dto.UserDto;
import org.scott.service.mapstruct.UserMapper;
import org.scott.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Object queryAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return PageUtils.toPage(page.map(userMapper::toDto));
    }
}
