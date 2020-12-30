package spring.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("doge")
public class Dog implements Animal {
    @Override
    public String speak() {
        return "Woof!";
    }

    public String fetch() {
        return "stick";
    }
}
