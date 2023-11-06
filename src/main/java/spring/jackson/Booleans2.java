package spring.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.Accessors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Value
@JsonAutoDetect(fieldVisibility = ANY, isGetterVisibility = NONE)
public class Booleans2 {
    boolean isPrimitive;

    Boolean isWrapper;

    @Accessors(fluent = true)
    Boolean isFluent;
}
