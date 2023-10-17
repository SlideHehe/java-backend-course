package edu.hw1;

public class Task8 {
    private Task8() {
    }

    private final static int[] POSSIBLE_MOVES_BY_X = {2, 2, 1, 1, -1, -1, -2, -2};
    private final static int[] POSSIBLE_MOVES_BY_Y = {1, -1, 2, -2, 2, -2, 1, -1};
    private final static int POSSIBLE_MOVES = 8;
    private final static int DIMENSION = 8;
    private final static int KNIGHT_EXIST = 1;


    public static boolean knightBoardCapture(int[][] chessBoard) {
        if (!isBoardValid(chessBoard)) {
            return false;
        }

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (checkKnight(chessBoard, i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isBoardValid(int[][] chessBoard) {
        if (chessBoard.length != DIMENSION) {
            return false;
        }

        for (int[] column : chessBoard) {
            if (column.length != DIMENSION) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkKnight(int[][] chessBoard, int i, int j) {
        if (chessBoard[i][j] == KNIGHT_EXIST) {
            return isAbleToCapture(chessBoard, i, j);
        }

        return false;
    }

    private static boolean isAbleToCapture(int[][] chessBoard, int i, int j) {
        for (int k = 0; k < POSSIBLE_MOVES; k++) {
            int x = i + POSSIBLE_MOVES_BY_X[k];
            int y = j + POSSIBLE_MOVES_BY_Y[k];

            if (isSquareValid(x, y)) {
                if (chessBoard[x][y] == KNIGHT_EXIST) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isSquareValid(int i, int j) {
        return i >= 0 && i <= DIMENSION - 1 && j >= 0 && j <= DIMENSION - 1;
    }
}
