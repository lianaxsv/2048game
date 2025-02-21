package org.cis1200.Game2048;

import java.util.ArrayDeque;
import java.util.Deque;
import java.io.Serializable;

/**
 * Houses the actual playing field of the game, provides ways to move
 * the tiles (e.g. left right up down). Gives high score, and if the game is
 * over.
 * Uses collections (array deques) to implement an undo button!
 */
public class GameBoard implements Serializable {

    private Deque<GameState> undoStack;
    private Tile[][] board;
    private int score;
    private int squares = 4;
    int border = 0;

    /**
     * This class represents the entire game state, including the board and score
     */
    class GameState implements Serializable {
        private Tile[][] board;
        private int score;

        public GameState(Tile[][] board, int score) {
            this.board = new Tile[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    this.board[i][j] = new Tile(board[i][j]);
                }
            }
            this.score = score;
        }

        public Tile[][] getBoard() {
            return board;
        }

        public int getScore() {
            return score;
        }
    }

    /**
     * Makes a 4x4 game board with empty tiles and sets up the undo stack.
     */
    public GameBoard() {
        board = new Tile[4][4];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Tile();
            }
        }
        undoStack = new ArrayDeque<>();
        saveState();
    }

    /**
     * Saves the current state of the board to the undo stack.
     */
    private void saveState() {
        Tile[][] currentState = new Tile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                currentState[i][j] = new Tile(board[i][j]);
            }
        }
        undoStack.push(new GameState(currentState, score));
    }

    /**
     * Restores the previous state of the board from the undo stack.
     */
    public void restoreState() {
        if (!undoStack.isEmpty()) {
            GameState gameState = undoStack.pop();
            board = gameState.getBoard();
            score = gameState.getScore();
        }
    }

    /**
     *
     * Finds highest tile and returns it (will be useful for high score)
     *
     * @return highest Greatest tile value
     */
    public int getHighestTile() {
        int highest = board[0][0].getValue();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() > highest) {
                    highest = board[i][j].getValue();
                }
            }
        }
        return highest;
    }

    /**
     * Gets score
     *
     * @return Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds a method to access board
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Game logic: spawns a tile with the value 2 in a random open space each time a
     * move occurs.
     * Does nothing if there are no open spaces left.
     */
    public void spawn2or4() {
        boolean openSpaceExists = false;
        for (int i = 0; i < squares; i++) {
            for (int j = 0; j < squares; j++) {
                if (board[i][j].getValue() == 0) {
                    openSpaceExists = true;
                    break;
                }
            }
        }
        if (openSpaceExists) {
            while (true) {
                int col = (int) (Math.random() * squares);
                int row = (int) (Math.random() * squares);
                if (board[row][col].getValue() == 0) {
                    int value = (Math.random() < 0.2) ? 4 : 2;
                    board[row][col] = new Tile(value);
                    break;
                }
            }
        }
    }

    /**
     * Gives board as string value
     *
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                s += board[i][j].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Moves tiles vertically on the entire board based on the 'up' arrow input.
     */
    public void moveUp() {
        saveState();
        for (int j = 0; j < squares; j++) {
            border = 0;
            for (int i = 0; i < squares; i++) {
                if (board[i][j].getValue() != 0) {
                    if (border <= i) {
                        vertical(i, j, "up");
                    }
                }
            }
        }
    }

    /**
     * Moves tiles vertically on the entire board based on the 'down' arrow input.
     */
    public void moveDown() {
        saveState();
        for (int j = 0; j < squares; j++) {
            border = squares - 1;
            for (int i = squares - 1; i >= 0; i--) {
                if (board[i][j].getValue() != 0) {
                    if (border >= i) {
                        vertical(i, j, "down");
                    }
                }
            }
        }
    }

    /**
     * Moves the tiles vertically based on the specified direction.
     *
     * @param row       The row index of the tile.
     * @param col       The column index of the tile.
     * @param direction The direction of movement ("up" or "down").
     */
    private void vertical(int row, int col, String direction) {
        Tile initial = board[border][col];
        Tile compare = board[row][col];

        if (initial.getValue() == 0 || initial.getValue() == compare.getValue()) {
            if (row > border || (direction.equals("down") && row < border)) {
                int addScore = initial.getValue() + compare.getValue();
                if (initial.getValue() != 0) {
                    score += addScore;
                }
                initial.setValue(addScore);
                compare.setValue(0);
            }
        } else {
            if (direction.equals("down")) {
                border--;
            } else {
                border++;
            }
            vertical(row, col, direction);
        }
    }

    /**
     * Moves tiles horizontally on the entire board based on the 'left' arrow input.
     */
    public void moveLeft() {
        saveState();
        for (int i = 0; i < squares; i++) {
            border = 0;
            for (int j = 0; j < squares; j++) {
                if (board[i][j].getValue() != 0) {
                    if (border <= j) {
                        horizontal(i, j, "left");
                    }
                }
            }
        }
    }

    /**
     * Moves tiles horizontally on the entire board based on the 'right' arrow
     * input.
     */
    public void moveRight() {
        saveState();
        for (int i = 0; i < squares; i++) {
            border = squares - 1;
            for (int j = squares - 1; j >= 0; j--) {
                if (board[i][j].getValue() != 0) {
                    if (border >= j) {
                        horizontal(i, j, "right");
                    }
                }
            }
        }
    }

    /**
     * Moves the tiles horizontally based on the specified direction.
     *
     * @param row       The row index of the tile.
     * @param col       The column index of the tile.
     * @param direction The direction of movement ("left" or "right").
     */
    private void horizontal(int row, int col, String direction) {
        Tile initial = board[row][border];
        Tile compare = board[row][col];

        if (initial.getValue() == 0 || initial.getValue() == compare.getValue()) {
            if (col > border || (direction.equals("right") && col < border)) {
                int addScore = initial.getValue() + compare.getValue();
                if (initial.getValue() != 0) {
                    score += addScore;
                }
                initial.setValue(addScore);
                compare.setValue(0);
            }
        } else {
            if (direction.equals("right")) {
                border--;
            } else {
                border++;
            }
            horizontal(row, col, direction);
        }
    }

    /**
     * Checks if two tiles can merge based on their positions.
     *
     * @param row1 The row index of the first tile.
     * @param col1 The column index of the first tile.
     * @param row2 The row index of the second tile.
     * @param col2 The column index of the second tile.
     * @return true if the tiles can merge, false otherwise.
     */
    private boolean canMerge(int row1, int col1, int row2, int col2) {
        int value1 = board[row1][col1].getValue();
        int value2 = board[row2][col2].getValue();

        return value1 == value2 && value1 != 0;
    }

    /**
     * Checks if the game is over by determining if there are no empty spaces
     * or if no tiles can merge with neighboring tiles.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean gameOver() {
        boolean noEmptySpace = true;
        boolean noMergingPossibility = true;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int currentValue = board[i][j].getValue();

                if (currentValue == 0) {
                    noEmptySpace = false;
                } else {
                    if ((i < board.length - 1 && canMerge(i, j, i + 1, j)) ||
                            (j < board[i].length - 1 && canMerge(i, j, i, j + 1)) ||
                            (i > 0 && canMerge(i, j, i - 1, j)) ||
                            (j > 0 && canMerge(i, j, i, j - 1))) {
                        noMergingPossibility = false;
                    }
                }
            }
        }
        return noEmptySpace && noMergingPossibility;
    }

}
