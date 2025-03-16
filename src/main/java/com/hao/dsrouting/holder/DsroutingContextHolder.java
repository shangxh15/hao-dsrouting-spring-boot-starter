package com.hao.dsrouting.holder;

/**
 * Dsrouting本地线程数据源持有者
 *
 * @author shangxuhao
 */
public class DsroutingContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
