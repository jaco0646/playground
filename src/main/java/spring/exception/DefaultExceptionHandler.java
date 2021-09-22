package spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> handleValidationErrors(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(it -> Objects.equals(it.getCode(), AssertTrue.class.getSimpleName())
                        ? it.getDefaultMessage()
                        : it.getField() + ": " + it.getDefaultMessage()
                ).toList();
    }

}
