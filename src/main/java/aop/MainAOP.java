package aop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainAOP {

    public static void main(String... args) {
        SpringApplication.run(MainAOP.class, args).close();
    }

    @Bean
    public CommandLineRunner commandLineRunner(Observable mo) {
        return args -> {
            mo.foo();
            mo.bar();
        };
    }

}
