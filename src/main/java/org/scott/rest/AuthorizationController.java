package org.scott.rest;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.service.dto.AuthUserDto;
import org.scott.stringConstant.StringConstant;
import org.scott.utils.RedisUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
        // 从 redis 中 根据 key 获取 value
        String captchaValue = (String) redisUtils.get(authUserDto.getUuid());
        // 删除 redis 中 保存的 验证码信息
        redisUtils.del(authUserDto.getUuid());
        //
        if(StringUtils.isBlank(captchaValue)) {
            throw new Exception("验证码为空 或 已过期！");
        }
        if(StringUtils.isBlank(authUserDto.getCode()) || !authUserDto.getCode().equals(captchaValue)) {
            throw new Exception(" 验证码错误！");
        }
        // 验证码验证通过
        return ResponseEntity.ok("验证码校验通过，还未认证授权！");
    }

    @GetMapping(value = "/code")
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
