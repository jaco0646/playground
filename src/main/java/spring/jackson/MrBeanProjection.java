package spring.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface MrBeanProjection {
    String getFoo();
    String getBar();
    int getBaz();

    @JsonIgnore
    Integer getQuxPrimitive();
    default String getQux() {
        return String.valueOf(getQuxPrimitive());
    }
}
