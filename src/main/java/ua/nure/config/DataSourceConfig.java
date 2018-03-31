package ua.nure.config;

import javax.sql.DataSource;

public interface DataSourceConfig {
    public DataSource dataSource(String driverClassName, String url, String user, String password);
}
