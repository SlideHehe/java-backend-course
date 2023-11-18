package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clusterizer {
    private Clusterizer() {
    }

    public static List<String> clusterize(String brackets) {
        Objects.requireNonNull(brackets);

        StringBuilder stringBuilder = new StringBuilder();
        List<String> clusterizedBrackets = new ArrayList<>();

        int openingBracketsCounter = 0;

        String trimmedBrackets = brackets.replaceAll("\\s+", "");

        for (char symbol : trimmedBrackets.toCharArray()) {
            stringBuilder.append(symbol);

            openingBracketsCounter = updateCounter(symbol, openingBracketsCounter);

            if (openingBracketsCounter == 0) {
                String cluster = stringBuilder.toString();
                clusterizedBrackets.add(cluster);

                stringBuilder.setLength(0);
            }
        }

        if (openingBracketsCounter != 0) { // выбрасывается эксепшн в случае если скобки в строке не сбалансированы
            throw new IllegalArgumentException();
        }

        return clusterizedBrackets;
    }

    private static int updateCounter(char symbol, int counter) {
        switch (symbol) {
            case '(' -> {
                return counter + 1;
            }

            case ')' -> {
                if (counter == 0) { // выбрасывается эксепшн в случае если скобки в строке не сбалансированы
                    throw new IllegalArgumentException();
                }

                return counter - 1;
            }

            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
