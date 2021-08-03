package spring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "HttpStatus", url = "${http.status.url}")
@FeignHeader(name = "${feign.header.key}", value = "${feign.header.value}")
public interface HttpStatusClient {
    record HttpStatus(int code, String description){}

    @RequestMapping(method = GET, path = "/200")
    HttpStatus twoHundred();
}
