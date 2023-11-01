package edu.hw2.task2;

public class Rectangle {

    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        this.width = width;
        this.height = height;
    }

    Rectangle setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException();
        }

        return new Rectangle(width, height);
    }

    Rectangle setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException();
        }

        return new Rectangle(width, height);
    }

    double area() {
        return width * height;
    }
}

