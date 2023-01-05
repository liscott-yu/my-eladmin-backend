package org.scott.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuVO
 *
 * @author liscott
 * @date 2023/1/5 10:50
 * description  菜单
 * //这个注解在类上方，表示类的某个属性如果为空字符串或者null的时候，则该属性不参与序列化
 * @JsonInclude(JsonInclude.Include.NON_EMPTY)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private List<MenuVo> children;
}
