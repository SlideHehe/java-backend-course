package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BackwardIteratorTest {
    static Stream<Arguments> listsProvider() {
        return Stream.of(
            Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)),
            Arguments.of(List.of(Integer.MAX_VALUE))
        );
    }

    @DisplayName("Проверка на передачу null в конструктор BackwardIterator")
    @Test
    void nullArgCheck() {
        // expect
        assertThatThrownBy(() -> new BackwardIterator<>(null)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Проверка на создание итератора для пустой коллекции")
    @Test
    void emptyCollectionTest() {
        // given
        BackwardIterator<Integer> backwardIterator = new BackwardIterator<>(List.of());

        // when
        boolean hasNext = backwardIterator.hasNext();

        // then
        assertThat(hasNext).isFalse();
    }

    @DisplayName("Проверка выхода за границы коллекции")
    @Test
    void outOfBoundsTest() {
        // given
        BackwardIterator<Integer> backwardIterator = new BackwardIterator<>(List.of(1, 2, 3));

        // when
        backwardIterator.next();
        backwardIterator.next();
        backwardIterator.next();

        // then
        assertThatThrownBy(backwardIterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("Проверка работоспособности BackwardIterator")
    @ParameterizedTest
    @MethodSource("listsProvider")
    void backwardIteratorCheck(List<Integer> list) {
        // given
        BackwardIterator<Integer> backwardIterator = new BackwardIterator<>(list);

        for (int i = list.size() - 1; i >= 0; i--) {
            // when
            Integer actual = backwardIterator.next();

            // then
            assertThat(actual).isEqualTo(list.get(i));
        }
    }
}
