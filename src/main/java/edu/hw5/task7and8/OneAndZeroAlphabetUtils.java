package edu.hw5.task7and8;

import java.util.Objects;

public class OneAndZeroAlphabetUtils {
    // Напишите регулярные выражения для строк из алфавита {0, 1}
    private OneAndZeroAlphabetUtils() {
    }

    // task 7: содержит не менее 3 символов, причем третий символ равен 0
    public static boolean containsNotLessThanThreeSymbolsAndThirdIsZero(String string) {
        Objects.requireNonNull(string);

        return string.matches("^[01]{2}0[01]*$");
    }

    // task 7: начинается и заканчивается одним и тем же символом
    public static boolean startsAndEndsWithSameSymbol(String string) {
        Objects.requireNonNull(string);

        return string.matches("^([01])([01]*\\1)?$");
    }

    // task 7: длина не менее 1 и не более 3
    public static boolean isLengthBetweenOneAndThree(String string) {
        Objects.requireNonNull(string);

        return string.matches("^[01]{1,3}$");
    }

    // task 8: нечетной длины
    public static boolean isLengthOdd(String string) {
        Objects.requireNonNull(string);

        return string.matches("^[01]([01][01])*$");
    }

    // task 8: начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    public static boolean isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength(String string) {
        Objects.requireNonNull(string);

        return string.matches("^(1[01]([01][01])*)$|^(0([01][01])*)$");
    }

    // task 8: количество 0 кратно 3
    public static boolean isZerosAreMultipleOfThree(String string) {
        Objects.requireNonNull(string);

        return string.matches("^(1*01*01*01*)+$");
    }

    // task 8: любая строка, кроме 11 или 111
    public static boolean isAnythingExceptTwoOrThreeOnes(String string) {
        Objects.requireNonNull(string);

        return string.matches("^(?!11(1)?$)[01]+$");
    }

    // task 8: каждый нечетный символ равен 1
    public static boolean isEveryOddSymbolIsOne(String string) {
        Objects.requireNonNull(string);

        return string.matches("^1[01]?$|^(1[01])+$|^1([01]?1)*$");
    }

    // task 8: содержит не менее двух 0 и не более одной 1
    public static boolean containsNotLessThanTwoZerosAndNotMoreThanOneOnes(String string) {
        Objects.requireNonNull(string);

        return string.matches("^1?0{2,}$|^01?0+$|^0{2,}1?0*$");
    }

    // task 8: нет последовательных 1
    public static boolean doesntContainsSequentialOnes(String string) {
        Objects.requireNonNull(string);

        return string.matches("^(?![01]*11)[01]+$");
    }
}
