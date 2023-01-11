package org.scott.service;

import org.scott.domain.User;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.UserQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import java.util.Set;

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

    /**
     * 根据分页信息查询所有用户
     * @param userQueryCriteria 用户查询条件
     * @param pageable 分页信息
     * @return Object
     */
    Object queryAll(UserQueryCriteria userQueryCriteria, Pageable pageable);

    /**
     * 新增用户
     * @param resources 用户
     */
    void create(User resources);

    /**
     * 修改用户
     * @param resources user
     * @throws Exception exception
     */
    void update(User resources) throws Exception;

    /**
     * 根据 id 查询 UserDto
     * @param id ID
     * @return UserDto
     */
    UserDto findById(Long id);

    /**
     * 根据 id集合 删除用户
     * @param ids id集合
     */
    void delete(Set<Long> ids);
}
