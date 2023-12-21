package edu.hw10.task1.randomclasses;

public record Student(String name, String surname) {
    public static Student create(String name, String surname) {
        return new Student(name, surname);
    }
}
