package com.ftl1ght.yygh.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-18 15:54
 */
@Configuration
@MapperScan("com.ftl1ght.yygh.mapper")
public class HospConfig {
    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptorForHosp(){
        return new PaginationInterceptor();
    }
}
