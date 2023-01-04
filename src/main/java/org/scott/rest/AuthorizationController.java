package org.scott.rest;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.scott.stringConstant.StringConstant;
import org.scott.utils.RedisUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * project name  my-eladmin-backend-v2
 * filename  AuthorizationController
 * @author liscott
 * @date 2023/1/4 11:39
 * description  登录 认证
 * 用 @RequiredArgsConstructor 取代 所有的 @Autowire
 */
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final RedisUtils redisUtils;

    @GetMapping("/code")
    public ResponseEntity<Object> getCode(){
         // 获取验证码信息
         Captcha captcha = new ArithmeticCaptcha(120, 38);
         captcha.setLen(2);
         String captchaValue = captcha.text();
         String uuid = StringConstant.UUID_PREFIX + IdUtil.simpleUUID();
         // 保存验证码信息（uuid, 验证码值）到Redis中
        redisUtils.set(uuid, captchaValue, 2L, TimeUnit.MINUTES);
        // 把验证码信息（uuid, captcha)封装成Map，返回
        Map<String, Object> imgResult = new HashMap<String, Object>(2){{
            put("uuid", uuid);
            // 验证码图片转化为Base64保存
            put("img", captcha.toBase64());
        }};
        return ResponseEntity.ok(imgResult);
    }
}
