package spring.valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ValidationController {

    @PostMapping("/valid")
    @ResponseStatus(CREATED)
    public void createV1(@Valid @RequestBody ValidatableEntity v1) {
        System.out.println( v1.getFoo() );
    }

}
