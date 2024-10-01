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
    }

}
