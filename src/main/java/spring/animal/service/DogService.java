package spring.animal.service;

import org.springframework.stereotype.Component;
import spring.animal.domain.Dog;

@Component
public class DogService implements AnimalService<Dog> {
    @Override
    public String apply(Dog dog) {
        return dog.fetch();
    }
}
