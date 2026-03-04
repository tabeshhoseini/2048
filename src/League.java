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
}