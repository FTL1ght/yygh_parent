package com.yygh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-18 15:54
 */
@Configuration
@MapperScan("com.yygh.mapper")
public class HospConfig {
}
