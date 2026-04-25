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
                    if (board != null) {
                        playBoard(board);
                    }
                    break;
                case 3:
                    showStats(player);
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
            System.out.println("number of moves: " + board.getMoveNumber());
            System.out.println("board status: " + board.getStatus());
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

    private static Board loadBoard(Player player) {
        int boardId;
        do {
            boardId = getUserInt("choose the board by id: (enter 0 to exit)");
            if (player.getBoardById(boardId) != null) {
                if (!player.getBoardById(boardId).isBoardOpen()) {
                    System.out.println("the board is finished.");
                    continue;
                }
                return player.getBoardById(boardId);
            }
            System.out.println("board not found!");
        } while (boardId != 0);
        return null;
    }

    private static void playBoard(Board board) {
        char move;
        boolean validMove;
        turn: while (true) {
            showBoard(board);
            System.out.println(
                    "r-right | l-left | u-up | d-down\n" + "n-undo(only three times per board) | e-pause and exit");
            move = getUserChar("enter your move: ");
            validMove = false;

            switch (move) {
                case 'r':
                    validMove = board.moveRight();
                    break;
                case 'l':
                    validMove = board.moveLeft();
                    break;
                case 'u':
                    validMove = board.moveUp();
                    break;
                case 'd':
                    validMove = board.moveDown();
                    break;
                case 'n':
                    undoBoard(board);
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
                    board.setStatus("Won");
                    break turn;
                } else {
                    System.out.println("well done!");
                    System.out.println("your point: " + board.getPoint());
                    board.setStatus("Finished");
                    break turn;
                }
            }
            if (validMove) {
                board.addRandomBlock();
            }
        }
    }

    private static void showStats(Player player) {
        System.out.println(player.getUsername() + " game stats" +
                "\n Overall points: " + player.getOverallPoint() +
                "\n Highest point: " + player.getHighestPoint() +
                "\n Number of games: " + player.getGameNumber() +
                "\n Number of merges: " + player.getMergeNumber() +
                "\n Point average: " + player.getPointAverage() +
                "\n Highest number: " + player.getHighestNumber());
    }

    private static void undoBoard(Board board) {
        if (board.getPreviousBoard() == null) {
            System.out.println("you haven't had any move yet!");
            return;
        }
        int moveBackNumber;

        while (true) {
            moveBackNumber = getUserInt("how many moves do you want to undo?(MAX : 5) ");

            if (moveBackNumber > 5 || moveBackNumber < 1) {
                System.out.println("enter a valid number");
                continue;
            }

            for (int i = 0; i < moveBackNumber; i++) {
                if (board.getPreviousBoard() != null) {
                    Board previousBoard = board.getPreviousBoard();
                    board = previousBoard;
                }
            }
            break;
        }
    }
}