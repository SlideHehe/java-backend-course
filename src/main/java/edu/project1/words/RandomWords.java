package edu.project1.words;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class RandomWords implements Dictionary {
    private final String[] words;

    public RandomWords(String[] words) {
        if (Objects.isNull(words) || words.length < 1) {
            throw new IllegalArgumentException();
        }

        for (String word : words) {
            if (Objects.isNull(word) || word.isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

        this.words = words;
    }

    @Override
    public @NotNull String randomWord() {
        int maxValue = words.length;
        int randomIdx = (int) (Math.random() * maxValue);

        return words[randomIdx];
    }
}
