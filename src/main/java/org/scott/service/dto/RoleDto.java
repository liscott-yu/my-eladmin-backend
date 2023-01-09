package org.scott.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.scott.base.BaseDTO;

import javax.persistence.SecondaryTable;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleDto
 * @author liscott
 * @date 2023/1/9 13:53
 * description  TODO
 */
@Setter
@Getter
public class RoleDto extends BaseDTO implements Serializable {
    private Long id;
    private Set<MenuDto> menus;
    private Set<DeptDto> depts;
    private String name;
    private String dataScope;
    private Integer level;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
