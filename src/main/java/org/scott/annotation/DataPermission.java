package org.scott.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project name  my-eladmin-backend-v2
 * filename  DataPermission
 * @author liscott
 * @date 2023/1/9 11:28
 * description  * 用于判断是否过滤 数据权限
 *  * 1、如果没有用到 @OneToOne 这种关联关系，只需要填写 fieldName [参考：DeptQueryCriteria.class]
 *  * 2、如果用到了 @OneToOne ，fieldName 和 joinName 都需要填写，拿UserQueryCriteria.class举例:
 *  * 应该是 @DataPermission(joinName = "dept", fieldName = "id")
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermission {
    /** Entity 中的字段名称*/
    String fieldName() default "";
    /** Entity 中与部门关联的字段名称*/
    String joinName() default  "";
}
