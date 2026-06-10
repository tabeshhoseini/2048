import java.util.ArrayList;

public class Player {
    private int boardBaseId = 0;

    private String username;
    private String password;

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
        int points = 0;
        for (Board board : boards) {
            points += board.getPoint();
        }
        return points;
    }

    public int getHighestPoint() {
        int highest = 0;
        for (Board board : boards) {
            if (board.getPoint() > highest) {
                highest = board.getPoint();
            }
        }
        return highest;
    }

    public int getGameNumber() {
        return boards.size();
    }

    public int getMergeNumber() {
        int mergeNumber = 0;
        for (Board board : boards) {
            mergeNumber += board.getMergeNumber();
        }
        return mergeNumber;
    }

    public float getPointAverage() {
        if (getGameNumber() == 0) {
            return 0;
        }
        return getOverallPoint() / getGameNumber();
    }

    public int getHighestNumber() {
        int highest = 0;
        for (Board board : boards) {
            if (board.getHighestNumber() > highest) {
                highest = board.getHighestNumber();
            }
        }
        return highest;
    }

    public int getUndoCount() {
        int undoCount = 0;
        for (Board board : boards) {
            undoCount += board.getUndoCount();
        }
        return undoCount;
    }

    public int getBoardsPointBySize(int boardSize) {
        int point = 0;
        for (Board board : boards) {
            if (board.getSize() == boardSize) {
                point += board.getPoint();
            }
        }
        return point;
    }

    public int getPointAchievement() {
        int count = 0;
        for (Board board : boards) {
            if (board.getPoint() > 999) {
                count++;
            }
        }
        return count;
    }

    public boolean checkFiveGamesPlayed() {
        if (boards.size() >= 5) {
            return true;
        }
        return false;
    }
}