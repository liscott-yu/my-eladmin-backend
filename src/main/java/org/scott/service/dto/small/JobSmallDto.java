package org.scott.service.dto.small;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * project name  my-eladmin-backend-v2
 * filename  JobSmallDto
 *
 * @author liscott
 * @date 2023/1/4 15:52
 * description  TODO
 */
@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {
    private Long id;
    private String name;
}
