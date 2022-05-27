package spring.async;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @GetMapping("/async")
    public String getTokenFromAsyncThread(WebRequest webRequest) throws ExecutionException, InterruptedException {
        if (webRequest.getHeader(AUTHORIZATION) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header required");
        }
        return asyncService.getAuthTokenAsync().get();
    }

}
