package org.scott.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.scott.base.BaseDTO;
import org.scott.service.dto.small.DeptSmallDto;
import org.scott.service.dto.small.JobSmallDto;
import org.scott.service.dto.small.RoleSmallDto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * project name  my-eladmin-backend-v2
 * filename  UserDto
 * @author liscott
 * @date 2023/1/4 15:58
 * description  TODO
 */
@Getter
@Setter
public class UserDto extends BaseDTO implements Serializable {
    private Long id;
    private Set<RoleSmallDto> roles;
    private Set<JobSmallDto> jobs;
    private DeptSmallDto dept;

    private Long deptId;

    private String username;
    private String nickName;
    private String email;
    private String phone;
    private String gender;

    private String avatarName;
    private String avatarPath;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    @JsonIgnore
    private Boolean isAdmin = false;

    private Date pwdResetTime;
}
