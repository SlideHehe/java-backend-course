package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnimalsUtilsTest {
    static Stream<Arguments> basicAnimalsListProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    new Animal("Meower", Animal.Type.CAT, Animal.Sex.F, 4, 50, 5, true),
                    new Animal("Barker", Animal.Type.DOG, Animal.Sex.M, 10, 110, 120, true),
                    new Animal("Sam Fisher", Animal.Type.FISH, Animal.Sex.M, 3, 10, 2, true)
                )
            )
        );
    }

    static Stream<Arguments> extendedAnimalsListProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    new Animal("Meower", Animal.Type.CAT, Animal.Sex.F, 4, 50, 5, true),
                    new Animal("Mr. Dog Barker", Animal.Type.DOG, Animal.Sex.M, 10, 110, 120, true),
                    new Animal("Sam Fisher", Animal.Type.FISH, Animal.Sex.M, 3, 10, 2, true),
                    new Animal("Bubbler", Animal.Type.FISH, Animal.Sex.F, 2, 5, 1, false),
                    new Animal("Ms. Dog Barker", Animal.Type.DOG, Animal.Sex.M, 8, 80, 7, true)
                )
            )
        );
    }

    static Stream<Arguments> animalsWithErrorsProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    new Animal("Morkovka", Animal.Type.CAT, Animal.Sex.F, 5, 70, -5, false),
                    new Animal("", Animal.Type.CAT, null, 5, -1, 5, false),
                    new Animal(null, null, Animal.Sex.F, -5, 70, 5, false)
                )
            )
        );
    }

    @Test
    @DisplayName("Проверка передачи null")
    void passNull() {
        // expect
        assertThatThrownBy(() -> AnimalsUtils.sortByHeightAsc(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка передачи пустого списка")
    void passEmptyList() {
        // given
        List<Animal> animals = List.of();

        // when
        List<Animal> actual = AnimalsUtils.sortByHeightAsc(animals);

        // then
        assertThat(actual).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("Проверка передачи списка с null")
    void passListWithNulls() {
        // given
        List<Animal> animals = Arrays.asList(null, null, null);

        // when
        List<Animal> actual = AnimalsUtils.sortByHeightAsc(animals);

        // then
        assertThat(actual).isEqualTo(Collections.emptyList());
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Сортировка по росту по возрастанию")
    void sortByHeightAsc(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.sortByHeightAsc(animals);

        // then
        List<Animal> expected = List.of(
            animals.get(2),
            animals.get(0),
            animals.get(1)
        );
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Недопустимое k при сортировке по весу")
    void sortByWeightInvalidK(List<Animal> animals) {
        // expect
        assertThatThrownBy(() -> AnimalsUtils.sortByWeightDesc(
            animals,
            4
        )).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> AnimalsUtils.sortByWeightDesc(
            animals,
            0
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Сортировка по весу по убыванию")
    void sortByWeightDesc(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.sortByWeightDesc(animals, 2);

        // then
        List<Animal> expected = List.of(
            animals.get(1),
            animals.get(0)
        );
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("extendedAnimalsListProvider")
    @DisplayName("Подсчет типов животных")
    void countTypes(List<Animal> animals) {
        // when
        Map<Animal.Type, Integer> actual = AnimalsUtils.countTypes(animals);

        // then
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(
            Map.of(
                Animal.Type.CAT, 1,
                Animal.Type.DOG, 2,
                Animal.Type.FISH, 2
            )
        );
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение самого длинного имени")
    void getLongestName(List<Animal> animals) {
        // when
        Animal actual = AnimalsUtils.getLongestName(animals);

        // then
        assertThat(actual).isEqualTo(animals.get(2));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Сравнение полов")
    void compareSexes(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.compareSexes(animals)).isEqualTo(Animal.Sex.M);
    }

    @ParameterizedTest
    @MethodSource("extendedAnimalsListProvider")
    @DisplayName("Получение самых тяжелых животных каждого вида")
    void getHeaviestAnimalInEachType(List<Animal> animals) {
        // when
        Map<Animal.Type, Animal> actual = AnimalsUtils.getHeaviestAnimalInEachType(animals);

        // then
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(
            Map.of(
                Animal.Type.CAT, animals.get(0),
                Animal.Type.DOG, animals.get(1),
                Animal.Type.FISH, animals.get(2)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение k-ого старого животного")
    void getKthOldestAnimal(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.getKthOldestAnimal(animals, 2)).isEqualTo(animals.get(0));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение самого тяжелого животного ниже k")
    void getHeaviestAnimalBelowK(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.getHeaviestAnimalBelowK(animals, 100).orElse(null))
            .isEqualTo(animals.get(0));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Подсчет лапок")
    void countPaws(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.countPaws(animals)).isEqualTo(8);
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение животных, у которых возраст не совпадает с количеством лап")
    void getAnimalsWithUnequalAgeAndPaws(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.getAnimalsWithUnequalAgeAndPaws(animals);

        // then
        assertThat(actual).containsExactly(animals.get(1), animals.get(2));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение животных с ростом выше 100 см и которые кусаются")
    void getAnimalsThatBitesAndHigherThan100cm(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.getAnimalsThatBitesAndHigherThan100cm(animals);

        // then
        assertThat(actual).containsExactly(animals.get(1));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Получение животных с ростом весом превышающим рост")
    void getAnimalsWithWeightGreaterThanHeight(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.getAnimalsWithWeightGreaterThanHeight(animals);

        // then
        assertThat(actual).containsExactly(animals.get(1));
    }

    @ParameterizedTest
    @MethodSource("extendedAnimalsListProvider")
    @DisplayName("Получение животных с именем, состоящим больше чем из двух слов")
    void getAnimalsWithComplexName(List<Animal> animals) {
        // when
        List<Animal> actual = AnimalsUtils.getAnimalsWithComplexName(animals);

        // then
        assertThat(actual).containsExactly(animals.get(1), animals.get(4));
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Содержится ли в списке собака, ростом больше 100")
    void containsDogHigherThan100(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.containsDogHigherThanK(animals, 100)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("basicAnimalsListProvider")
    @DisplayName("Содержится ли в списке собака, ростом больше 1000")
    void containsDogHigherThan1000(List<Animal> animals) {
        // expect
        assertThat(AnimalsUtils.containsDogHigherThanK(animals, 1000)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("extendedAnimalsListProvider")
    @DisplayName("Нахождение суммарного веса животных каждого вида, которым от 2 до 9 лет")
    void countWeightByTypes(List<Animal> animals) {
        // when
        Map<Animal.Type, Integer> actual = AnimalsUtils.countWeightByTypes(animals, 2, 9);

        // then
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(
            Map.of(
                Animal.Type.CAT, 5,
                Animal.Type.DOG, 7,
                Animal.Type.FISH, 3
            )
        );
    }

    @Test
    @DisplayName("Сортировка по виду, затем по полу, затем по имени")
    void sortByTypeThenBySexThenByName() {
        // given
        Animal animal1 = new Animal("Meower2", Animal.Type.CAT, Animal.Sex.F, 10, 10, 10, true);
        Animal animal2 = new Animal("Meower1", Animal.Type.CAT, Animal.Sex.F, 10, 10, 10, true);
        Animal animal3 = new Animal("Dogges", Animal.Type.DOG, Animal.Sex.F, 10, 10, 10, true);
        Animal animal4 = new Animal("Dogges", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true);
        Animal animal5 = new Animal("Meower4", Animal.Type.CAT, Animal.Sex.M, 10, 10, 10, true);
        Animal animal6 = new Animal("Meower3", Animal.Type.CAT, Animal.Sex.M, 10, 10, 10, true);
        List<Animal> animals = List.of(animal1, animal2, animal3, animal4, animal5, animal6);

        System.out.println(animal5.name().compareTo(animal6.name()));

        // when
        List<Animal> actual = AnimalsUtils.sortByTypeThenBySexThenByName(animals);

        // then
        assertThat(actual).containsExactly(animal6, animal5, animal2, animal1, animal4, animal3);
    }

    @Test
    @DisplayName("Кусаются ли пауки больше чем собаки (пауков > собак)")
    void isSpidersBitingMoreThanDogs() {
        // given
        List<Animal> animals = List.of(
            new Animal("Solid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Liquid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, false),
            new Animal("Naked snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Solidus snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Doggo", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, false),
            new Animal("Dogge", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Doggu", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true)
        );

        // when
        boolean isSpidersBitingMoreThanDogs = AnimalsUtils.isSpidersBitingMoreThanDogs(animals);

        // then
        assertThat(isSpidersBitingMoreThanDogs).isTrue();
    }

    @Test
    @DisplayName("Кусаются ли пауки больше чем собаки (пауков > собак)")
    void isSpidersBitingLessThanDogs() {
        // given
        List<Animal> animals = List.of(
            new Animal("Solid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Liquid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, false),
            new Animal("Dogge", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Doggu", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true)
        );

        // when
        boolean isSpidersBitingMoreThanDogs = AnimalsUtils.isSpidersBitingMoreThanDogs(animals);

        // then
        assertThat(isSpidersBitingMoreThanDogs).isFalse();
    }

    @Test
    @DisplayName("Поиск самой тяжелой рыбки из трех списков")
    void getHeaviestFish() {
        // given
        Animal heaviestFish = new Animal("Sam Fisher", Animal.Type.FISH, Animal.Sex.M, 3, 10, 9, true);
        List<Animal> list1 = List.of(
            new Animal("Doggu", Animal.Type.DOG, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Solid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true),
            new Animal("Bubbler1", Animal.Type.FISH, Animal.Sex.F, 2, 5, 1, false)

        );
        List<Animal> list2 = List.of(
            heaviestFish,
            new Animal("Solid snake", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 10, true)
        );
        List<Animal> list3 = List.of(
            new Animal("Bubbler2", Animal.Type.FISH, Animal.Sex.M, 2, 3, 6, false),
            new Animal("Bubbler3", Animal.Type.FISH, Animal.Sex.F, 2, 1, 4, true)
        );

        // when
        Animal actual = AnimalsUtils.getHeaviestFish(list1, list2, list3);

        // then
        assertThat(actual).isEqualTo(heaviestFish);
    }

    @ParameterizedTest
    @MethodSource("animalsWithErrorsProvider")
    @DisplayName("Получения сета ошибок")
    void getAnimalsErrors(List<Animal> animals) {
        // when
        Map<String, Set<ValidationError>> actual = AnimalsUtils.getAnimalsErrors(animals);

        // then
        assertThat(actual.get("Morkovka")).containsExactlyInAnyOrder(
            Validator.WEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO.error
        );
        assertThat(actual.get("")).containsExactlyInAnyOrder(
            Validator.SEX_IS_PRESENT.error,
            Validator.HEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO.error,
            Validator.NAME_NOT_EMPTY.error
        );
        assertThat(actual.get(null)).containsExactlyInAnyOrder(
            Validator.NAME_NOT_NULL.error,
            Validator.NAME_NOT_EMPTY.error,
            Validator.TYPE_IS_PRESENT.error,
            Validator.AGE_GREATER_THAN_OR_EQUAL_TO_ZERO.error
        );
    }

    @ParameterizedTest
    @MethodSource("animalsWithErrorsProvider")
    @DisplayName("Получения форматированной строки ошибок")
    void getAnimalsErrorsFormatted(List<Animal> animals) {
        // when
        Map<String, String> actual = AnimalsUtils.getAnimalsErrorsFormatted(animals);

        // then
        Map<String, String> expected = new HashMap<>() {{
            put("Morkovka", String.join(AnimalsUtils.FORMAT_DELIMITER, List.of(
                Validator.WEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO.error.toString()
            )));

            put("", String.join(AnimalsUtils.FORMAT_DELIMITER, List.of(
                Validator.HEIGHT_GREATER_THAN_OR_EQUAL_TO_ZERO.error.toString(),
                Validator.NAME_NOT_EMPTY.error.toString(),
                Validator.SEX_IS_PRESENT.error.toString()
            )));
            put(null, String.join(AnimalsUtils.FORMAT_DELIMITER, List.of(
                Validator.AGE_GREATER_THAN_OR_EQUAL_TO_ZERO.error.toString(),
                Validator.NAME_NOT_EMPTY.error.toString(),
                Validator.NAME_NOT_NULL.error.toString(),
                Validator.TYPE_IS_PRESENT.error.toString()
            )));
        }};

        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
    }
}
