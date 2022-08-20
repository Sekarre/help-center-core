package com.sekarre.helpcentercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HelpCenterCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpCenterCoreApplication.class, args);
    }

}
