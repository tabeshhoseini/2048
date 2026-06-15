import java.util.ArrayList;

public class League {

    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void addPlayer(String username, String password) {
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static int validateUsername(String username) {
        if (!username.matches(".*[a-zA-Z].*")) {
            return 1;
        }
        if (!username.matches(".*[@#$&*%!?].*")) {
            return 2;
        }
        if (!username.matches(".*[0-9].*")) {
            return 3;
        }
        return 0;
    }

    public static boolean validatePassword(String password) {
        if (password.matches(".{5,}")) {
            return true;
        }
        return false;
    }

    public static boolean isPlayerExist(String username) {
        for (Player i : players) {
            if (i.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // we have already checked if the user with this username exists, therefore this
    // method is only for the password
    public static boolean checkPassword(String password, String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username) && player.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static Player getPlayerByUsername(String username) {
        for (Player i : players) {
            if (i.getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }

    public static ArrayList<Player> sortPlayersByPoints(int boardSize) {
        ArrayList<Player> playersList = new ArrayList<>(players);

        for (int i = 0; i < playersList.size(); i++) {
            for (int j = i + 1; j < playersList.size(); j++) {
                if (playersList.get(i).getBoardsPointBySize(boardSize) < playersList.get(j)
                        .getBoardsPointBySize(boardSize)) {
                    Player temp = playersList.get(j);
                    playersList.set(j, playersList.get(i));
                    playersList.set(i, temp);
                }
            }
        }
        return playersList;
    }

    public static boolean checkPlayerLeadsTable(Player player) {
        if ((player == sortPlayersByPoints(4).get(0) || player == sortPlayersByPoints(6).get(0)
                || player == sortPlayersByPoints(8).get(0)) && player.getOverallPoint() != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkPlayerLeadsAllTables(Player player) {
        if (player == sortPlayersByPoints(4).get(0) && player == sortPlayersByPoints(6).get(0)
                && player == sortPlayersByPoints(8).get(0) && player.getOverallPoint() != 0) {
            return true;
        }
        return false;
    }
}