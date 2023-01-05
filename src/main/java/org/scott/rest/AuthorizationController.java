package org.scott.rest;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.config.RsaProperties;
import org.scott.service.dto.AuthUserDto;
import org.scott.service.dto.JwtUserDto;
import org.scott.stringConstant.StringConstant;
import org.scott.utils.RedisUtils;
import org.scott.utils.RsaUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
        // 密码解密
        String dePassword = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUserDto.getPassword());

        // 从 redis 中 根据 key 获取 value
        String captchaValue = (String) redisUtils.get(authUserDto.getUuid());
        // 删除 redis 中 保存的 验证码信息
        redisUtils.del(authUserDto.getUuid());
        if(StringUtils.isBlank(captchaValue)) {
            throw new Exception("验证码为空 或 已过期！");
        }
        if(StringUtils.isBlank(authUserDto.getCode()) || !authUserDto.getCode().equalsIgnoreCase(captchaValue)) {
            throw new Exception(" 验证码错误！");
        }
        // 验证码验证通过
        // //认证授权//1、根据用户名和密码构造一个UsernamePasswordAuthenticationToken实例
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authUserDto.getUsername(), dePassword);
        // //2、认证UsernamePasswordAuthenticationToken实例，认证成功则返回包含用户信息的Authentication实例
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        //3、设置当前登录用户，这一步是为了可以让其他类或方法通过SecurityContextHolder.getContext().getAuthentication()拿到当前登录的用户
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //4、通过已经认证的Authentication返回UserDetails
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        // 返回 JwtUserDto 和 token
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
           put("token", "token");
           put("user", jwtUserDto);
        }};
        return ResponseEntity.ok(authInfo);
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
