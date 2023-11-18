package edu.hw3.task6;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record Stock(String name, double price) implements Comparable<Stock> {
    public Stock {
        if (Objects.isNull(name) || name.replaceAll("\\s+", "").isEmpty() || price <= 0) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public int compareTo(@NotNull Stock o) {
        return (int) (this.price - o.price);
    }
}
