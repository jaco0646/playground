package spring.animal;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.animal.domain.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@RestController
public class AnimalController {
    private final ListableBeanFactory beanContainer;

    AnimalController(ListableBeanFactory beanContainer) {
        this.beanContainer = beanContainer;
    }

    @PostMapping("/animal")
    public String postAnimal(@RequestBody Animal animal) {
        String animalType = animal.getClass().getSimpleName();
        String gift = findAnimalServices(animal).stream()
                .map(service -> service.apply(animal))
                .collect(joining());
        return "Your " + animalType + " has brought you a " + gift + ".";
    }

    <T extends Animal> List<Function<T, String>> findAnimalServices(T animal) {
        ResolvableType type = ResolvableType
            .forClassWithGenerics(Function.class, animal.getClass(), String.class);
        List<Function<T, String>> services = new ArrayList<>();
        for (String name : beanContainer.getBeanNamesForType(type)) {
            services.add(beanContainer.getBean(name, Function.class));
        }
        return services;
    }
}
