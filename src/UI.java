import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    static Scanner inputReader = new Scanner(System.in);

    public static void runGame() {
        int choice;
        do {
            System.out.println("\n\n\n");
            showLoginMenu();
            choice = getUserInt("enter your choice: ");
            switch (choice) {
                case 1:
                    loginPlayer();
                    break;
                case 2:
                    registerPlayer();
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
                System.out.println("\u001B[31m" + "Input cannot be empty. Please enter an integer." + "\u001B[0m");
                continue;
            }
            // using error handling
            try {
                int userInt = Integer.parseInt(input);
                if (userInt < 0) {
                    System.out.println("\u001B[31m" + "input cannot be negative!" + "\u001B[0m");
                    continue;
                }
                return userInt;
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m" + input + " is not a valid integer. Please try again." + "\u001B[0m");

            }
        }

    }

    private static String getUserString(String message) {
        while (true) {
            System.out.println(message);
            String userText = inputReader.nextLine();

            if (userText.isEmpty()) {
                System.out.println("\u001B[31m" + "Input cannot be empty. Please enter a string!" + "\u001B[0m");
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
                System.out.println("\u001B[31m" + "Input cannot be empty. Please enter a character." + "\u001B[0m");
                continue;
            }
            if (input.length() > 1) {
                System.out.println("\u001B[31m" + "Input must be a character!" + "\u001B[0m");
                continue;
            }
            char userChar = input.charAt(0);

            if (Character.isLetter(userChar)) {
                return userChar;
            } else {
                System.out.println("\u001B[31m" + "Please enter a valid letter (A-Z or a-z)." + "\u001B[0m");
            }
        }

    }

    private static void printMenuHeader(String title) {
        System.out.println("╔════════════════════════════════╗");
        System.out.printf("║  %-30s║%n", title);
        System.out.println("╠════════════════════════════════╣");
    }

    private static void printMenuOption(String option) {
        System.out.printf("║  %-30s║%n", option);
    }

    private static void printMenuFooter() {
        System.out.println("╚════════════════════════════════╝");
    }

    private static void showLoginMenu() {
        printMenuHeader("2048");
        printMenuOption("1. Log in");
        printMenuOption("2. Register");
        printMenuOption("3. League leaderboard");
        printMenuOption("0. Exit");
        printMenuFooter();
    }

    private static void registerPlayer() {
        String username;
        String password;

        main: while (true) {

            System.out.println("\n\n");
            username = getUserString("enter your username('exit' for exit) \n"
                    + "it must contain characters, numbers and at least one special character like @, $, #, ...");

            if (username.equals("exit")) {
                return;
            }
            switch (League.validateUsername(username)) {
                case 0:
                    if (League.isPlayerExist(username)) {
                        System.out.println(
                                "\u001B[31m" + "the username is already exists! try another username." + "\u001B[0m");
                        continue;
                    }
                    break main;
                case 1:
                    System.out.println("\u001B[31m" + "username must contain characters." + "\u001B[0m");
                    break;
                case 2:
                    System.out.println(
                            "\u001B[31m" + "username must contain at least on special character." + "\u001B[0m");
                    break;
                case 3:
                    System.out.println("\u001B[31m" + "username must contain at least one number." + "\u001B[0m");
                    break;
            }
        }
        while (true) {
            System.out.println("\n\n");
            password = getUserString("enter your password:('exit' for exit) \n" + "(at least 5 characters)");

            if (password.equals("exit")) {
                return;
            }
            if (League.validatePassword(password)) {
                break;
            }
            System.out.println("\u001B[31m" + "password must be at least 5 characters." + "\u001B[0m");
        }
        League.addPlayer(username, password);
    }

    private static void loginPlayer() {
        String username;
        String password;
        while (true) {
            System.out.println("\n\n");
            username = getUserString("enter your username: \n" + "or type 'exit'");
            if (League.isPlayerExist(username)) {
                break;
            } else if (username.equals("exit")) {
                return;
            }
            System.out.println("\u001B[31m" + "player not found!" + "\u001B[0m");

        }
        while (true) {
            System.out.println("\n\n");
            password = getUserString("enter your password:\n" + "or type 'exit'");
            if (League.checkPassword(password, username)) {
                break;
            } else if (password.equals("exit")) {
                return;
            }
            System.out.println("\u001B[31m" + "password does not match!" + "\u001B[0m");
        }
        playerMenu(League.getPlayerByUsername(username));
    }

    private static void playerMenu(Player player) {

        int choice;
        do {
            showPlayerMenu();

            choice = getUserInt("");
            switch (choice) {
                case 1:
                    addNewBoard(player);
                    break;
                case 2:
                    if (player.getBoards().isEmpty()) {
                        System.out.println("\u001B[31m" + "you haven't played any board yet!" + "\u001B[0m");
                        break;
                    }
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
                case 0:
                    return;
                default:
                    System.out.println("\u001B[31m" + "choose a valid number!" + "\u001B[0m");
                    break;
            }
        } while (choice != 0);
    }

    private static void showPlayerMenu() {
        System.out.println("\n\n");
        printMenuHeader("Game menu");
        printMenuOption("1. Add and play new board");
        printMenuOption("2. Recent boards");
        printMenuOption("3. Game stats");
        printMenuOption("4. Achievements");
        printMenuOption("0. Exit");
        printMenuFooter();
    }

    private static void showBoard(Board board) {
        int size = board.getSize();
        int[][] grid = board.getBoard();

        String divider = "+-----".repeat(size) + "+";

        for (int i = 0; i < size; i++) {
            System.out.println(divider);
            for (int j = 0; j < size; j++) {
                String cell = grid[i][j] == 0 ? "   ." : String.format("%4d", grid[i][j]);
                System.out.print("|" + "\u001B[97m" + cell + "\u001B[0m" + " ");
            }
            System.out.println("|");
        }
        System.out.println(divider);
        System.out.println("Point: " + board.getPoint());
        System.out.println("Remaining undo: " + (3 - board.getUndoCount() + "\n"));
    }

    private static void showAllBoards(Player player) {
        System.out.println("\n");
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
            printMenuHeader("choose the board");
            printMenuOption("1. 4x4");
            printMenuOption("2. 6x6");
            printMenuOption("3. 8x8");
            printMenuOption("4. 10x10");
            printMenuOption("5. Optional size");
            printMenuFooter();

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
                        System.out.println("\u001B[31m" + "you are not leading a leaderboard!" + "\u001B[0m");
                        break;
                    }
                case 5:
                    if (League.checkPlayerLeadsAllTables(player)) {
                        boardSize = getUserInt("choose the size of the board: ");
                        break main;
                    } else {
                        System.out.println("\u001B[31m" + "you are not leading all leaderboards!" + "\u001B[0m");
                        break;
                    }
                default:
                    System.out.println("\u001B[31m" + "choose a valid number!" + "\u001B[0m");
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
            if (boardId == 0) {
                break;
            }
            System.out.println("\u001B[31m" + "board not found!" + "\u001B[0m");

        } while (boardId != 0);
        return null;
    }

    private static void playBoard(Board board) {
        char move;
        boolean validMove;
        boolean isShuffleMove;
        turn: while (true) {
            System.out.println("\n\n");
            showBoard(board);
            System.out.println(
                    "[w/a/s/d] Move  [n] Undo  [f] Shuffle  [e] Exit");
            move = getUserChar("enter your move: ");
            validMove = false;
            isShuffleMove = false;

            switch (move) {
                case 'd':
                    validMove = board.moveRight();
                    break;
                case 'a':
                    validMove = board.moveLeft();
                    break;
                case 'w':
                    validMove = board.moveUp();
                    break;
                case 's':
                    validMove = board.moveDown();
                    break;
                case 'n':
                    undoBoard(board);
                    continue;
                case 'e':
                    break turn;
                case 'f':
                    if (board.getShuffleUsed()) {
                        System.out.println("\u001B[31m" + "you have already used shuffle!" + "\u001B[0m");
                        continue;
                    }
                    board.shuffleBoard();
                    isShuffleMove = true;
                    break;
                default:
                    System.out.println("\u001B[31m" + "enter a valid character!" + "\u001B[0m");
                    continue;
            }

            if (validMove) {
                board.addRandomBlock();
            } else if (!isShuffleMove) {
                System.out.println("\u001B[31m" + "Invalid move!!!" + "\u001B[0m");
                continue;
            }

            if (board.checkWin()) {
                System.out.println("congrats! you won!");
                break turn;
            }
            if (board.isGameFinished()) {
                showBoard(board);
                System.out.println("well done!");

                if (board.checkUserCanUndo()) {
                    while (true) {
                        String toUndo = getUserString("do you want to undo?y/n");
                        if (toUndo.equals("y")) {
                            undoBoard(board);
                            continue turn;
                        } else if (toUndo.equals("n")) {
                            break;
                        } else {
                            System.out.println("\u001B[31m" + "Enter y or n!" + "\u001B[0m");
                            continue;
                        }
                    }
                }

                board.setStatusToFinished();
                break turn;
            }
        }

    }

    private static void showStats(Player player) {
        printMenuHeader(" Game Stats");
        printMenuOption(" Overall points: " + player.getOverallPoint());
        printMenuOption(" Highest point: " + player.getHighestPoint());
        printMenuOption(" Games: " + player.getGameNumber());
        printMenuOption(" Merges: " + player.getMergeNumber());
        printMenuOption(" Point average: " + player.getPointAverage());
        printMenuOption(" Highest number: " + player.getHighestNumber());
        printMenuOption(" Total undo: " + player.getUndoCount());
        printMenuFooter();
    }

    private static void undoBoard(Board board) {
        if (board.getPreviousBoards().isEmpty()) {
            System.out.println("\u001B[31m" + "No more moves left in history!" + "\u001B[0m");
            return;
        }
        if (!board.checkUserCanUndo()) {
            System.out.println("\u001B[31m" + "you have already used undo 3 times!" + "\u001B[0m");
            return;
        }

        int moveBackNumber;

        while (true) {
            moveBackNumber = getUserInt(
                    "how many moves do you want to undo?(MAX :5)");

            if (moveBackNumber > 5 || moveBackNumber < 1) {
                System.out.println("\u001B[31m" + "enter a valid number!" + "\u001B[0m");
                continue;
            }

            board.addUndoCount();

            for (int i = 0; i < moveBackNumber; i++) {
                if (!board.getPreviousBoards().isEmpty()) {
                    board.undoBoard();
                } else {
                    System.out.println("\u001B[31m" + "your total moves are less than " + moveBackNumber + "\u001B[0m");
                    break;
                }
            }
            break;
        }
    }

    private static void showLeaderboard(int boardSize) {
        ArrayList<Player> players = League.sortPlayersByPoints(boardSize);

        printMenuHeader(boardSize + "x" + boardSize + " Leaderboard");
        for (int i = 0; i < players.size(); i++) {
            String row = String.format("%-15s| %d",
                    (i + 1) + ". " + players.get(i).getUsername(),
                    players.get(i).getBoardsPointBySize(boardSize));
            printMenuOption(row);
        }
        printMenuFooter();

    }

    private static void leaderboardMenu() {
        int choice;
        do {

            showLeaderboardMenu();

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
                    System.out.println("\u001B[31m" + "choose a valid number!" + "\u001B[0m");
                    break;
            }
        } while (choice != 0);
    }

    private static void showLeaderboardMenu() {
        printMenuHeader("LeaderBoard Menu");
        printMenuOption("1. 4x4");
        printMenuOption("2. 6x6");
        printMenuOption("3. 8x8");
        printMenuOption("0. Exit");
        printMenuFooter();
    }

    private static void showAchievements(Player player) {
        printMenuHeader("User Achievements");
        printMenuOption("1000 points reached: " + player.getPointAchievement());
        printMenuOption("5 games played: " + player.checkFiveGamesPlayed());
        printMenuFooter();
    }
}