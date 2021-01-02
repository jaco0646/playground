package spring.animal.service;

import spring.animal.domain.Animal;

import java.util.function.Function;

public interface AnimalService<T extends Animal> extends Function<T, String> {}
