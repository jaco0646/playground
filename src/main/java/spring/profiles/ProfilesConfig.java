package spring.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("foo")
@Configuration
public class ProfilesConfig {

    @Profile("bar")
    @Bean
    String foobarBean() {
        System.out.println(">>> instantiating foobar bean");
        return "foobar";
    }

    @Profile("baz")
    @Bean
    String foobazBean() {
        System.out.println(">>> instantiating foobaz bean");
        return "foobaz";
    }
}
