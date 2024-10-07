package model;

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
                System.out.println("You hit a ship! Try again:");
                break;
            case 2:
                System.out.println("You sank a ship! Specify a new target:");
                break;
        }
    }
}
