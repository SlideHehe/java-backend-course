package edu.hw3.task1;

import java.util.Map;
import java.util.Objects;
import static java.util.Map.entry;

public class Atbash {
    private static final Map<Character, Character> ATBASH_CIPHER = Map.ofEntries(
        entry('a', 'z'),
        entry('b', 'y'),
        entry('c', 'x'),
        entry('d', 'w'),
        entry('e', 'v'),
        entry('f', 'u'),
        entry('g', 't'),
        entry('h', 's'),
        entry('i', 'r'),
        entry('j', 'q'),
        entry('k', 'p'),
        entry('l', 'o'),
        entry('m', 'n'),
        entry('n', 'm'),
        entry('o', 'l'),
        entry('p', 'k'),
        entry('q', 'j'),
        entry('r', 'i'),
        entry('s', 'h'),
        entry('t', 'g'),
        entry('u', 'f'),
        entry('v', 'e'),
        entry('w', 'd'),
        entry('x', 'c'),
        entry('y', 'b'),
        entry('z', 'a')
    );

    private Atbash() {
    }

    public static String atbash(String message) {
        Objects.requireNonNull(message);

        StringBuilder stringBuilder = new StringBuilder();

        for (char symbol : message.toCharArray()) {
            stringBuilder.append(processSymbol(symbol));
        }

        return stringBuilder.toString();
    }

    private static char processSymbol(char symbol) {
        if (!Character.isLetter(symbol)) {
            return symbol;
        }

        if (Character.isLowerCase(symbol)) {
            return processLowerCaseSymbol(symbol);
        }

        char lowerCaseSymbol = Character.toLowerCase(symbol);
        lowerCaseSymbol = processLowerCaseSymbol(lowerCaseSymbol);

        return Character.toUpperCase(lowerCaseSymbol);
    }

    private static char processLowerCaseSymbol(char symbol) {
        if (ATBASH_CIPHER.containsKey(symbol)) {
            return ATBASH_CIPHER.get(symbol);
        }

        return symbol;
    }
}
