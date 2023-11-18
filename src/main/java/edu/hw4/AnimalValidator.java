package edu.hw4;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalValidator {
    private AnimalValidator() {
    }

    public static Set<ValidationError> getErrors(Animal animal) {
        Objects.requireNonNull(animal);

        return Arrays.stream(Validator.values())
            .filter(validator -> !validator.validate(animal))
            .map(validator -> validator.error)
            .collect(Collectors.toSet());
    }
}
