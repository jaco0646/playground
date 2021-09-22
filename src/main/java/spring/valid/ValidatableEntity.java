package spring.valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Value
@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
class ValidatableEntity {
    @NotEmpty(message = "foo must not be empty")
    String foo;
    @PositiveOrZero(message = "int cannot be negative")  // defaults to zero if no value is passed
    int i;
    @NotNull
    @Future
    LocalDate time;
    @Valid
    List<NestedValidatableEntity> nestedEntity;

    @AssertTrue(message = "Length of foo must equal i.")
    private boolean isValid() {
        return foo.length() == i;
    }

    @Value
    @RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
    static class NestedValidatableEntity {
        @NotEmpty(message = "bar must not be empty")
        String bar;
    }
}
