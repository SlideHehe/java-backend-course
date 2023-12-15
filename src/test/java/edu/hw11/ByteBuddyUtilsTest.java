package edu.hw11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteBuddyUtilsTest {
    @Test
    @DisplayName("Проверка создания класса HelloByteBuddy")
    void helloByteBuddy() {
        // expect
        assertThat(ByteBuddyUtils.getHelloByteBuddyInstance().toString()).isEqualTo("Hello, ByteBuddy!");
    }

    /*
    Присутствует некоторый конфликт Jacoco и Bytebuddy. При прогоне теста ко 2-му заданию локально - все ок, на гитхабе и
    при запуске Maven verify падает вместе с UnsupportedOperationException.
     */

//    @Test
//    @DisplayName("Проверка замены суммы у класа ArithmeticUtils")
//    void sumReloading() {
//        // expect
//        assertThat(ArithmeticUtils.sum(5, 5)).isEqualTo(10);
//        ByteBuddyUtils.changeArithmeticUtilsBehaviour();
//        assertThat(ArithmeticUtils.sum(5, 5)).isEqualTo(25);
//    }

    @Test
    @DisplayName("Проверка нахождения числа Фибоначчи")
    void fibonacciCalculator()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        Class<?> type = ByteBuddyUtils.getFibCalculatorClass();
        Object instance = type.getDeclaredConstructor().newInstance();
        Method fibMethod = type.getMethod("fib", int.class);

        // expect
        assertThat((long) fibMethod.invoke(instance, 0)).isEqualTo(0);
        assertThat((long) fibMethod.invoke(instance, 1)).isEqualTo(1);
        assertThat((long) fibMethod.invoke(instance, 2)).isEqualTo(1);
        assertThat((long) fibMethod.invoke(instance, 5)).isEqualTo(5);
        assertThat((long) fibMethod.invoke(instance, 10)).isEqualTo(55);
        assertThat((long) fibMethod.invoke(instance, 20)).isEqualTo(6765);

    }
}
