package com.sme.mts.test.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Dummy configuration for mybatis
 */
@Configuration @MapperScan(basePackages = "com.sme.mts.config")
public class Mybatis {
    @Mapper // Dummy mapper to avoid Mybatis WARN log message
    public interface Dummy {

    }
}
