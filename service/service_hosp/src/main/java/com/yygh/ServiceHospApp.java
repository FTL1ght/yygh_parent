package com.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-17 14:05
 */
@SpringBootApplication
@EnableDiscoveryClient  //启用nacos服务注册
@EnableFeignClients(basePackages = "com.ftl1ght")     //启用feign调用
//@ComponentScan(basePackages="com.ftl1ght")
public class ServiceHospApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApp.class,args);
    }
}
