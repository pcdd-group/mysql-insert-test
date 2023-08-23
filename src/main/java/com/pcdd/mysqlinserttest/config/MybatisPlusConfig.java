package com.pcdd.mysqlinserttest.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pcdd
 */
@Configuration
@MapperScan("com.pcdd.mysqlinserttest.mapper")
public class MybatisPlusConfig {

    @Bean
    @ConditionalOnMissingBean(InsertBatchSqlInjector.class)
    public InsertBatchSqlInjector customSqlInjector() {
        return new InsertBatchSqlInjector();
    }

}
