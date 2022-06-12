package spring.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskDecorator taskDecorator() {
        return new TaskDecorator() {
            @Nonnull
            @Override
            public Runnable decorate(@Nonnull Runnable runnable) {
                String authToken = getAuthToken();
                return () -> {
                    try {
                        AuthTokenContextHolder.setAuthToken(authToken);
                        runnable.run();
                    } finally {
                        AuthTokenContextHolder.clearAuthToken();
                    }
                };
            }
        };
    }

    private static String getAuthToken() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader(AUTHORIZATION))
                .orElse("No Authorization Token in this Context!");
    }
}
