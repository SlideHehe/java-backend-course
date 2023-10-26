package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FreqDict {
    private FreqDict() {
    }

    public static <T> Map<T, Integer> freqDict(List<T> list) {
        Objects.requireNonNull(list);

        Map<T, Integer> freqMap = new HashMap<>();

        for (T elem : list) {
            Objects.requireNonNull(elem);

            if (freqMap.containsKey(elem)) {
                int freq = freqMap.get(elem) + 1;
                freqMap.put(elem, freq);
            } else {
                freqMap.put(elem, 1);
            }
        }

        return freqMap;
    }
}
