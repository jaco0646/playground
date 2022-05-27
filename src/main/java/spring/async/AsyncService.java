package spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AsyncService {

    @Async
    public CompletableFuture<String> getAuthTokenAsync() throws InterruptedException {
        return CompletableFuture.completedFuture(getAuthToken());
    }

    private String getAuthToken() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader(AUTHORIZATION))
                .orElse("No Authorization Token in this Context!");
    }

}
