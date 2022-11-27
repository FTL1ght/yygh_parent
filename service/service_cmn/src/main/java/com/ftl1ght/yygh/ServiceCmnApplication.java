package com.ftl1ght.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-23 16:06
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ftl1ght")
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class,args);
    }
}
