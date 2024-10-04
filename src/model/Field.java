package model;

import java.util.Objects;

/**
 * Class for creating the 10x10 field, as well as taking user coordinates for (in the future) placing ships and updating
 * the field
 */
public class Field {
    private final int[][] field;

    public Field() {
        int FIELD_SIZE = 10;
        this.field = new int[FIELD_SIZE][FIELD_SIZE];
    }

    /**
     * Prints current field
     */
    public void printField(boolean gameOver) {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i <= this.field.length; i++) {
            if (i == 0) {
                System.out.print("  ");
                continue;
            }
            System.out.print(i + " ");
        }

        for (int i = 0; i < this.field.length; i++) {
            if (i == 0) {
                System.out.println();

            }
            System.out.print(letters[i]);
            for (int j = 0; j < this.field[i].length; j++) {
                final char FOG_OF_WAR_SYMBOL = '~';
                final char UNDAMAGED_CELL = 'O';
                final char DAMAGED_CELL = 'X';
                final char MISSED_CELL = 'M';
                if (this.field[i][j] == 1 && gameOver) {
                    System.out.print(" " + UNDAMAGED_CELL);
                } else if (this.field[i][j] == 2) {
                    System.out.print(" " + DAMAGED_CELL);
                } else if (this.field[i][j] == 3) {
                    System.out.print(" " + MISSED_CELL);
                }
                else {
                    System.out.print(" " + FOG_OF_WAR_SYMBOL);
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks if user input is valid
     * Ensures that letters and numbers are within range of the field, and that diagonal placements are not possible
     * @param coordinate1 First coordinate
     * @param coordinate2 Second coordinate
     * @return true if input is valid, else false
     */
    public static boolean validInputs(String[] coordinate1, String[] coordinate2) {

        if (coordinate1.length == 2 && coordinate2.length == 2) {
            if (Objects.equals(coordinate1[0], coordinate2[0]) || Objects.equals(coordinate1[1], coordinate2[1])) {
                if (coordinate1[0].charAt(0) >= 'A' && coordinate1[0].charAt(0) <= 'J') {
                    if (coordinate2[0].charAt(0) >= 'A' && coordinate2[0].charAt(0) <= 'J') {
                        int i = Integer.parseInt(coordinate1[1]);
                        if (i > 0 && i < 11) {
                            int j = Integer.parseInt(coordinate2[1]);
                            return j > 0 && j < 11;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check for vertical ship placement
     * @param row2 Row of second coordinate
     * @param currentRow Current row being checked
     * @return new current row
     */
    public char checkRow(char row2, char currentRow) {
        // if true, ship is placed vertically from bottom to top
        if (currentRow > row2) {
            currentRow -= 1;
            return currentRow;
        // if true, ship is placed vertically from bottom to top
        } else if (currentRow < row2) {
            currentRow += 1;
            return currentRow;
        // if true, ship is either placed horizontally, or currentRow is the tail of the ship
        } else {
            return currentRow;
        }
    }

    /**
     * Returns current value of cell
     * @param row Selected row
     * @param col Selected column
     * @return Cell value
     */
    public int getCellValue(int row, int col) {
        return this.field[row][col];
    }

    /**
     * Updates current cell value
     * 1 = Undamaged ship cell
     * 2 = Damaged ship cell
     * 3 = Missed shot
     *
     * @param row Selected row
     * @param col Selected column
     * @param value Updated value
     */
    public void setCellValue(int row, int col, int value) {
        this.field[row][col] = value;
    }

    /**
     * Verifies that based on entered coordinates, there are no ships directly adjacent to those coordinates
     * @param row Current row to be tested
     * @param col Current column to be tested
     * @return True if there are no adjacent ships, else false
     */
    public boolean checkAdjacentPositions(int row, int col) {
        if (this.field[row][col] == 1) {
            Alert.getError(3);
            return false;
        }

        if (isValidPosition(row - 1, col - 1)) {
            if (this.field[row - 1][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row - 1, col)) {
            if (this.field[row - 1][col] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row - 1, col + 1)) {
            if (this.field[row - 1][col + 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row, col - 1)) {
            if (this.field[row][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row, col + 1)) {
            if (this.field[row][col + 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col - 1)) {
            if (this.field[row + 1][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col)) {
            if (this.field[row + 1][col] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col + 1)) {
            if (this.field[row + 1][col + 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }

        return true;
    }

    /**
     * Verifies that current cell being checked exists and is not out of bounds
     * @param row Current row being checked
     * @param col Current column being checked
     * @return True if position exists, else false
     */
    public boolean isValidPosition(int row, int col) {
        if (row < 0 || col < 0 || row > field.length - 1 || col > field[0].length - 1) {
            return false;
        }
        return true;
    }

    public enum Ship {
        AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER, NONE
    }
}




