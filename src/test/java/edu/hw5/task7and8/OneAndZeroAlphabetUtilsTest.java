package edu.hw5.task7and8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OneAndZeroAlphabetUtilsTest {
    @Test
    @DisplayName(
        "Передача аргументов в containsNotLessThanThreeSymbolsAndThirdIsZero, вызывающих null pointer exception")
    void containsNotLessThanThreeSymbolsAndThirdIsZeroNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.containsNotLessThanThreeSymbolsAndThirdIsZero(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0101", "000000", "11011111"})
    @DisplayName("Проверка containsNotLessThanThreeSymbolsAndThirdIsZero с подходящими строками")
    void containsNotLessThanThreeSymbolsAndThirdIsZeroCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.containsNotLessThanThreeSymbolsAndThirdIsZero(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "01", "011", "1111110", ""})
    @DisplayName("Проверка containsNotLessThanThreeSymbolsAndThirdIsZero с неподходящими строками")
    void containsNotLessThanThreeSymbolsAndThirdIsZeroIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.containsNotLessThanThreeSymbolsAndThirdIsZero(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в startsAndEndsWithSameSymbol, вызывающих null pointer exception")
    void startsAndEndsWithSameSymbolNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.startsAndEndsWithSameSymbol(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010", "000000", "11010001", "1", "0"})
    @DisplayName("Проверка startsAndEndsWithSameSymbol с подходящими строками")
    void startsAndEndsWithSameSymbolCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.startsAndEndsWithSameSymbol(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "01", "011", "1111110", ""})
    @DisplayName("Проверка containsNotLessThanThreeSymbolsAndThirdIsZero с неподходящими строками")
    void startsAndEndsWithSameSymbolIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.startsAndEndsWithSameSymbol(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в isLengthBetweenOneAndThree, вызывающих null pointer exception")
    void isLengthBetweenOneAndThreeNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isLengthBetweenOneAndThree(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010", "00", "1", "111"})
    @DisplayName("Проверка isLengthBetweenOneAndThree с подходящими строками")
    void isLengthBetweenOneAndThreeCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isLengthBetweenOneAndThree(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1011", "1111110", ""})
    @DisplayName("Проверка containsNotLessThanThreeSymbolsAndThirdIsZero с неподходящими строками")
    void isLengthBetweenOneAndThreeIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isLengthBetweenOneAndThree(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в isLengthOdd, вызывающих null pointer exception")
    void isLengthOddNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isLengthOdd(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"01011", "0000000", "1", "101"})
    @DisplayName("Проверка isLengthOdd с подходящими строками")
    void isLengthOddCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isLengthOdd(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "01", "0111", "11111101", ""})
    @DisplayName("Проверка isLengthOdd с неподходящими строками")
    void isLengthOddIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isLengthOdd(s)).isFalse();
    }

    @Test
    @DisplayName("Передача аргументов в isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength, вызывающих null pointer exception")
    void isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLengthNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010", "00000", "1101", "0"})
    @DisplayName("Проверка isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength с подходящими строками")
    void isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLengthCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "01", "011010", ""})
    @DisplayName("Проверка isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength с неподходящими строками")
    void isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLengthIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isStartsWithZeroWithOddLengthOrStartsWithOneWIthEvenLength(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в isZerosAreMultipleOfThree, вызывающих null pointer exception")
    void isZerosAreMultipleOfThreeNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isZerosAreMultipleOfThree(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0100", "000", "000", "101010101010"})
    @DisplayName("Проверка isZerosAreMultipleOfThree с подходящими строками")
    void isZerosAreMultipleOfThreeCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isZerosAreMultipleOfThree(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"100100", "00", "0", ""})
    @DisplayName("Проверка isZerosAreMultipleOfThree с неподходящими строками")
    void isZerosAreMultipleOfThreeIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isZerosAreMultipleOfThree(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в isAnythingExceptTwoOrThreeOnes, вызывающих null pointer exception")
    void isAnythingExceptTwoOrThreeOnesNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isAnythingExceptTwoOrThreeOnes(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0101", "000000", "11011111", "1", "1111"})
    @DisplayName("Проверка isAnythingExceptTwoOrThreeOnes с подходящими строками")
    void isAnythingExceptTwoOrThreeOnesCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isAnythingExceptTwoOrThreeOnes(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "111", ""})
    @DisplayName("Проверка isAnythingExceptTwoOrThreeOnes с неподходящими строками")
    void isAnythingExceptTwoOrThreeOnesIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isAnythingExceptTwoOrThreeOnes(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в isEveryOddSymbolIsOne, вызывающих null pointer exception")
    void isEveryOddSymbolIsOneNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.isEveryOddSymbolIsOne(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "101", "11", "10", "10101"})
    @DisplayName("Проверка isEveryOddSymbolIsOne с подходящими строками")
    void isEveryOddSymbolIsOneCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isEveryOddSymbolIsOne(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "01", "0", "1111110", ""})
    @DisplayName("Проверка isEveryOddSymbolIsOne с неподходящими строками")
    void isEveryOddSymbolIsOneIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.isEveryOddSymbolIsOne(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в containsNotLessThanTwoZerosAndNotMoreThanOneOnes, вызывающих null pointer exception")
    void containsNotLessThanTwoZerosAndNotMoreThanOneOnesNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.containsNotLessThanTwoZerosAndNotMoreThanOneOnes(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010", "00", "100", "001", "001000"})
    @DisplayName("Проверка containsNotLessThanTwoZerosAndNotMoreThanOneOnes с подходящими строками")
    void containsNotLessThanTwoZerosAndNotMoreThanOneOnesCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.containsNotLessThanTwoZerosAndNotMoreThanOneOnes(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01", "0", "00101", ""})
    @DisplayName("Проверка containsNotLessThanTwoZerosAndNotMoreThanOneOnes с неподходящими строками")
    void containsNotLessThanTwoZerosAndNotMoreThanOneOnesIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.containsNotLessThanTwoZerosAndNotMoreThanOneOnes(s)).isFalse();
    }

    @Test
    @DisplayName(
        "Передача аргументов в doesntContainsSequentialOnes, вызывающих null pointer exception")
    void doesntContainsSequentialOnesNullPtrEx() {
        // expect
        assertThatThrownBy(() -> OneAndZeroAlphabetUtils.doesntContainsSequentialOnes(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010", "00", "100", "101"})
    @DisplayName("Проверка doesntContainsSequentialOnes с подходящими строками")
    void doesntContainsSequentialOnesCorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.doesntContainsSequentialOnes(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1091", "1101110", ""})
    @DisplayName("Проверка doesntContainsSequentialOnes с неподходящими строками")
    void doesntContainsSequentialOnesIncorrectStr(String s) {
        // expect
        assertThat(OneAndZeroAlphabetUtils.doesntContainsSequentialOnes(s)).isFalse();
    }
}
