package com.hao.dsrouting.domain;

/**
 * 数据源参数实体
 *
 * @author shangxuhao
 */
public class DataSourceProperties {

    private String url;

    private String username;

    private String password;

    public boolean isActive() {
        return this.url != null && this.username != null && this.password != null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DataSourceProperties{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
