package spring.valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

// @GroupSequence does _not_ recurse into @Valid fields.
// Alternatively, @Validated can be used wherever this class is a method param.
@GroupSequence({AssertTrue.class, ValidatableEntity.class})
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

    @AssertTrue(groups = AssertTrue.class, message = ">>> Custom Time Must Not Be Null Custom <<<")
    private boolean isValid2() {
        return time != null;
    }

    @Value
    @RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
    static class NestedValidatableEntity {
        @NotEmpty(message = "bar must not be empty")
        String bar;
    }
}
