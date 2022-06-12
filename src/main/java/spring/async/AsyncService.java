package spring.async;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spring.jpa.PlaygroundRepository;

import static spring.async.AuthTokenContextHolder.getAuthToken;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final PlaygroundRepository repo;

    @Async
    public void logAuthToken() {
        System.out.println(">>> authToken before Repo call: " + getAuthToken().orElse(null));
        repo.findAll();
        System.out.println(">>> authToken after Repo call: " + getAuthToken().orElse(null));
    }

}
