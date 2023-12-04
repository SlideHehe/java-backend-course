package edu.hw7.task3;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonDatabaseTest {
    static Stream<Arguments> personDatabaseProvider() {
        return Stream.of(
            Arguments.of(new SynchronizedPersonDatabase()),
            Arguments.of(new ReadWriteLockPersonDatabase())
        );
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка при нескольких потока")
    void concurrentTest(PersonDatabase personDatabase) {
        // given
        int numOfThreads = 5;
        int iterationsPerThread = 1_000_000;
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        // expect
        try (ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads)) {
            for (int i = 0; i < numOfThreads; i++) {
                int currentThread = i;
                executorService.submit(() -> {
                    try {
                        for (int j = 0; j < iterationsPerThread; j++) {
                            Person person = new Person(currentThread * j + j, "Arthur Morgan", "Aboba Street", "123");

                            personDatabase.add(person);

                            assertThat(personDatabase.findByName(person.name())).contains(person);
                            assertThat(personDatabase.findByAddress(person.address())).contains(person);
                            assertThat(personDatabase.findByPhone(person.phoneNumber())).contains(person);

                            personDatabase.delete(person.id());
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executorService.shutdown();
        }

        List<Person> actual = personDatabase.findByName("Arthur Morgan");
        assertThat(actual).isEmpty();
        actual = personDatabase.findByAddress("Aboba Street");
        assertThat(actual).isEmpty();
        actual = personDatabase.findByPhone("123");
        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка метода add")
    void add(PersonDatabase personDatabase) {
        // given
        Person person = new Person(1, "Arthur Morgan", "Aboba Street", "123");

        // when
        personDatabase.add(person);

        // then
        assertThat(personDatabase.findByName("Arthur Morgan")).containsExactly(person);
        assertThat(personDatabase.findByAddress("Aboba Street")).containsExactly(person);
        assertThat(personDatabase.findByPhone("123")).containsExactly(person);
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка метода add c недопустимым Person")
    void addInvalidPerson(PersonDatabase personDatabase) {
        // given
        Person invalidPerson = new Person(0, "", "", "");

        // when
        personDatabase.add(invalidPerson);

        // then
        assertThat(personDatabase.findByName("")).isEmpty();
        assertThat(personDatabase.findByAddress("")).isEmpty();
        assertThat(personDatabase.findByPhone("")).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка метода delete")
    void delete(PersonDatabase personDatabase) {
        // given
        Person person = new Person(1, "Arthur Morgan", "Aboba Street", "123");
        personDatabase.add(person);

        // when
        personDatabase.delete(1);

        // then
        assertThat(personDatabase.findByName("Arthur Morgan")).isEmpty();
        assertThat(personDatabase.findByAddress("Aboba Street")).isEmpty();
        assertThat(personDatabase.findByPhone("123")).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка поиска несуществующего человека")
    void findByUnexistingName(PersonDatabase personDatabase) {
        // when
        List<Person> result = personDatabase.findByName("somerandomstuff");

        // then
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка добавление null вместо Person")
    void addNullPerson(PersonDatabase personDatabase) {
        // expect
        assertThatThrownBy(() -> personDatabase.add(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка, что ничего не происходит при попытке удалить персон с несуществующим id")
    void deleteIgnoreUnexistingId(PersonDatabase personDatabase) {
        // expect
        assertThatCode(() -> personDatabase.delete(1))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("personDatabaseProvider")
    @DisplayName("Проверка передачи null в метод find")
    void findNullPtrEx(PersonDatabase personDatabase) {
        // expect
        assertThatThrownBy(() -> personDatabase.findByName(null))
            .isInstanceOf(NullPointerException.class);
    }
}
