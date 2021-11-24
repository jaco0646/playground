package spring.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
    Three annotations will be added by ByteBuddy,
        when it encounters the @ImmutableEntity annotation.
    1. @Immutable
    2. @Entity
    3. @Table(name = tableName())
*/
@Target(ElementType.TYPE)
public @interface ImmutableEntity {
    String tableName();
}
