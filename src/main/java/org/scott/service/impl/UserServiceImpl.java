package org.scott.service.impl;

import org.scott.domain.User;
import org.scott.exception.EntityExistException;
import org.scott.repository.UserRepository;
import org.scott.service.UserService;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.UserQueryCriteria;
import org.scott.service.mapstruct.UserMapper;
import org.scott.utils.PageUtils;
import org.scott.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Object queryAll(UserQueryCriteria userQueryCriteria, Pageable pageable) {
        //这里findall方法的第一个参数是Specification，Specification是一个函数式接口，因为他只有一个抽象方法:toPredicate()，所以可以用lambda表达式去表示函数式接口
        Page<User> page = userRepository.findAll(
                (root, query, cb) -> QueryHelp.getPredicate(root, userQueryCriteria, cb), pageable);
        //以上代码等价于以下代码：
//        Page<User> page = userRepository.findAll(new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return QueryHelp.getPredicate(root, query, criteriaBuilder);
//            }
//        }, pageable);
        return PageUtils.toPage(page.map(userMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if(userRepository.findByUsername(resources.getUsername()) != null){
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        if (userRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        if (userRepository.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }
        userRepository.save(resources);
    }
}
