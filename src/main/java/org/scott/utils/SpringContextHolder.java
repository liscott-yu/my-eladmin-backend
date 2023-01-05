package org.scott.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;

/**
 * project name  my-eladmin-backend-v2
 * filename  SpringContentHoder
 * @author liscott
 * @date 2023/1/5 11:35
 * description  TODO
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
    }


    /**
     *  根据名，从 静态变量 applicationContext 中获取 Bean, 自动转型 T
     * @param name name
     * @param <T> T
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据 类型，从 静态变量 applicationContext 中获取 Bean, 自动转型 T
     * @param requiredType T
     * @param <T> T
     * @return T
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if(applicationContext == null){
            throw new IllegalStateException("applicaitonContext属性未注入, 请在applicationContext" +
                    ".xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder.");
        }
    }
}
