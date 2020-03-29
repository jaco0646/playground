package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import spring.cache.CacheConfig;

@PropertySource({
    // Default properties cannot be overridden by @PropertySource files.
    // Adding application.properties here does nothing.
    "application.properties",
    "local.properties",
    "prod.properties"
})
// This import is necessary to ensure CacheConfig receives properties from the @PropertySource files.
// Because CacheConfig utilizes @ConditionalOnProperty, the properties must be loaded first.
@Import(CacheConfig.class)
@Configuration
public class RootConfig {
    public RootConfig() {
        System.out.println("instantiating RootConfig");
    }

    @Bean
    Service service() {
        return new Service();
    }
}

