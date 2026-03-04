import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        int choice;
        do {
            showLoginMenu();
            choice = getUserInt("enter your choice: ");
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

        // TEST TEST TEST
        // Board board = new Board(0, 4);
        // for (int i = 0; i < 4; i++) {
        // for (int j = 0; j < 4; j++) {
        // System.out.print(board.getBoard()[i][j] + " ");
        // }
        // System.out.println();
        // }
        // System.out.println();

        // for (int i = 0; i < 4; i++) {
        // for (int j = 0; j < 4; j++) {
        // System.out.print(board.getBoard()[i][j] + " ");
        // }
        // System.out.println();
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

    private static char getUserChar(String message) {
        System.out.println(message);
        char userChar = inputReader.nextLine().charAt(0);
        return userChar;
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
        playerMenu(League.getPlayerByUsername(username));
    }

    private static void playerMenu(Player player) {

        int choice;
        do {
            System.out.println("\n\n_____________Game menu_____________\n" +
                    "1. Add and play new board\n" +
                    "2. Recent boards\n" +
                    "3. Game stats\n" +
                    "0. Exit");
            choice = getUserInt("");
            switch (choice) {
                case 1:
                    addNewBoard(player);
                    break;
                case 2:
                    showAllBoards(player);
                    Board board = loadBoard(player);
                    playBoard(board);
                case 3:
                    // game stats
                    break;
                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);
    }

    private static void showBoard(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                System.out.print(board.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void showAllBoards(Player player) {
        for (Board board : player.getBoards()) {
            System.out.println("id: " + board.getId());
            showBoard(board);
        }
    }

    private static void addNewBoard(Player player) {
        int boardSize;
        int choice;
        main: while (true) {
            System.out.println("1. 4x4\n" + "2. 6x6\n" + "3. 8x8\n" + "4. 10x10 (if your leading a leaderboard)");
            choice = getUserInt("choose your board: ");
            switch (choice) {
                case 1:
                    boardSize = 4;
                    break main;
                case 2:
                    boardSize = 6;
                    break main;
                case 3:
                    boardSize = 8;
                    break main;
                // case 4:

                // break main;
                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        }
        int boardId = player.addNewBoard(boardSize);
        playBoard(player.getBoardById(boardId));
    }

    public static Board loadBoard(Player player) {
        int boardId;
        while (true) {
            boardId = getUserInt("choose the board by id: ");
            if (player.getBoardById(boardId) != null) {
                return player.getBoardById(boardId);
            }
            System.out.println("board not found!");
        }
    }

    public static void playBoard(Board board) {
        char move;
        turn: while (true) {
            showBoard(board);
            System.out.println("r-right | l-left | u-up | d-down\n" + "e-pause and exit");
            move = getUserChar("enter your move: ");
            switch (move) {
                case 'r':
                    board.moveRight();
                    break;
                case 'l':
                    board.moveLeft();
                    break;
                case 'u':
                    board.moveUp();
                    break;
                case 'd':
                    board.moveDown();
                    break;
                case 'e':
                    break turn;
                default:
                    System.out.println("enter a valid character!");
                    break;
            }
            if (board.isGameFinished()) {
                if (board.checkWin()) {
                    System.out.println("congrats! you won!");
                    break turn;
                } else {
                    System.out.println("well done!");
                    System.out.println("your point: " + board.getPoint());
                    break turn;
                }
            }
            board.addRandomBlock();
        }
    }
}