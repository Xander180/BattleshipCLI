import model.Game;

/**
 * Main entry point for program
 * Creates 10x10 field and asks for user input
 * @author Wilson Ramirez
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        game.playGame();
    }
}
