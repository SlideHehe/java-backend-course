package edu.project4.components;

import java.util.Random;

public record Rect(double xMin, double xMax, double yMin, double yMax) {
    public Point getRandomPoint(Random random) {
        double x = random.nextDouble(xMin, xMax);
        double y = random.nextDouble(yMin, yMax);

        return new Point(x, y);
    }

    public boolean contains(Point point) {
        return point.x() >= xMin && point.x() <= xMax && point.y() >= yMin && point.y() <= yMax;
    }
}
