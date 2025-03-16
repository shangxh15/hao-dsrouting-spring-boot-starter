package com.hao.dsrouting.factory;

import com.alibaba.druid.pool.DruidDataSource;
import com.hao.dsrouting.domain.DataSourceProperties;

import javax.sql.DataSource;

/**
 * Druid 数据库连接池工厂
 *
 * @auth shangxuhao 2025/3/13
 */
public class DruidDataSourceFactory extends AbstractDataSourceFactory<DruidDataSource> {

    private static final String DAHO_DRUID_PREFIX = "dsrouting.druid.";

    @Override
    public DataSource getObject(DataSourceProperties dataSourceProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        configFromEnvironment(druidDataSource, DAHO_DRUID_PREFIX);
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        return druidDataSource;
    }
}
