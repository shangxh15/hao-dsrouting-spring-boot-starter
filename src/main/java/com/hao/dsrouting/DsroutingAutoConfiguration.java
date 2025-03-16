package com.hao.dsrouting;

import com.hao.dsrouting.datasource.DsroutingDataSource;
import com.hao.dsrouting.domain.DataSourceProperties;
import com.hao.dsrouting.factory.DataSourceFactory;
import com.hao.dsrouting.factory.DruidDataSourceFactory;
import com.hao.dsrouting.load.LoadDataSourceProperties;
import com.hao.dsrouting.load.PropertiesLoadDataSourceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @auth shangxuhao 2025/3/16
 */
@AutoConfiguration(
        before = {SqlInitializationAutoConfiguration.class}
)
@ConditionalOnClass({DataSource.class})
@Configuration
public class DsroutingAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "dsrouting.default")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConditionalOnMissingBean(value = {DataSourceFactory.class})
    public DataSourceFactory dataSourceFactory() {
        return new DruidDataSourceFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value = {LoadDataSourceProperties.class})
    public LoadDataSourceProperties loadDataSourceProperties() {
        return new PropertiesLoadDataSourceProperties();
    }

    @Bean
    public DataSource dataSource() {
        return new DsroutingDataSource();
    }

}
