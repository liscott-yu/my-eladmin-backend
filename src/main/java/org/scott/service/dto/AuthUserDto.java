package org.scott.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * project name  my-eladmin-backend-v2
 * filename  AuthUserDto
 * @author liscott
 * @date 2023/1/4 14:36
 * description  登录用户Dto，属性由前端登录页面的参数而定
 */
@Setter
@Getter
public class AuthUserDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String uuid = "";
    private String code;
}
