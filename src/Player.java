import java.util.ArrayList;

public class Player {
    private int boardBaseId = 0;

    private String username;
    private String password;

    // game stats
    private int overallPoint;
    private int highestPoint;
    private int averagePoint;
    private int highestNumber;
    private int gameNumber;
    private int moveNumber;
    private int mergeNumber;
    private int undoNumber;

    private ArrayList<Board> boards = new ArrayList<Board>();

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int addNewBoard(int size) {
        boardBaseId++;
        Board board = new Board(boardBaseId, size);
        boards.add(board);
        return boardBaseId;
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

    public Board getBoardById(int id) {
        for (Board board : boards) {
            if (board.getId() == id) {
                return board;
            }
        }
        return null;
    }

    public int getOverallPoint() {
        for (Board board : boards) {
            this.overallPoint += board.getPoint();
        }
        return this.overallPoint;
    }
}