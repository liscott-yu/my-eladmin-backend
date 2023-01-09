package org.scott.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.scott.annotation.Query;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobQueryCriteria
 * @author liscott
 * @date 2023/1/9 14:08
 * description  TODO
 */
@Data
@NoArgsConstructor
public class JobQueryCriteria {
    @Query
    private Boolean enabled;
}
