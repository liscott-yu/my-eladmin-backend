package org.scott.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.scott.base.BaseDTO;

import java.io.Serializable;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobDto
 * @author liscott
 * @date 2023/1/9 14:09
 * description  TODO
 */
@Setter
@Getter
@NoArgsConstructor
public class JobDto extends BaseDTO implements Serializable {
    private Long id;
    private Integer JobSort;
    private String name;
    private Boolean enabled;

    public JobDto(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
