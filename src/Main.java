import model.Field;
import java.util.Scanner;

/**
 * Main entry point for program
 * Creates 10x10 field and asks for user input
 * @author Wilson Ramirez
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Field field = new Field();

        field.printField();

        System.out.println("Enter the coordinates for the ship:");
        String input = scanner.nextLine();
        field.createShip(input);
    }
}
