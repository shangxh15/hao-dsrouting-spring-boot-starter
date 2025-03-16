package com.hao.dsrouting.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据源对象工厂抽象类
 *
 * @auth shangxuhao 2025/3/15
 */
public abstract class AbstractDataSourceFactory<T> implements DataSourceFactory, EnvironmentAware {

    private final Log log = LogFactory.getLog(AbstractDataSourceFactory.class);

    private Environment environment;

    protected void configFromEnvironment(T dataSource, String prefix) {
        Class<?> clazz = dataSource.getClass();
        List<Field> fields = getFields(clazz);
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            field.setAccessible(true);
            //获取配置文件属性
            String property = getProperty(field, prefix);
            if (null != property) {
                //赋值
                setFieldValue(field, dataSource, property);
            }
        }

    }

    private static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            if (declaredFields.length > 0) {
                fields.addAll(Arrays.asList(declaredFields));
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    //获取配置文件属性
    private String getProperty(Field field, String prefix) {
        String key = prefix + field.getName();
        String property = environment.getProperty(key);
        if (null == property) {
            key = prefix + toKebabCase(field.getName());
            property = environment.getProperty(key);
        }
        return property;
    }

    //赋值
    private void setFieldValue(Field field, T dataSource, String property) {
        Class<?> fieldType = field.getType();
        try {
            if (fieldType == int.class || fieldType == Integer.class) {
                field.setInt(dataSource, Integer.parseInt(property));
            } else if (fieldType == long.class || fieldType == Long.class) {
                field.setLong(dataSource, Long.parseLong(property));
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                field.setBoolean(dataSource, Boolean.parseBoolean(property));
            } else if (fieldType == String.class) {
                field.set(dataSource, property);
            } else {
                //数据加载失败
                log.error("Unsupported field type: " + fieldType);
            }
        } catch (IllegalAccessException e) {
            log.error("Unsupported set field : ", e);
        }

    }

    //转换为kebab-case格式
    protected String toKebabCase(String camelCase) {
        StringBuilder kebabCase = new StringBuilder();
        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (kebabCase.length() > 0) {
                    kebabCase.append('-');
                }
                kebabCase.append(Character.toLowerCase(c));
            } else {
                kebabCase.append(c);
            }
        }
        return kebabCase.toString();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
