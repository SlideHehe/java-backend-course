package edu.hw4;

public class ValidationError extends IllegalArgumentException {
    public ValidationError(String s) {
        super(s);
    }

    @Override public String toString() {
        return this.getMessage();
    }
}
