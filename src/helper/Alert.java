package helper;

import java.util.Scanner;

public class Alert {
    /**
     * Repository for all alert messages
     * @param errorType Error code
     */
    public static void getError(int errorType) {
        switch (errorType) {
            case 1:
                System.out.println("Error! Invalid input! Try again:");
                break;
            case 2:
                System.out.println("Error! Wrong length! Try again: ");
                break;
            case 3:
                System.out.println("Error! You placed it too close to another one. Try again: ");
                break;
        }
    }

    public static void getInformation(int infoType) {
        switch (infoType) {
            case 1:
                System.out.println("You hit a ship!");
                break;
            case 2:
                System.out.println("You sank a ship!");
                break;
            case 3:
                System.out.println("You missed!");
                break;
            case 4:
                Scanner scanner = new Scanner(System.in);
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                System.out.flush();
                break;
            case 5:
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
        }
    }
}
