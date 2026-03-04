import java.util.ArrayList;

public class Player {
    private int boardBaseId = 0;

    private String username;
    private String password;

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

    }

    public void addNewBoard(int size) {
        boardBaseId++;
        Board board = new Board(boardBaseId, size);
        boards.add(board);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }
}