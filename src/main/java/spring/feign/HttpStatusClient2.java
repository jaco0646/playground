package spring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "HttpStatusClient", url = "${http.status.url}")
interface HttpStatusClient2 {
    record HttpStatus(int code, String description){}

    @GetMapping(path = "/200", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<HttpStatus> twoHundred();
}
