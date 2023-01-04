package com.ftl1ght.yygh.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author FTL1ght
 * @Description
 * @create 2023-01-01 20:36
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ftl1ght")
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.yygh")
@EnableFeignClients(basePackages = "com.ftl1ght")

//swagger相关
//@MapperScan(basePackages = "com.ftl1ght.yygh.common.config")
//@EnableSwagger2
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class,args);
    }
}
