package edu.hw3.task6;

import java.util.Collections;
import java.util.Objects;
import java.util.PriorityQueue;

public class TechStockMarket implements StockMarket {
    private final PriorityQueue<Stock> stockPriorityQueue = new PriorityQueue<>(Collections.reverseOrder());

    @Override
    public void add(Stock stock) {
        Objects.requireNonNull(stock);

        stockPriorityQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        Objects.requireNonNull(stock);

        stockPriorityQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stockPriorityQueue.peek();
    }
}
