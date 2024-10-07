package model;

import java.util.Scanner;

import static model.ShipBuilder.createShip;

public class Game {

    private final Field field = new Field();
    private int shipsDestroyed;


    /**
     * Starting method for game
     * Updates current ship to be placed
     */
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Ship ship = Ship.AIRCRAFT_CARRIER;

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
        System.out.println("The game starts!");
        field.printField(false);
        takeShot();
    }

    /**
     * Takes user input
     * Keeps count of active ships
     * Game ends when shipCount == 0
     */
    void takeShot() {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        int shipCount = 5;
        int aircraftCarrier = 5;
        int battleship = 4;
        int submarine = 3;
        int cruiser = 3;
        int destroyer = 2;

        System.out.println("Take a shot!");

        while (shipCount != 0) {
            String input = scanner.nextLine();

            int[] coordinate = {Integer.parseInt(String.valueOf(input.charAt(0) - 65)),
                    Integer.parseInt(input.replaceAll("\\D", "")) - 1};

            while (!isValid) {
                if (field.isValidPosition(coordinate[0], coordinate[1])) {
                    if (field.getCellValue(coordinate[0], coordinate[1]) == 0
                            || field.getCellValue(coordinate[0], coordinate[1]) == 1) {
                        field.setCellValue(coordinate[0], coordinate[1], 1);
                        field.printField(false);
                        System.out.println("You missed. Try again:");
                    } else {
                        if (field.getCellValue(coordinate[0], coordinate[1]) == 6) {
                            field.printField(false);
                            Alert.getInformation(1);
                        } else if (field.getCellValue(coordinate[0], coordinate[1]) == 5) {
                            field.setCellValue(coordinate[0], coordinate[1], 6);
                            field.printField(false);
                            aircraftCarrier--;
                            if (aircraftCarrier == 0) {
                                shipCount--;
                                if (shipCount == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                            } else {
                                Alert.getInformation(1);
                            }
                        } else if (field.getCellValue(coordinate[0], coordinate[1]) == 4) {
                            field.setCellValue(coordinate[0], coordinate[1], 6);
                            field.printField(false);
                            battleship--;
                            if (battleship == 0) {
                                shipCount--;
                                if (shipCount == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                            } else {
                                Alert.getInformation(1);
                            }
                        } else if (field.getCellValue(coordinate[0], coordinate[1]) == 3) {
                            field.setCellValue(coordinate[0], coordinate[1], 6);
                            field.printField(false);
                            if (submarine == 0) {
                                cruiser--;
                                if (cruiser == 0) {
                                    shipCount--;
                                    if (shipCount == 0) {
                                        break;
                                    }
                                    Alert.getInformation(2);
                                } else {
                                    Alert.getInformation(1);
                                }
                            } else {
                                submarine--;
                                if (submarine == 0) {
                                    shipCount--;
                                    if (shipCount == 0) {
                                        break;
                                    }
                                    Alert.getInformation(2);
                                } else {
                                    Alert.getInformation(1);
                                }
                            }
                        }
                        else if (field.getCellValue(coordinate[0], coordinate[1]) == 2) {
                            field.setCellValue(coordinate[0], coordinate[1], 6);
                            field.printField(false);
                            destroyer--;
                            if (destroyer == 0) {
                                shipCount--;
                                if (shipCount == 0) {
                                    break;
                                }
                                Alert.getInformation(2);
                            } else {
                                Alert.getInformation(1);
                            }
                        }
                    }
                    isValid = true;
                } else {
                    Alert.getError(1);
                    input = scanner.nextLine();

                    coordinate = new int[]{Integer.parseInt(String.valueOf(input.charAt(0) - 65)),
                            Integer.parseInt(input.replaceAll("\\D", "")) - 1};
                }
            }
            isValid = false;
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

}
