package spring.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Nonnull;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskDecorator taskDecorator() {
        return new TaskDecorator() {
            @Nonnull
            @Override
            public Runnable decorate(@Nonnull Runnable runnable) {
                RequestAttributes context = RequestContextHolder.getRequestAttributes();
                return () -> {
                    try {
                        RequestContextHolder.setRequestAttributes(context);
                        runnable.run();
                    } finally {
                        RequestContextHolder.resetRequestAttributes();
                    }
                };
            }
        };
    }

}
