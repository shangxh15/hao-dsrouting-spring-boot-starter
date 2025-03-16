<h1> hao-dsrouting-spring-boot-starter </h1>

[项目地址](https://github.com/shangxh15/hao-dsrouting-spring-boot-starter) 

**项目说明**
- 基于SpringBoot 2.7.7版本
- hao-dsrouting-spring-boot-starter 是一个轻量级的数据库切换插件，已集成Druid、Hikari数据库连接池

**版本信息**
- 核心框架：Spring Boot 2.7.7
- Druid连接池：Druid 1.2.20
- HikariCP连接池：HikariCP 4.0.3

**环境**
- jdk 8

**项目结构**
```
hao-dsrouting-spring-boot-starter
├─sql  
│  ├─tend_db.sql 默认库SQL语句
│  ├─tend_db_1.sql 
│  ├─tend_db_2.sql 
│
├─datasource Dsroting 多数据源
|
├─factory 数据源创建工厂
│ 
├─load 数据源连接配置参数加载器
```

**常见问题**

1、配置变量
```yaml
dsrouting:
  # 默认数据源
  default:
    url: jdbc:mysql://xxx.xxx.xxx.xxx:3306/tend_db
    username: xxxx
    password: xxxx
  # PropertiesLoadDataSource模式下数据源配置模式
  dataSources:
    app1:
      url: jdbc:mysql://xxx.xxx.xxx.xxx:3306/tend_db_1
      username: xxxx
      password: xxxx
    app2:
      url: jdbc:mysql://xxx.xxx.xxx.xxx:3306/tend_db_2
      username: xxxx
      password: xxxx
  #druid数据库连接池参数配置
  druid:
    driver-class-name: com.mysql.cj.jdbc.Driver
    initial-size: 5
    min-idle: 5
    max-active: 20
    max-wait: 60000
    time-between-eviction-runs-millis: 60000
    min-evictable-idle-time-millis: 300000
    validation-query: SELECT 1 FROM DUAL
    test-while-idle: true
    test-on-borrow: false
    test-on-return: false
    pool-prepared-statements: true
    max-pool-prepared-statement-per-connection-size: 20
    use-global-data-source-stat: true
    connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

  #hikariCP数据库连接池参数配置
  hikari:
    driver-class-name: com.mysql.cj.jdbc.Driver
    initial-size: 5
    min-idle: 5
    max-active: 20
    max-wait: 60000
    time-between-eviction-runs-millis: 60000
    min-evictable-idle-time-millis: 300000
    validation-query: SELECT 1 FROM DUAL
    test-while-idle: true
    test-on-borrow: false
    test-on-return: false
    pool-prepared-statements: true
    max-pool-prepared-statement-per-connection-size: 20
    use-global-data-source-stat: true
    connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

```

2、插件使用

项目文件中，安装当前插件
```shell 
mvn install
```
项目pom文件中引入当前插件
```xml
<dependency>
    <groupId>com.hao</groupId>
    <artifactId>hao-dsrouting-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```
最后项目启动文件中去除DataSourceAutoConfiguration.class

```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

3、自定义配置

数据源自定义，通过实现LoadDataSourceProperties.java接口，自定义获取数据源方式
```java
@FunctionalInterface
public interface LoadDataSourceProperties {

    DataSourceProperties load(Object lookupKey);
}

//从默认数据库中获取数据源信息
@Component
public class DbLoadDataSourceProperties implements LoadDataSourceProperties {

    @Autowired
    @Lazy
    private DatabaseTendMapper databaseTendMapper;

    @Override
    public DataSourceProperties load(Object lookupKey) {
        String dataSource = DsroutingContextHolder.getDataSource();
        DsroutingContextHolder.clearDataSource();
        DatabaseTend databaseTend = databaseTendMapper.selectDatabaseTendByDatabaseName(String.valueOf(lookupKey));
        DsroutingContextHolder.setDataSource(dataSource);
        if (null == databaseTend) {
            return null;
        } else {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            BeanUtils.copyProperties(databaseTend, dataSourceProperties);
            return dataSourceProperties;
        }
    }
}

```

数据库连接池自定义，目前已实现Druid、HikariCP两种数据库连接池，可以通过继承AbstractDataSourceFactory.java抽象类，或者实现DataSourceFactory.java接口,实现自定义数据库连接池

```java
@FunctionalInterface
public interface DataSourceFactory {

    DataSource getObject(DataSourceProperties dataSourceProperties);

}

public abstract class AbstractDataSourceFactory<T> implements DataSourceFactory, EnvironmentAware {
    
    //...
}

//Druid数据库连接池工厂
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


```


**最后**

- 如果喜欢，记得star fork 谢谢您的关注

