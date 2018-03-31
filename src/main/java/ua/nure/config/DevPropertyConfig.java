package ua.nure.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Profile("dev")
public class DevPropertyConfig implements PropertyConfig {

    @Override
    @Bean
    public PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] {new ClassPathResource("application-dev.properties")};
        // array cause later we can separate properties on common and specific. But now we have all props in one file
        propertyConfigurer.setLocations(resources);
        propertyConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertyConfigurer;
    }
}
