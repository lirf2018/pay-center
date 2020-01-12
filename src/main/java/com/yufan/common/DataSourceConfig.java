package com.yufan.common;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 12:41
 * 功能介绍: 多数据源
 */
@Configuration
public class DataSourceConfig {

    private Logger LOG = Logger.getLogger(DataSourceConfig.class);

    @Primary
    @Bean(name = "db1DataSource")
    @Qualifier("db1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1DataSource() {
        LOG.info("---------init---------db1  built");
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "db2DataSource")
    @Qualifier("db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2DataSource() {
        LOG.info("---------init---------db2  built");
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
