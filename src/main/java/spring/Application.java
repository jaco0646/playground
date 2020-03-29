package spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Bean count == " + ctx.getBeanDefinitionCount());
//            System.out.println( Arrays.stream(ctx.getBeanDefinitionNames())
//                    .sorted()
//                    .collect(joining(", ")));
        };
    }
}