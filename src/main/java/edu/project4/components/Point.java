package edu.project4.components;

public record Point(double x, double y) {
    public static Point getRotatedCopy(Point point, double theta2) {
        double newX = point.x() * Math.cos(theta2) - point.y() * Math.sin(theta2);
        double newY = point.x() * Math.sin(theta2) + point.y() * Math.cos(theta2);

        return new Point(newX, newY);
    }

    public static Point applyAffine(Point point, AffineCoefficients coefficients) {
        double newX = coefficients.a() * point.x + coefficients.b() * point.y + coefficients.c();
        double newY = coefficients.d() * point.x + coefficients.e() * point.y + coefficients.f();

        return new Point(newX, newY);
    }
}
