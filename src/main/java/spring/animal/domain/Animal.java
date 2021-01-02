package spring.animal.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME)
//@JsonTypeIdResolver(AnimalTypeResolver.class)
public interface Animal {
    String speak();
}

// sample payload:
//  {"@type": "doge"}