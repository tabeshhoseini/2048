import java.util.ArrayList;

public class League {

    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void addPlayer(String username, String password) {
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
    }
}