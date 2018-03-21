package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application-dev.properties")
public class DevDataSourceConfig implements DataSourceConfig {

    @Autowired
    Environment environment;

    @Override
    public DataSource datasource() {
        return null;
    }
}
