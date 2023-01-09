package org.scott.repository;

import org.scott.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobRepository
 * @author liscott
 * @date 2023/1/9 13:46
 * description  TODO
 */
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

}
