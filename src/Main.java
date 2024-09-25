import java.util.Scanner;

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