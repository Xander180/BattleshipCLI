import java.util.Arrays;

public class Field {
    private static char[][] field = new char[10][10];

    public Field() {
        for (char[] chars : field) {
            Arrays.fill(chars, '~');
        }
    }

    public void printField() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i < field.length; i++) {
            if (i == 0) {
                System.out.print("  ");
                continue;
            }
            System.out.print(i + " ");
        }

        for (int i = 0; i < field.length; i++) {
            if (i == 0) {
                System.out.println(" ");
                continue;
            }
            System.out.print(letters[i]);
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    public void createShip(String coordinates) {
        String[] chars = coordinates.split("(?!^)");
        System.out.println(Arrays.toString(chars));
    }
}
