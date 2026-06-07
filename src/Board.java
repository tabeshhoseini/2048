import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int id;
    private int size;
    private int point;
    private int mergeNumber;
    private int moveNumber;
    private int undoCount;
    private String status; // won, finished, paused
    private int[][] board;
    private Random random;

    private ArrayList<BoardCopy> previousBoards = new ArrayList<>();

    public Board(
            int id,
            int size) {
        this.id = id;
        this.size = size;
        this.point = 0;
        this.mergeNumber = 0;
        this.moveNumber = 0;
        this.undoCount = 0;
        this.board = new int[size][size];
        this.random = new Random();
        this.status = "Paused";

        addRandomBlock();
        addRandomBlock();
    }

    public void addRandomBlock() {
        ArrayList<Block> emptyBlocks = findEmptyBlocks();

        Block block = emptyBlocks.get(random.nextInt(emptyBlocks.size()));
        // 90% chance of 2, 10% chance of 4
        board[block.row][block.col] = random.nextDouble() < 0.9 ? 2 : 4;
    }

    private ArrayList<Block> findEmptyBlocks() {
        ArrayList<Block> emptyBlocks = new ArrayList<Block>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    emptyBlocks.add(new Block(i, j));
                }
            }
        }
        return emptyBlocks;
    }

    public boolean moveLeft() {
        boolean isMoved = false;

        for (int i = 0; i < size; i++) {
            int[] newLine = mergeLine(board[i]);
            if (!arrayEquals(newLine, board[i])) {
                if (!isMoved) {
                    BoardCopy lastBoard = new BoardCopy(board, point, mergeNumber, moveNumber);
                    previousBoards.add(lastBoard);
                    if (previousBoards.size() > 5) {
                        previousBoards.remove(0);
                    }
                }
                isMoved = true;
                board[i] = newLine;
            }
        }
        if (isMoved) {
            moveNumber++;
        }
        return isMoved;
    }

    public boolean moveRight() {
        boolean isMoved = false;

        for (int i = 0; i < size; i++) {
            int[] reversedArray = reverseArray(board[i]);
            int[] newLine = mergeLine(reversedArray);
            if (!arrayEquals(newLine, reversedArray)) {
                if (!isMoved) {
                    BoardCopy lastBoard = new BoardCopy(board, point, mergeNumber, moveNumber);
                    previousBoards.add(lastBoard);
                    if (previousBoards.size() > 5) {
                        previousBoards.remove(0);
                    }
                }
                isMoved = true;
                board[i] = reverseArray(newLine);
            }
        }
        if (isMoved) {
            moveNumber++;
        }

        return isMoved;
    }

    public boolean moveUp() {
        boolean isMoved = false;

        for (int i = 0; i < size; i++) {
            int[] newLine = mergeLine(getColumn(i));
            if (!arrayEquals(newLine, getColumn(i))) {
                if (!isMoved) {
                    BoardCopy lastBoard = new BoardCopy(board, point, mergeNumber, moveNumber);
                    previousBoards.add(lastBoard);
                    if (previousBoards.size() > 5) {
                        previousBoards.remove(0);
                    }
                }
                isMoved = true;
                setColumn(newLine, i);
            }
        }
        if (isMoved) {
            moveNumber++;
        }

        return isMoved;
    }

    public boolean moveDown() {
        boolean isMoved = false;

        for (int i = 0; i < size; i++) {
            int[] reversedArray = reverseArray(getColumn(i));
            int[] newLine = mergeLine(reversedArray);
            if (!arrayEquals(newLine, reversedArray)) {
                if (!isMoved) {
                    BoardCopy lastBoard = new BoardCopy(board, point, mergeNumber, moveNumber);
                    previousBoards.add(lastBoard);
                    if (previousBoards.size() > 5) {
                        previousBoards.remove(0);
                    }
                }
                isMoved = true;
                setColumn(reverseArray(newLine), i);
            }
        }
        if (isMoved) {
            moveNumber++;
        }
        return isMoved;
    }

    private int[] mergeLine(int[] line) {
        int[] newLine = new int[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (line[i] != 0) {
                newLine[index] = line[i];
                index++;
            }
        }

        for (int i = 0; i < size - 1; i++) {
            if (newLine[i] != 0 && newLine[i] == newLine[i + 1]) {
                newLine[i] = newLine[i] * 2;
                point += newLine[i];
                newLine[i + 1] = 0;
                mergeNumber++;
            }
        }

        int[] finalLine = new int[size];
        index = 0;
        for (int i = 0; i < size; i++) {
            if (newLine[i] != 0) {
                finalLine[index] = newLine[i];
                index++;
            }
        }

        return finalLine;

    }

    private boolean arrayEquals(int[] array1, int[] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    private int[] reverseArray(int[] array) {
        int[] newArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[array.length - 1 - i];
        }
        return newArray;
    }

    public boolean isGameFinished() {
        if (findEmptyBlocks().isEmpty()) {
            // check for each row and column if any move is possible
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size - 1; j++) {
                    if (board[i][j] == board[i][j + 1]) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size - 1; j++) {
                    if (getColumn(i)[j] == getColumn(i)[j + 1]) {
                        return false;
                    }
                }
            }
            status = "finished";
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == 2048) {
                    status = "Won";
                    return true;
                }
        return false;
    }

    public void undoBoard() {
        this.board = previousBoards.get(previousBoards.size() - 1).board;
        this.point = previousBoards.get(previousBoards.size() - 1).point;
        this.mergeNumber = previousBoards.get(previousBoards.size() - 1).mergeNumber;
        this.moveNumber = previousBoards.get(previousBoards.size() - 1).moveNumber;
        previousBoards.remove(previousBoards.size() - 1);
    }

    private int[] getColumn(int index) {
        int[] column = new int[size];
        for (int i = 0; i < size; i++) {
            column[i] = board[i][index];
        }
        return column;
    }

    private void setColumn(int[] line, int index) {
        for (int i = 0; i < size; i++) {
            board[i][index] = line[i];
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBoardOpen() {
        if (this.status.equals("Won") || this.status.equals("Finished")) {
            return false;
        }
        return true;
    }

    public int getHighestNumber() {
        int highest = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] > highest) {
                    highest = board[i][j];
                }
            }
        }
        return highest;
    }

    public String getStatus() {
        return status;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public int getPoint() {
        return point;
    }

    public int getMergeNumber() {
        return mergeNumber;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public int getUndoCount() {
        return undoCount;
    }

    public void addUndoCount() {
        undoCount++;
    }

    public ArrayList<BoardCopy> getPreviousBoards() {
        return previousBoards;
    }

}