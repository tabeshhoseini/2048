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
        ArrayList<Player> playersList = players;

        // sort with lambda expression
        playersList.sort((p1, p2) -> (p2.getBoardsPointBySize(boardSize) - p1.getBoardsPointBySize(boardSize)));

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