package spring.animal.service;

import org.springframework.stereotype.Component;
import spring.animal.domain.Cat;

@Component
public class CatService implements AnimalService<Cat> {
    @Override
    public String apply(Cat cat) {
        return cat.pounce();
    }
}
