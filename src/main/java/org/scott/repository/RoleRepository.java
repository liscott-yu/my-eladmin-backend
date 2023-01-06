package org.scott.repository;

import org.scott.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleRepository
 * @author liscott
 * @date 2023/1/5 14:32
 * description  TODO
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    /**
     * 根据用户ID查询角色集合
     * @param id 用户ID
     * @return role集合
     */
    @Query(value = "SELECT r.* " +
            "FROM sys_role r, sys_users_roles ur " +
            "WHERE r.role_id = ur.role_id AND ur.user_id = ?1",nativeQuery = true)
    Set<Role> findByUserId(Long id);
}
