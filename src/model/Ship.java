package model;

/**
 * All available ships in game and their corresponding lengths/health values
 * NONE - Used to end the loop in setShips() method above
 */
public enum Ship {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3),
    DESTROYER(2), NONE(0);

    private final int length;

    Ship(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
