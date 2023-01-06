package org.scott.repository;

import org.scott.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserRepository
 * @author liscott
 * @date 2023/1/4 17:50
 * description  User, Long前者代表要查哪个表，后者代表id的类型
 * //继承JpaRepository之后，会默认使用SimpleJpaRepository这个实现类作为UserRepository接口的实体bean
 * //继承JpaSpecificationExecutor是为了实现动态查询
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查询User
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

}
