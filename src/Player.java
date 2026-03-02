import java.util.ArrayList;

public class Player {
    private static int base_id = 0;

    private String username;
    private String password;
    private int id;

    // game stats
    private int overallPoints;
    private int highestPoint;
    private int averagePoint;
    private int highestNumber;
    private int gamenumber;
    private int moveNumber;
    private int mergenumber;
    private int undoNumber;

    private ArrayList<Board> boards = new ArrayList<Board>();

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = getNextId();

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private int getNextId() {
        base_id++;
        return base_id;
    }
}