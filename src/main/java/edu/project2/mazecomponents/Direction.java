package edu.project2.mazecomponents;

public enum Direction {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);
    private static final int OFFSET_MULTIPLIER = 2;

    private final int rowOffset;
    private final int colOffset;

    Direction(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public int cellRowOffset() {
        return rowOffset * OFFSET_MULTIPLIER;
    }

    public int cellColOffset() {
        return colOffset * OFFSET_MULTIPLIER;
    }

    public int rowOffset() {
        return rowOffset;
    }

    public int colOffset() {
        return colOffset;
    }
}
