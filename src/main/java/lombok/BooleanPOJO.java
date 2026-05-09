package lombok;

import lombok.experimental.Accessors;

@Value
public class BooleanPOJO {
    @Accessors(fluent = true)
    Boolean isObject;
    boolean isPrimitive;
}
