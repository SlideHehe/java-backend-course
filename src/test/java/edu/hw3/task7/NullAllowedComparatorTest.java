package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

public class NullAllowedComparatorTest {
    @DisplayName("Проверка получения компаратора, который позволяет сравнивать null")
    @Test
    void getNullAllowedComparator() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(NullAllowedComparator.getNullAllowedComparator());

        // when
        tree.put(null, "test");
        tree.put("1", "1");
        tree.put("2", "2");
        tree.put("3", "3");

        // then
        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo("test");
    }

    @DisplayName("Проверка получения TreeMap, которое позволяет добавлять null")
    @Test
    void getNullAllowedTreeMap() {
        // given
        TreeMap<Double, Double> tree = NullAllowedComparator.getNullAllowedTreeMap();

        // when
        tree.put(null, 0.0);
        tree.put(1.1, 1.1);
        tree.put(2.2, 2.2);
        tree.put(3.3, 3.3);

        // then
        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo(0.0);
    }
}
