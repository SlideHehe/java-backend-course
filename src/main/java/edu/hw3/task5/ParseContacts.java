package edu.hw3.task5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class ParseContacts {
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";

    private ParseContacts() {
    }

    public static Person[] parseContacts(String[] persons, String order) {

        if (Objects.isNull(persons) || persons.length == 0) {
            return new Person[0];
        }

        Comparator<Person> comparator = getComparator(order);

        int length = persons.length;

        Person[] contacts = new Person[length];

        for (int i = 0; i < length; i++) {
            contacts[i] = Person.of(persons[i]);
        }

        Arrays.sort(contacts, comparator);

        return contacts;
    }

    private static Comparator<Person> getComparator(String order) {
        Comparator<Person> comparator;

        switch (order) {
            case ASC -> {
                comparator = Comparator.naturalOrder();
            }
            case DESC -> {
                comparator = Comparator.reverseOrder();
            }
            case null, default -> {
                throw new IllegalArgumentException();
            }
        }

        return comparator;
    }
}
