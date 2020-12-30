package spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import spring.cache.CacheConfig;
import spring.domain.Animal;

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

    @Bean
    public CommandLineRunner registerAnimals(ObjectMapper mapper) {
        return args -> {
            Reflections reflections = new Reflections("spring.domain");
            reflections.getSubTypesOf(Animal.class).forEach(mapper::registerSubtypes);
        };
    }
}
