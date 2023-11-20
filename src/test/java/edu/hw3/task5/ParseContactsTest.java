package edu.hw3.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParseContactsTest {
    static Stream<Arguments> parseContactValuesProvider() {
        return Stream.of(
            Arguments.of(
                new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                "ASC",
                new Person[] {
                    Person.of("Thomas Aquinas"),
                    Person.of("Rene Descartes"),
                    Person.of("David Hume"),
                    Person.of("John Locke")
                }
            ),
            Arguments.of(
                new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"},
                "DESC",
                new Person[] {
                    Person.of("Carl Gauss"),
                    Person.of("Leonhard Euler"),
                    Person.of("Paul Erdos")
                }
            ),
            Arguments.arguments(
                new String[] {"John Locke", "Alex", "Alex Locle", "Thomas Aquinas"},
                "ASC",
                new Person[] {
                    Person.of("Alex"),
                    Person.of("Thomas Aquinas"),
                    Person.of("John Locke"),
                    Person.of("Alex Locle")
                }
            )
        );
    }

    @DisplayName("Проверка недопустимых аргументов Person")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"one two three", "\n\t", " "})
    void invalidPersonValuesTest(String person) {
        // expect
        assertThatThrownBy(() -> Person.of(person)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка получения пустого массива")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyArrayTest(String[] people) {
        // expect
        assertThat(ParseContacts.parseContacts(people, "ASC")).containsExactly();
    }

    @DisplayName("Проверка недопустимых аргументов order")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"ABC", "DSC"})
    void invalidParseContactsArgs(String order) {
        // given
        String[] people = new String[] {"a", "b"};

        // expect
        assertThatThrownBy(
            () -> ParseContacts.parseContacts(people, order)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка правильности работы parseContacts")
    @ParameterizedTest
    @MethodSource("parseContactValuesProvider")
    void parseContactValuesTest(String[] people, String order, Person[] expected) {
        // expect
        assertThat(ParseContacts.parseContacts(people, order)).isEqualTo(expected);
    }

}
