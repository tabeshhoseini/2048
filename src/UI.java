import java.util.ArrayList;
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
                    leaderboardMenu();
                    break;
                case 0:
                    break;

                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);
    }

    private static int getUserInt(String message) {
        while (true) {
            System.out.println(message);
            String input = inputReader.nextLine();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter an integer.");
                continue;
            }
            // using error handling
            try {
                int userInt = Integer.parseInt(input);
                if (userInt < 0) {
                    System.out.println("input cannot be negative!");
                    continue;
                }
                return userInt;
            } catch (NumberFormatException e) {
                System.out.println(input + " is not a valid integer. Please try again.");
            }
        }

    }

    private static String getUserString(String message) {
        while (true) {
            System.out.println(message);
            String userText = inputReader.nextLine();

            if (userText.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a string!");
                continue;
            }

            return userText;
        }

    }

    private static char getUserChar(String message) {
        while (true) {
            System.out.println(message);
            String input = inputReader.nextLine();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a character.");
                continue;
            }
            char userChar = input.charAt(0);

            if (Character.isLetter(userChar)) {
                return userChar;
            } else {
                System.out.println("Please enter a valid letter (A-Z or a-z).");
            }
        }

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
            // AI assisted (for regex)
            username = getUserString("enter your username: \n"
                    + "it must contain characters, numbers and at least one special character like @, $, #, ...");
            if (League.validateUsername(username)) {
                if (League.isPlayerExist(username)) {
                    System.out.println("the username is already existed! try another usernaem.");
                    continue;
                }
                break;
            }
            System.out.println("please enter a valid username");

        }
        while (true) {
            password = getUserString("enter your passsword: \n" + "(at least 5 characters)");
            if (League.validatePassword(password)) {
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
                    "4. Achievements\n" +
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
                case 4:
                    showAchievements(player);
                    break;
                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);
    }

    private static void showBoard(Board board) {
        int size = board.getSize();
        int[][] grid = board.getBoard();

        String divider = "+-----".repeat(size) + "+";

        for (int i = 0; i < size; i++) {
            System.out.println(divider);
            for (int j = 0; j < size; j++) {
                String cell = grid[i][j] == 0 ? "   ." : String.format("%4d", grid[i][j]);
                System.out.print("|" + cell + " ");
            }
            System.out.println("|");
        }
        System.out.println(divider);
        System.out.println("Point: " + board.getPoint() + "\n");
    }

    private static void showAllBoards(Player player) {
        for (Board board : player.getBoards()) {
            System.out.println("id: " + board.getId());
            System.out.println("number of moves: " + board.getMoveNumber());
            System.out.println("undo count: " + board.getUndoCount());
            System.out.println("board status: " + board.getStatus());
            showBoard(board);
        }
    }

    private static void addNewBoard(Player player) {
        int boardSize;
        int choice;
        main: while (true) {
            System.out.println("\n\n\n1. 4x4\n" + "2. 6x6\n" + "3. 8x8\n" + "4. 10x10 (if your leading a leaderboard)\n"
                    + "5. Optional size(leading all leaderboards)");
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
                case 4:
                    if (League.checkPlayerLeadsTable(player)) {
                        boardSize = 10;
                        break main;
                    } else {
                        System.out.println("you don't have the permissino!");
                        break;
                    }
                case 5:
                    if (League.checkPlayerLeadsAllTables(player)) {
                        boardSize = getUserInt("choose the size of the board: ");
                        break main;
                    } else {
                        System.out.println("you don't have the permissino!");
                        break;
                    }
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
                    "r-right | l-left | u-up | d-down\n"
                            + "n-undo(only three times per board) | e-pause and exit | s-shuffle(only once)");
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
                    continue;
                case 'e':
                    break turn;
                case 's':
                    if (board.getShuffleUsed()) {
                        System.out.println("you have already used shuffle!");
                        continue;
                    }
                    board.shuffleBoard();
                    break;
                default:
                    System.out.println("enter a valid character!");
                    break;
            }
            if (board.checkWin()) {
                System.out.println("congrats! you won!");
                board.setStatus("Won");
                break turn;
            }
            if (board.isGameFinished()) {
                System.out.println("well done!");
                System.out.println("your point: " + board.getPoint());

                if (board.getUndoCount() < 3) {
                    String toUndo = getUserString("do you want to undo? y/n");
                    if (toUndo.equals("y")) {
                        undoBoard(board);
                        continue;
                    }
                }

                break turn;
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
                "\n Games: " + player.getGameNumber() +
                "\n Merges: " + player.getMergeNumber() +
                "\n Point average: " + player.getPointAverage() +
                "\n Highest number: " + player.getHighestNumber() +
                "\n Total undo: " + player.getUndoCount());
    }

    private static void undoBoard(Board board) {
        if (board.getPreviousBoards().isEmpty()) {
            System.out.println("you cannot undo right now!");
            return;
        }
        if (board.getUndoCount() == 3) {
            System.out.println("you have already used undo 3 times!");
            return;
        }

        int moveBackNumber;

        while (true) {
            moveBackNumber = getUserInt("how many moves do you want to undo?(MAX : 5) ");

            if (moveBackNumber > 5 || moveBackNumber < 1) {
                System.out.println("enter a valid number");
                continue;
            }

            board.addUndoCount();

            for (int i = 0; i < moveBackNumber; i++) {
                if (!board.getPreviousBoards().isEmpty()) {
                    board.undoBoard();
                } else {
                    System.out.println("your total moves are less than " + moveBackNumber);
                    break;
                }
            }
            break;
        }
    }

    private static void showLeaderboard(int boardSize) {
        ArrayList<Player> players = League.sortPlayersByPoints(boardSize);

        System.out.println(boardSize + "x" + boardSize + " Leaderboard");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getUsername() + "  |  "
                    + players.get(i).getBoardsPointBySize(boardSize));
        }

    }

    private static void leaderboardMenu() {
        int choice;
        do {
            System.out.println(
                    "\n\n\n\n__________LeaderBoard Menu__________\n" + "1. 4x4\n" + "2. 6x6\n" + "3. 8x8\n"
                            + "0. Exit");
            choice = getUserInt("choose a leaderboard:  ");
            switch (choice) {
                case 1:
                    showLeaderboard(4);
                    break;
                case 2:
                    showLeaderboard(6);
                    break;
                case 3:
                    showLeaderboard(8);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("choose a valid number!");
                    break;
            }
        } while (choice != 0);
    }

    private static void showAchievements(Player player) {
        System.out.println("--------User Achievements--------" +
                "\n1000 points reached: " + player.getPointAchievement() +
                "\n5 games played: " + player.checkFiveGamesPlayed());
    }
}