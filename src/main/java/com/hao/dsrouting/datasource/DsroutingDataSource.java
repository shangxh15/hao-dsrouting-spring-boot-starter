package com.hao.dsrouting.datasource;

import com.hao.dsrouting.domain.DataSourceProperties;
import com.hao.dsrouting.factory.DataSourceFactory;
import com.hao.dsrouting.holder.DsroutingContextHolder;
import com.hao.dsrouting.load.LoadDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Dsrouting数据源
 *
 * @author shangxuhao
 */

public class DsroutingDataSource extends AbstractRoutingDataSource {

    private final Log log = LogFactory.getLog(DsroutingDataSource.class);

    private DataSource defaultDataSource;

    private Map<Object, DataSource> dataSourceMap;

    @Resource
    private LoadDataSource loadDataSource;

    @Autowired(required = false)
    private DataSourceProperties dataSourceProperties;

    @Resource
    private DataSourceFactory dataSourceFactory;

    //默认数据库配置

    public DsroutingDataSource() {
        this.dataSourceMap = new HashMap<>();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DsroutingContextHolder.getDataSource();
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    protected DataSource determineTargetDataSource() {
        Object lookupKey = this.determineCurrentLookupKey();
        //使用默认数据库
        if (lookupKey == null) {
            DataSource defaultDataSource = getDefaultDataSource();
            if (null == defaultDataSource) {
                throw new IllegalStateException("Cannot get default DataSource");
            }
            return defaultDataSource;
        }

        DataSource dataSource = null;
        if (this.dataSourceMap.containsKey(lookupKey)) {
            dataSource = this.dataSourceMap.get(lookupKey);
        } else {
            DataSourceProperties dataSourceProp = loadDataSource.load(lookupKey);
            if (null == dataSourceProp) {
                throw new IllegalStateException("Cannot determine target DataSourceProperties for lookup key [" + lookupKey + "]");
            }

            dataSource = dataSourceFactory.getObject(dataSourceProp);
            this.dataSourceMap.put(lookupKey, dataSource);
        }

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        } else {
            return dataSource;
        }
    }

    public DataSource getDefaultDataSource() {
        if (null == this.defaultDataSource) {
            try {
                if (dataSourceProperties.isActive()) {
                    this.defaultDataSource = dataSourceFactory.getObject(dataSourceProperties);
                }
            } catch (Exception e) {
                log.error("connot load DefaultDataSourceProperties.class bean", e);
            }
        }
        return defaultDataSource;
    }
}
