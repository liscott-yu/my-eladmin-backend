package org.scott.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project name  my-eladmin-backend-v2
 * filename  Query
 * @author liscott
 * @date 2023/1/9 10:17
 * description  查询注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    //基本对象的属性名
    String propName() default "";
    // 查询方式
    Type type() default Type.EQUAL;
    // 连接查询的属性名，如User类中的dept
    String joinName() default "";
    // 默认 左连接
    Join join() default Join.LEFT;
    // 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username,phone")
    String blurry() default "";

    enum Type {
        EQUAL,
        GREATER_THAN,
        IN
    }
    /**适用于简单连接查询，复杂的请自定义该注解，或者使用sql查询*/
    enum Join {
        LEFT,
        RIGHT,
        INNER
    }
}
