package edu.hw10.task2;

import edu.hw10.task2.fibonacci.DefaultFibCalculator;
import edu.hw10.task2.fibonacci.FibCalculator;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheProxyTest {

    @Test
    @DisplayName("Проверка создания proxy")
    void createTest() {
        // given
        FibCalculator fibCalculator = new DefaultFibCalculator();

        // when
        FibCalculator proxy = CacheProxy.create(fibCalculator, fibCalculator.getClass());

        // then
        assertThat(proxy).isNotNull();
        assertThat(Proxy.isProxyClass(proxy.getClass())).isTrue();
    }

    @Test
    @DisplayName("Передача null вместо target и targetClass")
    void createWithNullArgs() {
        // expect
        assertThatThrownBy(() -> CacheProxy.create(null, FibCalculator.class))
            .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> CacheProxy.create(new DefaultFibCalculator(), null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка кеширования")
    void cachingTest() {
        // given
        FibCalculator fibCalculator = new DefaultFibCalculator();
        FibCalculator proxy = CacheProxy.create(fibCalculator, fibCalculator.getClass());

        // when
        long result1 = proxy.fib(10);
        long result2 = proxy.fib(20);

        // then
        assertThat(result1).isEqualTo(55);
        assertThat(result2).isEqualTo(6765);
        Object[] args1 = new Object[] {10};
        Object[] args2 = new Object[] {20};
        String hash1 = String.valueOf(Arrays.hashCode(args1));
        String hash2 = String.valueOf(Arrays.hashCode(args2));
        assertThat(Files.exists(Path.of("cache", hash1))).isTrue();
        assertThat(Files.exists(Path.of("cache", hash2))).isTrue();
    }
}
