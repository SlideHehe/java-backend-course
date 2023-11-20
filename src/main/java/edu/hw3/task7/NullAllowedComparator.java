package edu.hw3.task7;

import java.util.Comparator;
import java.util.TreeMap;

public class NullAllowedComparator {
    private NullAllowedComparator() {

    }

    public static <T extends Comparable<? super T>> Comparator<T> getNullAllowedComparator() {
        return Comparator.nullsFirst(Comparator.naturalOrder());
    }

    public static <K extends Comparable<? super K>, V> TreeMap<K, V> getNullAllowedTreeMap() {
        return new TreeMap<>(getNullAllowedComparator());
    }
}
