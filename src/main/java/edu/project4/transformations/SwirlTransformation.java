package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Objects;

public class SwirlTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        Objects.requireNonNull(point);

        double r = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);

        double newX = point.x() * Math.sin(r) - point.y() * Math.cos(r);
        double newY = point.x() * Math.cos(r) + point.y() * Math.sin(r);

        return new Point(newX, newY);
    }
}
