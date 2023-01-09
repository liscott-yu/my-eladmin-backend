package org.scott.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.scott.annotation.DataPermission;
import org.scott.annotation.Query;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  QueryHelp
 * @author liscott
 * @date 2023/1/6 15:32
 * description  辅助查询类
 */
@Slf4j
@SuppressWarnings({"all"})
public class QueryHelp {
    /**
     * /该方法对应于Specification的toPredicate方法，Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
     * /@root代表查询的实体类（表），可以通过root.join定义要关联查询的表；
     * /@query代表查询条件，具体指的是哪些字段需要条件查询，如这里使用的是自定义的查询条件类UserQueryCriteria；
     * /@cb代表构建查询条件，如or, and ,like, equal等等
     * /JPQL：select user from User user left join Dept dept where user.dept.id in (2)
     */
    public static <R, Q> Predicate getPredicate(Root<R> root, Q queryCriteria, CriteriaBuilder cb) {
        // 条件的集合
        List<Predicate> list = new ArrayList<>();
        if(queryCriteria == null) {
            return cb.and(list.toArray(new Predicate[0]));
        }
        // 获取数据权限，即：部门id的集合
//        List<Long> dataScope = SecurityUtils.getCurrentUserDataScope();
//        if(CollectionUtil.isNotEmpty(dataScope)) {
//            // root 代表 User， 这里的 join 代表 Dept
//            Join join = root.join("dept", JoinType.LEFT);
//            //添加条件：用户的部门id要在dataScopes里面，dataScopes指当前登录用户能看到的部门id集合
//            list.add(join.get("id").in(dataScope));
//        }
        // 数据权限验证
        DataPermission permission = queryCriteria.getClass().getAnnotation(DataPermission.class);
        if( permission != null ) {
            // 获取数据权限
            List<Long> dataScope = SecurityUtils.getCurrentUserDataScope();
            if(CollectionUtil.isNotEmpty(dataScope)) {
                if(StringUtils.isNotBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName())) {
                    Join join = root.join(permission.joinName(), JoinType.LEFT);
                    list.add(join.get("id").in(dataScope));
                } else if ((StringUtils.isBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName()))) {
                    list.add(root.get("id").in(dataScope));
                }
            }
        }

        try {
            // 获取UserQueryCriteria 类的所有字段
            List<Field> fields = Arrays.asList(queryCriteria.getClass().getDeclaredFields());
            // 遍历 每个 字段（属性）
            for (Field field:fields) {
                boolean accessible = field.isAccessible();
                // 设置对象的访问权限，保证对private的属性的访
                field.setAccessible(true);
                field.getAnnotation(Query.class);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String joinName = q.joinName();
                    String blurry = q.blurry();
                    // 用作条件查询的字段名，如 id
                    String attributeName = StringUtils.isBlank(propName) ? field.getName() : propName;
                    Class<?> fieldType = field.getType();
                    Object valueFromFrontEnd = field.get(queryCriteria);
                    if(ObjectUtils.isEmpty(valueFromFrontEnd)){
                        continue;
                    }
                    Join join = null;
                    // 模糊多字段，username，Email，phone
                    if (ObjectUtils.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        ArrayList<Predicate> orPredicate = new ArrayList<>();
                        for (String s: blurrys ) {
                            orPredicate.add(cb.like(root.get(s), "%" + valueFromFrontEnd.toString() + "%"));
                        }
                        list.add(cb.or(orPredicate.toArray(new Predicate[0])));
                        continue;
                    }
                    // 查询 条件
                    if(ObjectUtils.isNotEmpty(joinName)) {
                        String[] joinNames = joinName.split(">");
                        for (String name : joinNames ) {
                            switch (q.join()) {
                                case LEFT:
                                    if(ObjectUtils.isNotEmpty(join) && ObjectUtils.isNotEmpty(valueFromFrontEnd)) {
                                        join = join.join(name, JoinType.LEFT);
                                    } else {
                                        join = root.join(name, JoinType.LEFT);
                                    }
                                default:
                                    break;
                            }
                        }
                    }
                    //
                    switch(q.type()) {
                        case EQUAL:
                            list.add(cb.equal(getExpression(attributeName, join, root)
                                    .as((Class<? extends Comparable>) fieldType), valueFromFrontEnd));
                            break;
                        case IS_NULL:
                            list.add(cb.isNull(getExpression(attributeName, join, root)));
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThan(getExpression(attributeName, join, root)
                                    , (Comparable) valueFromFrontEnd));
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Object>) valueFromFrontEnd)) {
                                list.add(getExpression(attributeName, join, root).in((Collection<Object>) valueFromFrontEnd));
                            }
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return cb.and(list.toArray(new Predicate[0]));
    }

    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if(ObjectUtils.isNotEmpty(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }
}
