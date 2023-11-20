package edu.hw4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class AnimalsUtils {
    public static final String FORMAT_DELIMITER = ";\n";
    private static final int MIN_HEIGHT = 100;
    private static final int MIN_WORDS = 3;

    private AnimalsUtils() {
    }

    // task 1
    public static List<Animal> sortByHeightAsc(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    // task 2
    public static List<Animal> sortByWeightDesc(List<Animal> animals, int k) {
        Objects.requireNonNull(animals);

        if (k <= 0 || k > animals.size()) {
            throw new IllegalArgumentException();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    // task 3
    public static Map<Animal.Type, Integer> countTypes(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .collect(
                Collectors.groupingBy(
                    Animal::type, Collectors.summingInt(e -> 1)
                )
            );
    }

    // task 4
    public static Animal getLongestName(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(animal -> Objects.nonNull(animal) && Objects.nonNull(animal.name()))
            .max(Comparator.comparingInt(animal -> animal.name().length()))
            .orElse(null);
    }

    // task 5
    public static Animal.Sex compareSexes(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Animal.Sex.F);
    }

    // task 6
    public static Map<Animal.Type, Animal> getHeaviestAnimalInEachType(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                Animal::type,
                animal -> animal,
                BinaryOperator.maxBy(Comparator.comparingInt(Animal::weight))
            ));
    }

    // task 7
    public static Animal getKthOldestAnimal(List<Animal> animals, int k) {
        Objects.requireNonNull(animals);

        if (k <= 0 || k > animals.size()) {
            throw new IllegalArgumentException();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    // task 8
    public static Optional<Animal> getHeaviestAnimalBelowK(List<Animal> animals, int k) {
        Objects.requireNonNull(animals);

        if (k < 0) {
            throw new IllegalArgumentException();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    // task 9
    public static Integer countPaws(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .mapToInt(Animal::paws)
            .sum();
    }

    // task 10
    public static List<Animal> getAnimalsWithUnequalAgeAndPaws(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    // task 11
    public static List<Animal> getAnimalsThatBitesAndHigherThan100cm(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .filter(animal -> animal.bites() && animal.height() > MIN_HEIGHT)
            .collect(Collectors.toList());
    }

    // task 12
    public static List<Animal> getAnimalsWithWeightGreaterThanHeight(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .filter(animal -> animal.weight() > animal.height())
            .collect(Collectors.toList());
    }

    // task 13
    public static List<Animal> getAnimalsWithComplexName(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(animal -> Objects.nonNull(animal) && Objects.nonNull(animal.name()))
            .filter(animal -> animal.name().split(" ").length >= MIN_WORDS)
            .collect(Collectors.toList());
    }

    // task 14
    public static Boolean containsDogHigherThanK(List<Animal> animals, int k) {
        Objects.requireNonNull(animals);

        if (k < 0) {
            throw new IllegalArgumentException();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    // task 15
    public static Map<Animal.Type, Integer> countWeightByTypes(List<Animal> animals, int k, int l) {
        Objects.requireNonNull(animals);

        if (k < 0 || l <= k) {
            throw new IllegalArgumentException();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .filter(animal -> animal.age() >= k && animal.age() < l)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(Animal::weight)
            ));
    }

    // task 16
    public static List<Animal> sortByTypeThenBySexThenByName(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(
                animal -> Objects.nonNull(animal)
                    && Objects.nonNull(animal.type())
                    && Objects.nonNull(animal.name())
                    && Objects.nonNull(animal.sex())
            )
            .sorted(
                Comparator.comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name)
            )
            .collect(Collectors.toList());
    }

    // task 17
    public static Boolean isSpidersBitingMoreThanDogs(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(
                animal -> Objects.nonNull(animal)
                    && animal.bites()
                    && (animal.type() == Animal.Type.DOG || animal.type() == Animal.Type.SPIDER)
            )
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(e -> 1)))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .equals(Optional.of(Animal.Type.SPIDER));
    }

    // task 18
    @SafeVarargs public static Animal getHeaviestFish(List<Animal>... animals) {
        Objects.requireNonNull(animals);

        return Arrays.stream(animals)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }

    // task 19
    public static Map<String, Set<ValidationError>> getAnimalsErrors(List<Animal> animals) {
        Objects.requireNonNull(animals);

        return animals.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                Animal::name,
                AnimalValidator::getErrors
            ));
    }

    // task 20
    public static Map<String, String> getAnimalsErrorsFormatted(List<Animal> animals) {
        return getAnimalsErrors(animals)
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                    .map(ValidationError::toString)
                    .sorted()
                    .collect(Collectors.joining(FORMAT_DELIMITER))
            ));
    }
}
