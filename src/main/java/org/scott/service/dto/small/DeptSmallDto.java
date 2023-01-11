package org.scott.service.dto.small;

import lombok.Data;

import java.io.Serializable;

/**
 * project name  my-eladmin-backend-v2
 * filename  DeptSmallDto
 * @author liscott
 * @date 2023/1/4 15:52
 * description  TODO
 */
@Data
public class DeptSmallDto implements Serializable {
    private Long id;
    private String name;
}
