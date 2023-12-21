package edu.hw10.task1;

import edu.hw10.task1.randomclasses.Empty;
import edu.hw10.task1.randomclasses.Garbage;
import edu.hw10.task1.randomclasses.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomObjectGeneratorTest {

    @Test
    @DisplayName("Создание объекта с пустым конструктором")
    void objectWithEmptyConstructor() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();

        // when
        Empty empty = rog.nextObject(Empty.class);

        // then
        assertThat(empty).isNotNull();
    }

    @Test
    @DisplayName("Создание объекта с конструктором с аргументами")
    void objectWithArgsConstructor() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();

        // when
        Student student = rog.nextObject(Student.class);

        // then
        assertThat(student).isNotNull();
    }

    @Test
    @DisplayName("Создание рекорда при помощи фабричного метода")
    void recordWithFactoryMethod() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();

        // when
        Student student = rog.nextObject(Student.class, "create");

        // then
        assertThat(student).isNotNull();
    }

    @Test
    @DisplayName("Создание объекта без публичных конструкторов")
    void objectWithNoPublicConstructors() {
        // expect
        assertThatThrownBy(() -> new RandomObjectGenerator().nextObject(System.class))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("There are no public constructors");
    }

    @Test
    @DisplayName("Создание объекта с несуществующим фабричным методом")
    void objectWithInvalidMethod() {
        // expect
        assertThatThrownBy(() -> new RandomObjectGenerator().nextObject(Student.class, "nonExistingMethod"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("There are no such factory method");
    }

    @Test
    @DisplayName("Передача null вместо типа класса")
    void nullClassType() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();

        // expect
        assertThatThrownBy(() -> rog.nextObject(null, "hehe")).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> rog.nextObject(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача null вместо названиия фабричного метода")
    void nullFactoryMethod() {
        // expect
        assertThatThrownBy(() -> new RandomObjectGenerator().nextObject(String.class, null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка создания объекта рекорда с аннотациями")
    void objectWithAnnotations() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();

        // when
        Garbage garbage = rog.nextObject(Garbage.class);

        // then
        assertThat(garbage.intValue()).isBetween(1, 3);
        assertThat(garbage.doubleValue()).isLessThan(0.0);
        assertThat(garbage.charValue()).isGreaterThan('a');
        assertThat(garbage.stringValue()).isNotNull();
    }
}
