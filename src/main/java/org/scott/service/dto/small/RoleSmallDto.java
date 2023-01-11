package org.scott.service.dto.small;

import lombok.Data;

import java.io.Serializable;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleSmallDto
 *
 * @author liscott
 * @date 2023/1/4 15:52
 * description  TODO
 */
@Data
public class RoleSmallDto implements Serializable {
    private Long id;
    private String name;
    /** 权限等级 */
    private Integer level;
    /** 权限 */
    private String dataScope;
}
