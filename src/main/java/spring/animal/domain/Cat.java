package spring.animal.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("kitty")
public class Cat implements Animal {
    @Override
    public String speak() {
        return "Meow.";
    }

    public String pounce() {
        return "mouse";
    }
}
