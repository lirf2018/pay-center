package com.yufan.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 14:56
 * 功能介绍:
 */
@Primary
@Configuration
@EnableTransactionManagement
public class DB1Config {

    @Autowired
    @Qualifier("db1DataSource")
    private DataSource db1DataSource;


    @Primary
    @Bean(name = "entityManagerFactoryDb1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDb1(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db1DataSource)
                .packages("com.yufan.pojo") //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }


    @Primary
    @Bean(name = "transactionManagerDb1")
    public PlatformTransactionManager transactionManagerDb1(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDb1(builder).getObject());
    }

}
