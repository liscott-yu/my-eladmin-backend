package org.scott.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * project name  my-eladmin-backend-v2
 * filename  EntityExistException
 * @author liscott
 * @date 2023/1/9 13:21
 * description  实体存在异常
 */
public class EntityExistException extends RuntimeException {
    public EntityExistException(Class clazz, String field, String val) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " " + val + " existed";
    }
}
