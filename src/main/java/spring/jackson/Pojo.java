package spring.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

@Value
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
@JsonFormat(with = ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Pojo {
    String foo;
    LocalDate bar;
    LocalDateTime baz;
}
