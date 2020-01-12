package com.yufan.common.config;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 18:48
 * 功能介绍:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactoryPrimary",
//        transactionManagerRef = "transactionManagerPrimary",
//        basePackages = {"com.yufan.dao.db1.impl"}
//        )
public class SourceDataConfig {

//    @Autowired
//    private HibernateProperties hibernateProperties;
//
//    @Autowired
//    private JpaProperties jpaProperties;
//
//    private Map<String, Object> getVendorProperties() {
//        return hibernateProperties.determineHibernateProperties(
//                jpaProperties.getProperties(), new HibernateSettings()
//        );
//    }
//
//    @Primary
//    @Bean(name = "primaryDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db1") // 配置数据源获取的涞源
//    public DataSource primaryDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryPrimary")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource)
//                .properties(getVendorProperties())
//                .packages("com.yufan.pojo1")
//                .persistenceUnit("primaryPersistenceUnit")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManagerPrimary")
//    public PlatformTransactionManager propertyTransactionManager(
//            @Qualifier("entityManagerFactoryPrimary") EntityManagerFactory propertyEntityManagerFactory) {
//        return new JpaTransactionManager(propertyEntityManagerFactory);
//    }

}
