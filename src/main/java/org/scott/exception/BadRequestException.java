package org.scott.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * project name  my-eladmin-backend-v2
 * filename  BadRequestException
 *
 * @author liscott
 * @date 2023/1/5 11:24
 * description  400异常请求，返回状态码、错误消息
 */
@Getter
public class BadRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }
}
