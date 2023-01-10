package org.scott.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.scott.exception.BadRequestException;

/**
 * project name  my-eladmin-backend-v2
 * filename  ValidationUtils
 *
 * @author liscott
 * @date 2023/1/9 11:36
 * description  校验工具
 */
public class ValidationUtils {
    /** 校验空，若是空，抛出异常
     *
     * ValidationUtils.isEmpty(null,entity,param,value)  = true
     * ValidationUtils.isEmpty("",entity,param,value) = true
     * ValidationUtils.isEmpty("ab",entity,param,value) = false
     * ValidationUtils.isEmpty(new int[]{},entity,param,value) = true
     */
    public static void isNull(Object object, String entity, String parameter, Object value) {
        if(ObjectUtils.isEmpty(object)) {
            String msg = entity + " is not exits: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }
}
