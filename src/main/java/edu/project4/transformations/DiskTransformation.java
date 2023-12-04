package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Objects;

public class DiskTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        Objects.requireNonNull(point);

        double r = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
        double theta = Math.atan(point.x() / point.y());

        double newX = (theta / Math.PI) * Math.sin(Math.PI * r);
        double newY = (theta / Math.PI) * Math.cos(Math.PI * r);

        return new Point(newX, newY);
    }
}
