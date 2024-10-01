package model;

public class ShipBuilder {

    public static boolean createShip(String coordinates, Ship ship, Field field) {
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
//        StringBuilder parts = new StringBuilder("Parts: ");


        if (!Field.validInputs(coordinate1, coordinate2)) {
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
                    if (!field.checkAdjacentPositions(currentRow - 65, j)) {
                        return false;
                    }
                    currentRow = field.checkRow(row2, currentRow);
                }
                currentRow = row1;
                // Create ship if tests pass
                for (int i = column1; i >= column2; i--) {
                    field.setCellValue(currentRow - 65, i, 1);
                    currentRow = field.checkRow(row2, currentRow);
                }
            } else if (column1 < column2) {
                count = column2 + 1 - column1;
                if (!checkShipLength(ship, count)) {
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
                    field.setCellValue(currentRow - 65, i, 1);
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
                if (!checkShipLength(ship, count)) {
                    Alert.getError(2);
                    return false;
                }
                currentRow = row1;
                field.setCellValue(currentRow - 65, column1, 1);
                for (int i = 0; i <= count; i++) {
                    field.setCellValue(currentRow - 65, column1, 1);
                    currentRow = field.checkRow(row2, currentRow);
                }
            }
//            System.out.println("Length: " + count);
        }
        return true;
    }

    /**
     * Verifies that coordinates match the length of the current ship
     * @param ship Current ship
     * @param length Length of entered coordinate
     * @return True if length is correct, else false
     */
    private static boolean checkShipLength(Ship ship, int length) {
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


}
