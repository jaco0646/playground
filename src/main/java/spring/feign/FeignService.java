package spring.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeignService {
    private final HttpStatusClient httpStatusClient;

    public HttpStatusClient.HttpStatus success() {
        return httpStatusClient.twoHundred();
    }
}
