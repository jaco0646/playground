package spring.valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ValidationController {

    @PostMapping("/valid")
    @ResponseStatus(CREATED)
    public void createV1(@Valid @RequestBody V1 v1) {
        System.out.println( v1.foo );
    }

    @Value
    @RequiredArgsConstructor(onConstructor_={@JsonCreator})
    static class V1 {
        @NotEmpty(message = "foo must not be empty")
        String foo;
        @PositiveOrZero(message = "int cannot be null")  // defaults to zero if no value is passed
        int i;
        @NotNull
        @Future
        LocalDate time;
        @Valid
        List<V1> v1;
    }
}
