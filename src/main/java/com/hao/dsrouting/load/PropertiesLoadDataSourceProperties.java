package com.hao.dsrouting.load;

import com.hao.dsrouting.domain.DataSourceProperties;
import com.hao.dsrouting.factory.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "dsrouting")
public class PropertiesLoadDataSourceProperties implements LoadDataSourceProperties {

    private Map<String, DataSourceProperties> dataSources;

    public void setDataSources(Map<String, DataSourceProperties> dataSources) {
        this.dataSources = dataSources;
    }

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Override
    public DataSourceProperties load(Object lookupKey) {
        DataSourceProperties dataSourceProperties = dataSources.get(String.valueOf(lookupKey));
        if (dataSourceProperties == null) {
            return null;
        } else {
            return dataSourceProperties;
        }
    }

}
