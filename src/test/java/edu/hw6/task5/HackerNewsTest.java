package edu.hw6.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HackerNewsTest {
    @Test
    @DisplayName("Получение списка айдишников")
    void hackerNewsTopStories() {
        // expect
        assertThat(HackerNews.hackerNewsTopStories()).isNotEmpty();
    }

    @Test
    @DisplayName("Получение названия статьи")
    void news() {
        // expect
        assertThat(HackerNews.news(37570037)).isEqualTo("JDK 21 Release Notes");
    }

    @Test
    @DisplayName("Получение null при передачи несуществующего айдишника")
    void newsInvalidId() {
        // expect
        assertThat(HackerNews.news(-1)).isNull();
    }

}
