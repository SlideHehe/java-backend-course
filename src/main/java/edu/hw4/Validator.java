package edu.hw4;

import java.util.Objects;

public enum Validator {
    NAME_NOT_NULL("Name error -> can't be null") {
        @Override
        public boolean validate(Animal animal) {
            return Objects.nonNull(animal.name());
        }
    },
    NAME_NOT_EMPTY("Name error -> can't be empty") {
        @Override
        public boolean validate(Animal animal) {
            if (Objects.isNull(animal.name())) {
                return false;
            }

            String nameWithoutWhitespaces = animal.name().replaceAll("\\s+", "");
            return !nameWithoutWhitespaces.isEmpty();
        }
    },
    TYPE_IS_PRESENT("Type error -> can't be absent") {
        @Override
        public boolean validate(Animal animal) {
            return Objects.nonNull(animal.type());
        }
    },
    SEX_IS_PRESENT("Sex error -> can't be absent") {
        @Override
        public boolean validate(Animal animal) {
            return Objects.nonNull(animal.sex());
        }
    },
    AGE_GREATER_THAN_OR_EQUAL_TO_ZERO("Age error -> can't be under 0") {
        @Override
        public boolean validate(Animal animal) {
            return animal.age() >= 0;
        }
    },
    WEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO("Weight error -> can't be under 0") {
        @Override
        public boolean validate(Animal animal) {
            return animal.weight() >= 0;
        }
    },
    HEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO("Height error -> can't be under 0") {
        @Override
        public boolean validate(Animal animal) {
            return animal.height() >= 0;
        }
    };

    final ValidationError error;

    Validator(String errorMessage) {
        error = new ValidationError(errorMessage);
    }

    public abstract boolean validate(Animal animal);
}
