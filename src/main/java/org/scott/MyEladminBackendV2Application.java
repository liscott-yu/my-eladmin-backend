package org.scott;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyEladminBackendV2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyEladminBackendV2Application.class, args);
        System.out.println("http://localhost:8080");
    }

}
