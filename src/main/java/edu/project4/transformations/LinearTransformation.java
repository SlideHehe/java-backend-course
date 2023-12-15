package edu.project4.transformations;

import edu.project4.components.Point;
import java.util.Objects;

public class LinearTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        Objects.requireNonNull(point);

        return point;
    }
}
