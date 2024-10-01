package model;

import java.util.Scanner;

import static model.ShipBuilder.createShip;

public class Game {

    Field field = new Field();

    /**
     * Starting method for game
     * Updates current ship to be placed
     */
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Ship ship = Ship.AIRCRAFT_CARRIER;

        field.printField();

        while (ship != Ship.NONE) {
            switch (ship) {
                case AIRCRAFT_CARRIER:
                    System.out.println("Enter the coordinates for the Aircraft Carrier (5 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.BATTLESHIP;
                        field.printField();
                        continue;
                    }
                    break;

                case BATTLESHIP:
                    System.out.println("Enter the coordinates for the Battleship (4 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.SUBMARINE;
                        field.printField();
                        continue;
                    }
                    break;

                case SUBMARINE:
                    System.out.println("Enter the coordinates for the submarine (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.CRUISER;
                        field.printField();
                        continue;
                    }
                    break;

                case CRUISER:
                    System.out.println("Enter the coordinates for the Cruiser (3 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.DESTROYER;
                        field.printField();
                        continue;
                    }
                    break;

                case DESTROYER:
                    System.out.println("Enter the coordinates for the Destroyer (2 cells):");
                    input = scanner.nextLine();
                    if (createShip(input, ship, field)) {
                        ship = Ship.NONE;
                        field.printField();
                        continue;
                    }
                    break;
            }
        }
        System.out.println("The game starts!");
        takeShot();
    }

    /**
     * Cell values:
     * 0 = Empty space
     * 1 = Part of a ship
     */
    void takeShot() {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        System.out.println("Take a shot!");

        String input = scanner.nextLine();

        int[] coordinate = {Integer.parseInt(String.valueOf(input.charAt(0) - 65)),
                Integer.parseInt(input.replaceAll("\\D", "")) - 1};

        while (!isValid) {
            if (field.isValidPosition(coordinate[0], coordinate[1])) {
                if (field.getCellValue(coordinate[0], coordinate[1]) == 0) {
                    System.out.println("You missed!");
                    field.setCellValue(coordinate[0], coordinate[1], 3);
                } else if (field.getCellValue(coordinate[0], coordinate[1]) == 1) {
                    System.out.println("Direct hit!");
                    field.setCellValue(coordinate[0], coordinate[1], 2);
                }
                field.printField();
                isValid = true;
            } else {
                Alert.getError(1);
                input = scanner.nextLine();

                coordinate = new int[]{Integer.parseInt(String.valueOf(input.charAt(0) - 65)),
                        Integer.parseInt(input.replaceAll("\\D", "")) - 1};
            }
        }
    }

}
