import helper.Alert;
import model.Field;
import model.ShipBuilder;

/**
 * Main entry point for program
 * Creates 10x10 field and asks for user input
 * @author Wilson Ramirez
 */
public class Main {
    public static void main(String[] args) {
        Field player1 = new Field();
        Field player2 = new Field();
        int currentPlayer = 1;
        boolean gameOver = false;

        ShipBuilder.setShips(player1, 1);
        ShipBuilder.setShips(player2, 2);

        while (!gameOver) {
            if (currentPlayer == 1) {
                player1.takeShot(currentPlayer, player2);
                System.out.flush();
                currentPlayer++;
            } else if (currentPlayer == 2) {
                player2.takeShot(currentPlayer, player1);
                System.out.flush();
                currentPlayer--;
            }

            if (player1.getShipCount() == 0 || player2.getShipCount() == 0) {
                gameOver = true;
                Alert.getInformation(5);
            }
        }
    }
}
