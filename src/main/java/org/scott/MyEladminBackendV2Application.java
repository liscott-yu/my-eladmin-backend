package org.scott;

import org.scott.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyEladminBackendV2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyEladminBackendV2Application.class, args);
        System.out.println("http://localhost:8080");
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        // 在Spring Boot中注册 SpringContentHolder
        return new SpringContextHolder();
    }


}
