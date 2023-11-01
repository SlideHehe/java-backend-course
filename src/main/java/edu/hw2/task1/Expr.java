package edu.hw2.task1;

import java.util.Objects;

public sealed interface Expr {
    double NEGATE_NUMBER = -1.0;

    double evaluate();

    record Constant(double evaluate) implements Expr {
        public Constant {
            if (!Double.isFinite(evaluate)) {
                throw new IllegalArgumentException();
            }
        }

    }

    record Negate(Expr value) implements Expr {
        public Negate {
            if (Objects.isNull(value)) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public double evaluate() {
            return value.evaluate() * NEGATE_NUMBER;
        }
    }

    record Exponent(Expr base, double exponent) implements Expr {
        public Exponent {
            if (Objects.isNull(base) || !Double.isFinite(exponent)) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public double evaluate() {
            double result = Math.pow(base().evaluate(), exponent);

            if (Double.isFinite(result)) {
                return result;
            }

            throw new IllegalArgumentException();
        }
    }

    record Addition(Expr lhs, Expr rhs) implements Expr {
        public Addition {
            if (Objects.isNull(lhs) || Objects.isNull(rhs)) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public double evaluate() {
            return lhs.evaluate() + rhs.evaluate();
        }
    }

    record Multiplication(Expr lhs, Expr rhs) implements Expr {
        public Multiplication {
            if (Objects.isNull(lhs) || Objects.isNull(rhs)) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public double evaluate() {
            return lhs.evaluate() * rhs.evaluate();
        }
    }
}
