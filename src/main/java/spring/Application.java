package spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class Application {
    private static volatile ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Application.class, args);
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

    /** {@see https://www.baeldung.com/java-restart-spring-boot-app}
     * This does not play nice with spring-boot-devtools. */
    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(Application.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }
}