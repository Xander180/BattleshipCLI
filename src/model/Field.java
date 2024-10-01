package model;

import java.util.Objects;
import java.util.Scanner;

/**
 * Class for creating the 10x10 field, as well as taking user coordinates for (in the future) placing ships and updating
 * the field
 */
public class Field {
    private final int FIELD_SIZE = 10;
    private final int[][] field = new int[FIELD_SIZE][FIELD_SIZE];

    public Field() {
    }

    /**
     * Prints current field
     */
    public void printField() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i <= field.length; i++) {
            if (i == 0) {
                System.out.print("  ");
                continue;
            }
            System.out.print(i + " ");
        }

        for (int i = 0; i < field.length; i++) {
            if (i == 0) {
                System.out.println();

            }
            System.out.print(letters[i]);
            for (int j = 0; j < field[i].length; j++) {
                char FOG_OF_WAR_SYMBOL = '~';
                if (field[i][j] == 1) {
                    System.out.print(" " + 'O');
                } else {
                    System.out.print(" " + FOG_OF_WAR_SYMBOL);
                }
            }
            System.out.println();
        }
    }

    /**
     * Takes user coordinate inputs and displays the length of the created ship and which coordinates the ships occupies
     * @param coordinates user coordinates
     */
    public boolean createShip(String coordinates, Ship ship) {
        // Splits each coordinate into its own array entry
        String[] coordinatesArray = coordinates.split(" ");
        // Separates each coordinate's row letter and column number into their own array entry.
        // This allows for easier reading/manipulation of the letter and number individually
        String[] coordinate1 = {String.valueOf(coordinatesArray[0].charAt(0)),
                String.valueOf(Integer.parseInt(coordinatesArray[0].replaceAll("\\D", "")))};
        String[] coordinate2 = {String.valueOf(coordinatesArray[1].charAt(0)),
                String.valueOf(Integer.parseInt(coordinatesArray[1].replaceAll("\\D", "")))};
        // Used for verifying the correct length of the current ship
        int count;

        if (!validInputs(coordinate1, coordinate2)) {
            System.out.println("Error!");
            return false;
        } else {
            char row1 = coordinate1[0].charAt(0);
            char row2 = coordinate2[0].charAt(0);
            char currentRow = row1;
            int column1 = Integer.parseInt(coordinate1[1]) - 1;
            int column2 = Integer.parseInt(coordinate2[1]) - 1;
            if (column1 > column2) {
                count = column1 + 1 - column2;
                // If ship length is incorrect, return to prompt
                if (!checkShipLength(ship, count)) {
                    Alert.getError(2);
                    return false;
                }
                for (int j = column1; j >= column2; j--) {
                    // If ships are found in adjacent positions, do not place ship
                    if (!checkAdjacentPositions(currentRow - 65, j)) {
                        return false;
                    }
                    currentRow = checkRow(row2, currentRow);
                }
                currentRow = row1;
                // Create ship if tests pass
                for (int i = column1; i >= column2; i--) {
                    field[currentRow - 65][i] = 1;
                    currentRow = checkRow(row2, currentRow);
                }
            } else if (column1 < column2) {
                count = column2 + 1 - column1;
                if (!checkShipLength(ship, count)) {
                    Alert.getError(2);
                    return false;
                }
                for (int j = column1; j <= column2; j++) {
                    if (!checkAdjacentPositions(currentRow - 65, j)) {
                        return false;
                    }
                    currentRow = checkRow(row2, currentRow);
                }
                currentRow = row1;
                for (int i = column1; i <= column2; i++) {
                    field[currentRow - 65][i] = 1;
                    currentRow = checkRow(row2, currentRow);
                }
                // Triggers if ship is meant to be vertical (same column)
            } else {
                if (!checkAdjacentPositions(currentRow - 65, column1)) {
                    return false;
                }
                // Checks if first coordinate row is "higher" than second coordinate row
                // Based on ASCII character values (ex. 'J' = 74, A = '65')
                if (row1 > row2) {
                    for (int j = row1; j >= column2; j--) {
                        if (!checkAdjacentPositions(currentRow - 65, column1)) {
                            return false;
                        }
                        currentRow = checkRow(row2, currentRow);
                    }
                    count = (row1 - 65) + 1 - (row2 - 65);
                } else {
                    for (int j = row1; j <= column2; j++) {
                        if (!checkAdjacentPositions(currentRow - 65, column1)) {
                            return false;
                        }
                        currentRow = checkRow(row2, currentRow);
                    }
                    count = (row2 - 65) + 1 - (row1 - 65);
                }
                if (!checkShipLength(ship, count)) {
                    Alert.getError(2);
                    return false;
                }
                currentRow = row1;
                field[currentRow - 65][column1] = 1;
                for (int i = 0; i <= count; i++) {
                    field[currentRow - 65][column1] = 1;
                    currentRow = checkRow(row2, currentRow);
                }
            }
        }
        return true;
    }

    /**
     * Starting method for game
     * Updates current ship to be placed
     */
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Ship ship = Ship.AIRCRAFT_CARRIER;

        printField();

        while (ship != Ship.NONE) {
            switch (ship) {
                case AIRCRAFT_CARRIER:
                    System.out.println("Enter the coordinates for the Aircraft Carrier (5 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship)) {
                        ship = Ship.BATTLESHIP;
                        printField();
                        continue;
                    }
                    break;

                case BATTLESHIP:
                    System.out.println("Enter the coordinates for the Battleship (4 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship)) {
                        ship = Ship.SUBMARINE;
                        printField();
                        continue;
                    }
                    break;

                case SUBMARINE:
                    System.out.println("Enter the coordinates for the submarine (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship)) {
                        ship = Ship.CRUISER;
                        printField();
                        continue;
                    }
                    break;

                case CRUISER:
                    System.out.println("Enter the coordinates for the Cruiser (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship)) {
                        ship = Ship.DESTROYER;
                        printField();
                        continue;
                    }
                    break;

                case DESTROYER:
                    System.out.println("Enter the coordinates for the Destroyer (2 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship)) {
                        ship = Ship.NONE;
                        printField();
                        continue;
                    }
                    break;
            }
        }
    }

    /**
     * Checks if user input is valid
     * Ensures that letters and numbers are within range of the field, and that diagonal placements are not possible
     * @param coordinate1 First coordinate
     * @param coordinate2 Second coordinate
     * @return true if input is valid, else false
     */
    private boolean validInputs(String[] coordinate1, String[] coordinate2) {

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
    private char checkRow(char row2, char currentRow) {
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
     * Verifies that coordinates match the length of the current ship
     * @param ship Current ship
     * @param length Length of entered coordinate
     * @return True if length is correct, else false
     */
    private boolean checkShipLength(Ship ship, int length) {
        switch (ship) {
            case AIRCRAFT_CARRIER -> {
                return length == 5;
            }
            case BATTLESHIP -> {
                return length == 4;
            }
            case SUBMARINE, CRUISER -> {
                return length == 3;
            }
            case DESTROYER -> {
                return length == 2;
            }
        }
        return false;
    }

    /**
     * Verifies that based on entered coordinates, there are no ships directly adjacent to those coordinates
     * @param row Current row to be tested
     * @param col Current column to be tested
     * @return True if there are no adjacent ships, else false
     */
    private boolean checkAdjacentPositions(int row, int col) {
        if (field[row][col] == 1) {
            Alert.getError(3);
            return false;
        }

        if (isValidPosition(row - 1, col - 1)) {
            if (field[row - 1][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row - 1, col)) {
            if (field[row - 1][col] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row - 1, col + 1)) {
            if (field[row - 1][col + 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row, col - 1)) {
            if (field[row][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row, col + 1)) {
            if (field[row][col + 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col - 1)) {
            if (field[row + 1][col - 1] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col)) {
            if (field[row + 1][col] == 1) {
                Alert.getError(3);
                return false;
            }
        }
        if (isValidPosition(row + 1, col + 1)) {
            if (field[row + 1][col + 1] == 1) {
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
    private boolean isValidPosition(int row, int col) {
        if (row < 0 || col < 0 || row > field.length - 1 || col > field[0].length - 1) {
            return false;
        }
        return true;
    }
}




