package org.scott.service.dto;

import lombok.Data;
import org.scott.annotation.DataPermission;
import org.scott.annotation.Query;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptQueryCriteria
 * @author liscott
 * @date 2023/1/9 14:04
 * description  TODO
 */
@Data
@DataPermission(fieldName = "id")
public class DeptQueryCriteria {
    @Query
    private Boolean enabled;
    @Query
    private Long pid;

    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;
}
