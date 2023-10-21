package edu.hw2.task2;

public class Square extends Rectangle {
    public Square(int side) {
        super(side, side);
    }

    public Square setSize(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException();
        }
        return new Square(side);
    }
}
