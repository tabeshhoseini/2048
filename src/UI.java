import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        Board board = new Board(0, 4);
        for (int i = 0; i < board.getBoardLength(); i++) {
            for (int j = 0; j < board.getBoardLength(); j++) {
                System.out.print(board.getBoard()[i][j]);
            }
            System.out.println();
        }
    }
}
