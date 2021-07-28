package spring.feign;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Inherited
@interface FeignHeaders {
    FeignHeader[] value();
}

@Repeatable(FeignHeaders.class)
@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Inherited
@interface FeignHeader {
    String name();
    String value();
}
