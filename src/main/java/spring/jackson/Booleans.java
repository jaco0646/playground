package spring.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
public class Booleans {

    boolean isPrimitive;

    Boolean isWrapper;

    @Accessors(fluent = true)
    Boolean isFluent;

    @JsonProperty
    Boolean isJsonProperty;

    @JsonProperty
    @Accessors(fluent = true)
    Boolean isFluentProperty;
}
