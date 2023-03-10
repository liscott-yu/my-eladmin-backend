package org.scott.config;

import lombok.Data;
import org.scott.stringConstant.StringConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * project name  my-eladmin-backend-v2
 * filename  RsaProperties
 * @author liscott
 * @date 2023/1/4 16:16
 * description  TODO
 */
@Data
@Component
public class RsaProperties {

    public static String privateKey;
    public static String publicKey;

    @Value("${rsa.private_key}")
    public void setPrivateKey(String privateKey) {
        RsaProperties.privateKey = privateKey;
    }

    /** 修改用户密码后再加密用 */
    @Value("${rsa.public_key}")
    public void setPublicKey(String privateKey) {
        RsaProperties.publicKey = publicKey==null ? StringConstant.RSA_PUBLIC_KEY : publicKey;
    }
}
