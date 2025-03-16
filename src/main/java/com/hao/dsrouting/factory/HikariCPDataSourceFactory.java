package com.hao.dsrouting.factory;

import com.hao.dsrouting.domain.DataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * HikariCP 数据库连接池工厂
 *
 * @auth shangxuhao 2025/3/16
 */
public class HikariCPDataSourceFactory extends AbstractDataSourceFactory<HikariDataSource> {

    private static final String DAHO_HIKARI_PREFIX = "dsrouting.hikari.";

    @Override
    public DataSource getObject(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        configFromEnvironment(hikariDataSource, DAHO_HIKARI_PREFIX);
        hikariDataSource.setJdbcUrl(dataSourceProperties.getUrl());
        hikariDataSource.setUsername(dataSourceProperties.getUsername());
        hikariDataSource.setPassword(dataSourceProperties.getPassword());
        return hikariDataSource;
    }
}
