package org.scott.config;

import lombok.Data;

/**
 * project name  my-eladmin-backend-v2
 * filename  SecurityProperties
 * @author liscott
 * @date 2023/1/11 13:15
 * description  jwt 参数配置 在 .ConfigBeanConfiguration中进行属性装配
 */
@Data
public class SecurityProperties {
    /** Request Header: Authorization*/
    private String header;
    /** token前缀，样如：前缀 Bearer */
    private String tokenStartWith;
    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;
    /**TOKEN 过期时间，单位：秒*/
    private Long tokenValidityInSeconds;
    /**在线用户 key，根据 key 查询 redis 中在线用户的数据*/
    private String onlineKey;
    /** 验证码 key */
    private String codeKey;
    /** token 续期 */
    private Long detect;
    /** token 续期时间 */
    private Long renew;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
