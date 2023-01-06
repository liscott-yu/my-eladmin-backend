package org.scott.repository;

import org.scott.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuRepository
 * @author liscott
 * @date 2023/1/5 14:07
 * description  TODO
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    /**
     * 根据角色ID和菜单类型查询菜单
     * @param roleIds 角色ID
     * @param type 菜单类型
     * @return 菜单 LinkedHashSet
     */
    @Query(value = "SELECT m.* " +
            "FROM sys_menu m, sys_roles_menus rm " +
            "WHERE m.menu_id = rm.menu_id AND rm.role_id IN ?1 AND type != ?2 " +
            "ORDER BY m.menu_sort ASC", nativeQuery = true)
    LinkedHashSet<Menu> findByRoleIdsAndTypeNot(Set<Long> roleIds, int type);
}
