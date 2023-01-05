package org.scott.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * project name  my-eladmin-backend-v2
 * filename  MenuMetaVO
 * @author liscott
 * @date 2023/1/5 10:46
 * description  TODO
 */
@Data
public class MenuMetaVo implements Serializable {
    /** menu 名称，如 系统管理*/
    private String title;
    /** menu 是否缓存*/
    private Boolean cache;
    /** menu 图标名称*/
    private String icon;

    public MenuMetaVo(String title, Boolean cache, String icon) {
        this.title = title;
        this.icon = icon;
        this.cache = cache;
    }
}
