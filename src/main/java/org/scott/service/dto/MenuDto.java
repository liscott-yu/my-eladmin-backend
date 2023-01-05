package org.scott.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.scott.base.BaseDTO;
import org.scott.base.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuDto
 *
 * @author liscott
 * @date 2023/1/5 11:04
 * description  TODO
 */
@Setter
@Getter
public class MenuDto extends BaseDTO implements Serializable {
    private Long id;
    private List<MenuDto> children;
    private Integer type;
    private String permission;
    private String title;
    private Integer menuSort;
    private String path;
    private String component;
    /**上级菜单*/
    private Long pid;
    /**下级菜单个数*/
    private Integer subCount;
    private Boolean iFrame;
    private Boolean cache;
    private Boolean hidden;
    private String componentName;
    private String icon;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuDto menuDto = (MenuDto) o;
        return Objects.equals(id, menuDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
