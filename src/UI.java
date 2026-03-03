import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        // int choice;
        // do {
        // showLoginMenu();
        // choice = getUserInt("enter your choice: ");
        // switch (choice) {
        // case 1:
        // loginPlayer();
        // break;
        // case 2:
        // signPlayer();
        // break;
        // case 3:

        // break;
        // case 0:
        // break;

        // default:
        // System.out.println("choose a valid number!");
        // break;
        // }
        // } while (choice != 0);

        // TEST TEST TEST
        Board board = new Board(0, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board.getBoard()[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board.getBoard()[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();

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

        System.out.println("\n\n___________2048___________\n" +
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
        playerMenu();
    }

    private static void playerMenu() {
        System.out.println("\n\n_____________Game menu_____________\n" +
                "1. Add and play new board\n" +
                "2. Recent boards\n" +
                "3. Game stats\n" +
                "0. Exit");

        int choice;
        do {
            choice = getUserInt("");
            switch (choice) {
                case 1:
                    // add and play boards
                    break;
                case 2:
                    // boards history
                    break;
                case 3:
                    // game stats
                    break;
                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);
    }

}