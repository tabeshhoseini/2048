import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        showLoginMenu();
        // Board board = new Board(0, 4);
        // for (int i = 0; i < board.getBoardLength(); i++) {
        // for (int j = 0; j < board.getBoardLength(); j++) {
        // System.out.print(board.getBoard()[i][j]);
        // }
        // System.out.println();
    }

    private static void showLoginMenu() {
        System.out.println("___________2048___________\n" +
                "1. Login\n" +
                "2. Sign up\n" +
                "3. League table\n" +
                "0. Exit");
    }

}
