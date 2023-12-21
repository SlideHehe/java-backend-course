package edu.hw10.task2.fibonacci;

public class DefaultFibCalculator implements FibCalculator {

    @Override
    public long fib(int number) {
        if (number <= 1) {
            return number;
        } else {
            return fib(number - 1) + fib(number - 2);
        }
    }
}
