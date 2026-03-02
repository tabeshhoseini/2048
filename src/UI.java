import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        int choice;
        do {
            showLoginMenu();
            choice = getUserInt("choose your rule.");
            switch (choice) {
                case 1:
                    loginPlayer();
                    break;
                case 2:
                    signPlayer();
                    break;
                case 3:

                    break;
                case 0:
                    break;

                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);

        // Board board = new Board(0, 4);
        // for (int i = 0; i < board.getBoardLength(); i++) {
        // for (int j = 0; j < board.getBoardLength(); j++) {
        // System.out.print(board.getBoard()[i][j]);
        // }
        // System.out.println();
    }

    private static int getUserInt(String message) {
        System.out.println(message);
        int userNumber = inputReader.nextInt();
        inputReader.nextLine();
        return userNumber;
    }

    private static String getUserString(String message) {
        System.out.println(message);
        String userText = inputReader.nextLine();
        return userText;
    }

    private static void showLoginMenu() {

        System.out.println("___________2048___________\n" +
                "1. Login\n" +
                "2. Sign up\n" +
                "3. League leaderboard\n" +
                "0. Exit");
    }

    private static void signPlayer() {
        String username;
        String password;
        while (true) {
            username = getUserString("enter your username: \n"
                    + "it must contain characters, numbers and at least one special character like @, $, #, ...");
            if (username.matches(".*\\w.*") && username.matches(".*[@#$&*%!?].*")) {
                break;
            }
            System.out.println("please enter a valid username");
        }
        while (true) {
            password = getUserString("enter your passsword: \n" + "(at least 5 characters)");
            if (password.matches(".{5,}")) {
                break;
            }
            System.out.println("please enter a valid password");
        }
        League.addPlayer(username, password);
    }

    private static void loginPlayer() {
        String username;
        String password;
        while (true) {
            username = getUserString("enter your username: \n" + "or type 'exit'");
            if (League.isPlayerExist(username)) {
                break;
            } else if (username.equals("exit")) {
                return;
            }
            System.out.println("player not found!");
        }
        while (true) {
            password = getUserString("enter your passsword:\n" + "or type 'exit'");
            if (League.checkPassword(password, username)) {
                break;
            } else if (password.equals("exit")) {
                return;
            }
            System.out.println("password is incorrect");
        }
        // game menu
    }

    private static void playMenu() {
        System.out.println("game menu");
        // switch (inputReader) {
        // case value:

        // break;

        // default:
        // break;
        // }
    }

}