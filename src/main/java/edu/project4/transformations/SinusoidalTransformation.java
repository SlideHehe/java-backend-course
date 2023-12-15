package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Objects;

public class SinusoidalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        Objects.requireNonNull(point);

        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());

        return new Point(newX, newY);
    }
}
