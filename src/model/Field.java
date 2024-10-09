package model;

import helper.Alert;

import java.util.Objects;
import java.util.Scanner;

/**
 * Class for creating the 10x10 field, as well as taking user coordinates for (in the future) placing ships and updating
 * the field
 */
public class Field {
    private final int[][] field;
    private int shipCount;
    private int aircraftCarrier;
    private int battleship;
    private int submarine;
    private int cruiser;
    private int destroyer;

    /**
     * Constructor for Field
     */
    public Field() {
        int FIELD_SIZE = 10;
        this.field = new int[FIELD_SIZE][FIELD_SIZE];
        this.shipCount = 5;
        this.aircraftCarrier = 5;
        this.battleship = 4;
        this.submarine = 3;
        this.cruiser = 3;
        this.destroyer = 2;
    }

    /**
     * Prints current field
     * @param showField if true, show all ship placements
     */
    public void printField(boolean showField) {
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
                if (showField && (this.field[i][j] == 5 || this.field[i][j] == 4 || this.field[i][j] == 3 || this.field[i][j] == 2)) {
                    System.out.print(" " + UNDAMAGED_CELL);
                } else if (this.field[i][j] == 6) {
                    System.out.print(" " + DAMAGED_CELL);
                } else if (this.field[i][j] == 1) {
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
    private int getCellValue(int row, int col) {
        return this.field[row][col];
    }

    /**
     * Updates current cell value
     * 5-2 = Undamaged ship cell
     * 6 = Damaged ship cell
     * 1 = Missed shot
     *
     * @param row Selected row
     * @param col Selected column
     * @param value Updated value
     */
    public void setCellValue(int row, int col, int value) {
        this.field[row][col] = value;
    }

    /**
     * Reduce the health of a particular ship by 1
     * @param ship Ship to be damaged
     */
    private void damageShip(Ship ship) {
        switch (ship) {
            case AIRCRAFT_CARRIER:
                this.aircraftCarrier--;
                break;
            case BATTLESHIP:
                this.battleship--;
                break;
            case SUBMARINE:
                this.submarine--;
                break;
            case CRUISER:
                this.cruiser--;
                break;
            case DESTROYER:
                this.destroyer--;
                break;
        }
    }

    /**
     * Gets the current health status of a particular ship
     * @param ship Ship to be checked
     * @return ship health
     */
    private int getShipHealth(Ship ship) {
        return switch (ship) {
            case AIRCRAFT_CARRIER -> this.aircraftCarrier;
            case BATTLESHIP -> this.battleship;
            case SUBMARINE -> this.submarine;
            case CRUISER -> this.cruiser;
            case DESTROYER -> this.destroyer;
            default -> 0;
        };

    }

    /**
     * Reduces the amount of active ships by 1
     */
    private void sinkShip() {
        this.shipCount--;
    }

    /**
     * Gets the current count of active ships
     * @return ship count
     */
    public int getShipCount() {
        return this.shipCount;
    }

    /**
     * Verifies that based on entered coordinates, there are no ships directly adjacent to those coordinates
     * @param row Current row to be tested
     * @param col Current column to be tested
     * @return True if there are no adjacent ships, else false
     */
    public boolean checkAdjacentPositions(int row, int col) {
        int[] ships = {5, 4, 3, 2};

        for (int ship : ships) {
            if (this.field[row][col] == ship) {
                Alert.getError(3);
                return false;
            }

            if (isValidPosition(row - 1, col - 1)) {
                if (this.field[row - 1][col - 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row - 1, col)) {
                if (this.field[row - 1][col] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row - 1, col + 1)) {
                if (this.field[row - 1][col + 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row, col - 1)) {
                if (this.field[row][col - 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row, col + 1)) {
                if (this.field[row][col + 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row + 1, col - 1)) {
                if (this.field[row + 1][col - 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row + 1, col)) {
                if (this.field[row + 1][col] == ship) {
                    Alert.getError(3);
                    return false;
                }
            }
            if (isValidPosition(row + 1, col + 1)) {
                if (this.field[row + 1][col + 1] == ship) {
                    Alert.getError(3);
                    return false;
                }
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
        return row >= 0 && col >= 0 && row <= field.length - 1 && col <= field[0].length - 1;
    }

    /**
     * Takes user input
     * Keeps count of active ships
     * Game ends when shipCount == 0
     * @param player current player
     * @param otherField other player's field
     */
    public void takeShot(int player, Field otherField) {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        otherField.printField(false);
        System.out.println("---------------------");
        printField(true);

        System.out.printf("Player %d, it's your turn:\n", player);

            while (!isValid) {
                String input;
                int[] coordinate;
                try {
                    input = scanner.nextLine();

                    coordinate = new int[]{Integer.parseInt(String.valueOf(input.charAt(0) - 65)),
                            Integer.parseInt(input.replaceAll("\\D", "")) - 1};
                } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                    Alert.getError(1);
                    continue;
                }

                if (otherField.isValidPosition(coordinate[0], coordinate[1])) {
                    if (otherField.getCellValue(coordinate[0], coordinate[1]) == 0
                            || otherField.getCellValue(coordinate[0], coordinate[1]) == 1) {
                        otherField.setCellValue(coordinate[0], coordinate[1], 1);
                        Alert.getInformation(3);
                        Alert.getInformation(4);
                    } else {
                        if (otherField.getCellValue(coordinate[0], coordinate[1]) == 6) {
                            Alert.getInformation(1);
                        } else if (otherField.getCellValue(coordinate[0], coordinate[1]) == 5) {
                            otherField.setCellValue(coordinate[0], coordinate[1], 6);
                            otherField.damageShip(Ship.AIRCRAFT_CARRIER);
                            if (otherField.getShipHealth(Ship.AIRCRAFT_CARRIER) == 0) {
                                otherField.sinkShip();
                                if (otherField.getShipCount() == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                                Alert.getInformation(4);

                            } else {
                                Alert.getInformation(1);
                                Alert.getInformation(4);
                            }
                        } else if (otherField.getCellValue(coordinate[0], coordinate[1]) == 4) {
                            otherField.setCellValue(coordinate[0], coordinate[1], 6);
                            otherField.damageShip(Ship.BATTLESHIP);
                            if (otherField.getShipHealth(Ship.BATTLESHIP) == 0) {
                                otherField.sinkShip();
                                if (otherField.getShipCount() == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                                Alert.getInformation(4);
                            } else {
                                Alert.getInformation(1);
                                Alert.getInformation(4);
                            }
                        } else if (otherField.getCellValue(coordinate[0], coordinate[1]) == 3) {
                            otherField.setCellValue(coordinate[0], coordinate[1], 6);
                            if (otherField.getShipHealth(Ship.SUBMARINE) == 0) {
                                otherField.damageShip(Ship.CRUISER);
                                if (otherField.getShipHealth(Ship.CRUISER) == 0) {
                                    otherField.sinkShip();
                                    if (otherField.getShipCount() == 0) {
                                        break;
                                    }
                                    Alert.getInformation(2);
                                    Alert.getInformation(4);
                                } else {
                                    Alert.getInformation(1);
                                    Alert.getInformation(4);
                                }
                            } else {
                                otherField.damageShip(Ship.SUBMARINE);
                                if (otherField.getShipHealth(Ship.SUBMARINE) == 0) {
                                    otherField.sinkShip();
                                    if (otherField.getShipCount() == 0) {
                                        break;
                                    }
                                    Alert.getInformation(2);
                                    Alert.getInformation(4);
                                } else {
                                    Alert.getInformation(1);
                                    Alert.getInformation(4);
                                }
                            }
                        }
                        else if (otherField.getCellValue(coordinate[0], coordinate[1]) == 2) {
                            otherField.setCellValue(coordinate[0], coordinate[1], 6);
                            otherField.damageShip(Ship.DESTROYER);
                            if (otherField.getShipHealth(Ship.DESTROYER) == 0) {
                                otherField.sinkShip();
                                if (otherField.getShipCount() == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                                Alert.getInformation(4);
                            } else {
                                Alert.getInformation(1);
                                Alert.getInformation(4);
                            }
                        }
                    }
                    isValid = true;
                } else {
                    Alert.getError(1);

                }
            }
    }
}




