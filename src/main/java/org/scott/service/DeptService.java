package org.scott.service;

import org.scott.domain.Dept;

import java.util.List;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptService
 * @author liscott
 * @date 2023/1/6 15:46
 * description  TODO
 */
public interface DeptService {
    /**
     * 根据PID查询 部门列表
     * @param pid pid
     * @return 部门列表
     */
    List<Dept> findByPid(long pid);

    /**
     * 根据角色ID查询 部门集合
     * @param id  角色id
     * @return 部门集合
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 获取下级部门列表
     * @param deptList 部门集合
     * @return List
     */
    List<Long> getDeptChildren(List<Dept> deptList);
}
