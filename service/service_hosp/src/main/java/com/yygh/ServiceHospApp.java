package com.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-17 14:05
 */
@SpringBootApplication
//@ComponentScan(basePackages="com.ftl1ght")
public class ServiceHospApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApp.class,args);
    }
}
