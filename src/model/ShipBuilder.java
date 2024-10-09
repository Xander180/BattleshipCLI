package model;

import helper.Alert;

import java.util.Scanner;

public class ShipBuilder {

    public static boolean createShip(String coordinates, Ship ship, Field field) {
        // Splits each coordinate into its own array entry
        String[] coordinatesArray = coordinates.split(" ");
        // Separates each coordinate's row letter and column number into their own array entry.
        // This allows for easier reading/manipulation of the letter and number individually
        String[] coordinate1;
        String[] coordinate2;

        // Used for verifying the correct length of the current ship
        int count;

        try {
            coordinate1 = new String[]{String.valueOf(coordinatesArray[0].charAt(0)),
                    String.valueOf(Integer.parseInt(coordinatesArray[0].replaceAll("\\D", "")))};
            coordinate2 = new String[]{String.valueOf(coordinatesArray[1].charAt(0)),
                    String.valueOf(Integer.parseInt(coordinatesArray[1].replaceAll("\\D", "")))};
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            Alert.getError(1);
            return false;
        }

        if (!Field.validInputs(coordinate1, coordinate2)) {
            Alert.getError(1);
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
                if (count != ship.getLength()) {
                    Alert.getError(2);
                    return false;
                }
                for (int j = column1; j >= column2; j--) {
                    // If ships are found in adjacent positions, do not place ship
                    if (!field.checkAdjacentPositions(currentRow - 65, j)) {
                        return false;
                    }
                    currentRow = field.checkRow(row2, currentRow);
                }
                currentRow = row1;
                // Create ship if tests pass
                for (int i = column1; i >= column2; i--) {
                    field.setCellValue(currentRow - 65, i, ship.getLength());
                    currentRow = field.checkRow(row2, currentRow);
                }
            } else if (column1 < column2) {
                count = column2 + 1 - column1;
                if (count != ship.getLength()) {
                    Alert.getError(2);
                    return false;
                }
                for (int j = column1; j <= column2; j++) {
                    if (!field.checkAdjacentPositions(currentRow - 65, j)) {
                        return false;
                    }
                    currentRow = field.checkRow(row2, currentRow);
                }
                currentRow = row1;
                for (int i = column1; i <= column2; i++) {
                    field.setCellValue(currentRow - 65, i, ship.getLength());
                    currentRow = field.checkRow(row2, currentRow);
                }
                // Triggers if ship is meant to be vertical (same column)
            } else {
                if (!field.checkAdjacentPositions(currentRow - 65, column1)) {
                    return false;
                }
                // Checks if first coordinate row is "higher" than second coordinate row
                // Based on ASCII character values (ex. 'J' = 74, A = '65')
                if (row1 > row2) {
                    for (int j = row1; j >= column2; j--) {
                        if (!field.checkAdjacentPositions(currentRow - 65, column1)) {
                            return false;
                        }
                        currentRow = field.checkRow(row2, currentRow);
                    }
                    count = (row1 - 65) + 1 - (row2 - 65);
                } else {
                    for (int j = row1; j <= column2; j++) {
                        if (!field.checkAdjacentPositions(currentRow - 65, column1)) {
                            return false;
                        }
                        currentRow = field.checkRow(row2, currentRow);
                    }
                    count = (row2 - 65) + 1 - (row1 - 65);
                }
                if (count != ship.getLength()) {
                    Alert.getError(2);
                    return false;
                }
                currentRow = row1;
                field.setCellValue(currentRow - 65, column1, ship.getLength());
                for (int i = 0; i <= count; i++) {
                    field.setCellValue(currentRow - 65, column1, ship.getLength());
                    currentRow = field.checkRow(row2, currentRow);
                }
            }
        }
        return true;
    }

    /**
     * Starting method for game
     * Updates current ship to be placed
     */
    public static void setShips(Field field, int player) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Ship ship = Ship.AIRCRAFT_CARRIER;

        System.out.printf("Player %d, place your ships on the game field\n", player);

        field.printField(true);

        while (ship != Ship.NONE) {
            switch (ship) {
                case AIRCRAFT_CARRIER:
                    System.out.println("Enter the coordinates for the Aircraft Carrier (5 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.BATTLESHIP;
                        field.printField(true);
                        continue;
                    }
                    break;

                case BATTLESHIP:
                    System.out.println("Enter the coordinates for the Battleship (4 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.SUBMARINE;
                        field.printField(true);
                        continue;
                    }
                    break;

                case SUBMARINE:
                    System.out.println("Enter the coordinates for the submarine (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.CRUISER;
                        field.printField(true);
                        continue;
                    }
                    break;

                case CRUISER:
                    System.out.println("Enter the coordinates for the Cruiser (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.DESTROYER;
                        field.printField(true);
                        continue;
                    }
                    break;

                case DESTROYER:
                    System.out.println("Enter the coordinates for the Destroyer (2 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.NONE;
                        field.printField(true);
                        continue;
                    }
                    break;
            }
        }
        Alert.getInformation(4);
    }
}
