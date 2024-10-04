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
                    field.setCellValue(coordinate[0], coordinate[1], 3);
                    field.printField(false);
                    System.out.println("You missed!");
                } else if (field.getCellValue(coordinate[0], coordinate[1]) == 1) {
                    field.setCellValue(coordinate[0], coordinate[1], 2);
                    field.printField(false);
                    System.out.println("You hit a ship!");
                }
                field.printField(true);
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
