package edu.hw3.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StockMarketTest {
    static Stream<Arguments> invalidArgsProvider() {
        return Stream.of(
            Arguments.of("sber", 0.0),
            Arguments.of("", 200.5),
            Arguments.of("\t", 200.5),
            Arguments.of(" ", 200.5),
            Arguments.of("\n", 200.5),
            Arguments.of(null, -1.0)
        );
    }

    @DisplayName("Проверка недопустимых аргументов Stock")
    @ParameterizedTest
    @MethodSource("invalidArgsProvider")
    void invalidValuesTest(String name, double price) {
        assertThatThrownBy(() -> new Stock(name, price)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка на передачу null вместо Stock в add и remove")
    @ParameterizedTest
    @NullSource
    void nullStocksTest(Stock stock) {
        StockMarket market = new TechStockMarket();
        assertThatThrownBy(() -> market.add(stock)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> market.remove(stock)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Получение mostValuableStock из пустой очереди")
    @Test
    void emptyQueueGetTest() {
        // given
        StockMarket market = new TechStockMarket();

        // when
        Stock stock = market.mostValuableStock();

        // then
        assertThat(stock).isNull();
    }

    @DisplayName("Проверка удаления элемента из очереди")
    @Test
    void removeElementTest() {
        // given
        StockMarket market = new TechStockMarket();
        Stock stock1 = new Stock("Microsoft", 261.12);
        Stock stock2 = new Stock("Lenovo", 196.59);

        // when
        market.add(stock1);
        market.add(stock2);
        market.remove(stock1);

        // then
        assertThat(market.mostValuableStock()).isEqualTo(stock2);
    }

    @DisplayName("Проверка правильности получения самой дорогой акции")
    @Test
    void mostValuableStockTest() {
        // given
        StockMarket market = new TechStockMarket();
        Stock currentMostValuable = new Stock("Apple", 700.00);

        // when
        market.add(new Stock("Samsung", 590.59));
        market.add(currentMostValuable);
        market.add(new Stock("Sony", 350.55));

        // then
        assertThat(market.mostValuableStock()).isEqualTo(currentMostValuable);
    }
}
