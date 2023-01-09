package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Dept;
import org.scott.repository.DeptRepository;
import org.scott.service.DeptService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptServiceImpl
 * @author liscott
 * @date 2023/1/6 15:51
 * description  TODO
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dept")
public class DeptServiceImpl implements DeptService {
    private final DeptRepository deptRepository;

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()) {
                        List<Dept> depts = deptRepository.findByPid(dept.getId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
