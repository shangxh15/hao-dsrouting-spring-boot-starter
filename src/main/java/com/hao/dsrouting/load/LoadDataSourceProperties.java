package com.hao.dsrouting.load;

import com.hao.dsrouting.domain.DataSourceProperties;

/**
 * 数据源加载
 *
 * @author shangxuhao
 */
@FunctionalInterface
public interface LoadDataSourceProperties {

    DataSourceProperties load(Object lookupKey);
}
