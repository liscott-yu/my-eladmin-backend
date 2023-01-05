package org.scott.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * project name  my-eladmin-backend-v2
 * filename  DataScopeEnum
 * @author liscott
 * @date 2023/1/4 17:43
 * description  数据权限枚举类
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {
    /* 全部的数据权限 */
    ALL("全部", "全部的数据权限"),

    /* 自己部门的数据权限 */
    THIS_LEVEL("本级", "自己部门的数据权限"),

    /* 自定义的数据权限 */
    CUSTOMIZE("自定义", "自定义的数据权限");

    private final String value;
    private final String description;

    /**
     * 查找对应的数据权限
     * @param val 、
     * @return 数据权限
     */
    public static DataScopeEnum find(String val) {
        for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
            if (val.equals(dataScopeEnum.getValue())) {
                return dataScopeEnum;
            }
        }
        return null;
    }
}
