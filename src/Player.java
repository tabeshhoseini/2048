import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private int id;
    private static int base_id = 0;
    private ArrayList<Board> boards = new ArrayList<Board>();

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = getNextId();

    }

    private int getNextId() {
        base_id++;
        return base_id;
    }

}
