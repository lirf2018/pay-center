package com.yufan.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 14:56
 * 功能介绍:
 */
@Configuration
@EnableTransactionManagement
public class DB2Config {

    @Autowired
    @Qualifier("db2DataSource")
    private DataSource db2DataSource;

    @Bean(name = "entityManagerFactoryDb2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDb2 (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db2DataSource)
                .packages("com.yufan.pojo") //设置实体类所在位置
                .build();
    }


    @Bean(name = "transactionManagerDb2")
    public PlatformTransactionManager transactionManagerDb2(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDb2(builder).getObject());
    }
}
