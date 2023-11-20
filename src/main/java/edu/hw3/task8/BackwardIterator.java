package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BackwardIterator<E> implements Iterator<E> {
    private final E[] collection;
    private int pointer;

    @SuppressWarnings("unchecked") public BackwardIterator(Collection<E> collection) {
        Objects.requireNonNull(collection);
        this.collection = (E[]) collection.toArray();
        this.pointer = collection.size();
    }

    @Override
    public boolean hasNext() {
        return pointer > 0;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return collection[--pointer];
    }
}
