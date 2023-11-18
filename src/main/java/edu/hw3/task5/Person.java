package edu.hw3.task5;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Person implements Comparable<Person> {
    private static final String WHITESPACE_REGEX = "\\s+";
    private final String name;
    private final String surname;

    private Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static Person of(String person) {
        if (Objects.isNull(person) || person.replaceAll(WHITESPACE_REGEX, "").isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] fullName = person.trim().split(" ");

        String name;
        String surname = null;

        if (fullName.length == 2) {
            surname = fullName[1].replaceAll(WHITESPACE_REGEX, "");
        } else if (fullName.length != 1) {
            throw new IllegalArgumentException();
        }

        name = fullName[0].replaceAll(WHITESPACE_REGEX, "");

        return new Person(name, surname);
    }

    @Override
    public int compareTo(@NotNull Person o) {
        String thisToCompare = Objects.isNull(this.surname) ? this.name : this.surname;
        String oToCompare = Objects.isNull(o.surname) ? o.name : o.surname;

        return thisToCompare.compareTo(oToCompare);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
