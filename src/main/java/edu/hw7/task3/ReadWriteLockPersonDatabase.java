package edu.hw7.task3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();

    private final Map<Integer, Person> personMap = new HashMap<>();
    private final Map<String, Set<Integer>> nameIndex = new HashMap<>();
    private final Map<String, Set<Integer>> addressIndex = new HashMap<>();
    private final Map<String, Set<Integer>> phoneIndex = new HashMap<>();

    @Override
    public void add(Person person) {
        Objects.requireNonNull(person);

        if (!isPersonValid(person)) {
            return;
        }

        delete(person.id());

        try {
            writeLock.lock();

            personMap.put(person.id(), person);

            nameIndex.computeIfAbsent(person.name(), s -> new HashSet<>())
                .add(person.id());
            addressIndex.computeIfAbsent(person.address(), s -> new HashSet<>())
                .add(person.id());
            phoneIndex.computeIfAbsent(person.phoneNumber(), s -> new HashSet<>())
                .add(person.id());
        } finally {
            writeLock.unlock();
        }

    }

    private boolean isPersonValid(Person person) {
        Objects.requireNonNull(person);

        if (person.id() < 0) {
            throw new IllegalArgumentException("Id can't be less than zero");
        }

        return !Objects.isNull(person.name()) && !person.name().isBlank()
            && !Objects.isNull(person.address()) && !person.address().isBlank()
            && !Objects.isNull(person.phoneNumber()) && !person.phoneNumber().isBlank();
    }

    @Override
    public void delete(int id) {
        try {
            writeLock.lock();

            if (!personMap.containsKey(id)) {
                return;
            }
            Person personToDelete = personMap.get(id);
            nameIndex.get(personToDelete.name()).remove(id);
            addressIndex.get(personToDelete.address()).remove(id);
            phoneIndex.get(personToDelete.phoneNumber()).remove(id);
            personMap.remove(id);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public List<Person> findByName(String name) {
        Objects.requireNonNull(name);

        try {
            readLock.lock();

            if (name.isBlank() || !nameIndex.containsKey(name)) {
                return List.of();
            }

            return nameIndex.get(name).stream()
                .map(personMap::get)
                .toList();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        Objects.requireNonNull(address);

        try {
            readLock.lock();

            if (address.isBlank() || !addressIndex.containsKey(address)) {
                return List.of();
            }

            return addressIndex.get(address).stream()
                .map(personMap::get)
                .toList();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        Objects.requireNonNull(phone);

        try {
            readLock.lock();

            if (phone.isBlank() || !phoneIndex.containsKey(phone)) {
                return List.of();
            }

            return phoneIndex.get(phone).stream()
                .map(personMap::get)
                .toList();
        } finally {
            readLock.unlock();
        }
    }
}
