package com.hao.dsrouting.factory;

import com.hao.dsrouting.domain.DataSourceProperties;

import javax.sql.DataSource;

/**
 * 数据源生成工厂
 *
 * @auth shangxuhao 2025/3/15
 */
@FunctionalInterface
public interface DataSourceFactory {

    DataSource getObject(DataSourceProperties dataSourceProperties);

}
