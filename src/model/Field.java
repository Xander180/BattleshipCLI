package model;

import java.util.Objects;
import java.util.Scanner;

/**
 * Class for creating the 10x10 field, as well as taking user coordinates for (in the future) placing ships and updating
 * the field
 */
public class Field {
    private final int FIELD_SIZE = 10;
    private char[][] field = new char[FIELD_SIZE][FIELD_SIZE];

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
                System.out.print(" " + FOG_OF_WAR_SYMBOL);
            }
            System.out.println();
        }
    }

    /**
     * Takes user coordinate inputs and displays the length of the created ship and which coordinates the ships occupies
     * @param coordinates user coordinates
     */
    public void createShip(String coordinates) {
        // Splits each coordinate into its own array entry
        String[] coordinatesArray = coordinates.split(" ");
        // Separates each coordinate's row letter and column number into their own array entry.
        // This allows for easier reading/manipulation of the letter and number individually
        String[] coordinate1 = {String.valueOf(coordinatesArray[0].charAt(0)),
                String.valueOf(Integer.parseInt(coordinatesArray[0].replaceAll("\\D", "")))};
        String[] coordinate2 = {String.valueOf(coordinatesArray[1].charAt(0)),
                String.valueOf(Integer.parseInt(coordinatesArray[1].replaceAll("\\D", "")))};
        int count = 0;
        StringBuilder parts = new StringBuilder("Parts: ");


        if (!validInputs(coordinate1, coordinate2)) {
            System.out.println("Error!");
        } else {
            char row1 = coordinate1[0].charAt(0);
            char row2 = coordinate2[0].charAt(0);
            char currentRow = row1;
            int column1 = Integer.parseInt(coordinate1[1]);
            int column2 = Integer.parseInt(coordinate2[1]);
            if (column1 > column2) {
                for (int i = column1; i >= column2; i--) {
                    parts.append(currentRow).append(i).append(" ");
                    currentRow = checkRow(row2, currentRow);
                    count++;
                }
            } else if (column1 < column2) {
                for (int i = column1; i <= column2; i++) {
                    parts.append(currentRow).append(i).append(" ");
                    currentRow = checkRow(row2, currentRow);
                    count++;
                }
            } else {
                parts.append(currentRow).append(column1).append(" ");
                count++;
                while (currentRow != row2) {
                    currentRow = checkRow(row2, currentRow);
                    parts.append(currentRow).append(column1).append(" ");
                    count++;
                }
            }
            System.out.println("Length: " + count);
            System.out.println(parts);
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Ship ship = Ship.AIRCRAFT_CARRIER;

        while (ship != Ship.NONE) {
            switch (ship) {
                case AIRCRAFT_CARRIER:
                    System.out.println("Enter the coordinates for the Aircraft Carrier (5 cells):");
                    input = scanner.nextLine();
                    createShip(input);
                    ship = Ship.BATTLESHIP;
                    continue;

                case BATTLESHIP:
                    System.out.println("Enter the coordinates for the Battleship (4 cells):");
                    input = scanner.nextLine();
                    createShip(input);
                    ship = Ship.SUBMARINE;
                    continue;

                case SUBMARINE:
                    System.out.println("Enter the coordinates for the submarine (3 cells):");
                    input = scanner.nextLine();
                    createShip(input);
                    ship = Ship.CRUISER;
                    continue;

                case CRUISER:
                    System.out.println("Enter the coordinates for the Cruiser (3 cells):");
                    input = scanner.nextLine();
                    createShip(input);
                    ship = Ship.DESTROYER;
                    continue;

                case DESTROYER:
                    System.out.println("Enter the coordinates for the Destroyer (2 cells):");
                    input = scanner.nextLine();
                    createShip(input);
                    ship = Ship.NONE;
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
}
