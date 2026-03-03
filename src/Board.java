import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.Position;

public class Board {

    private int id;
    private int size;
    private int point;
    private int[][] board;
    private Random random;

    public Board(int id, int size) {
        this.id = id;
        this.size = size;
        this.board = new int[size][size];
        this.random = new Random();

        addRandomBlock();
        addRandomBlock();
    }

    private void addRandomBlock() {
        List<Block> emptyBlocks = findEmptyBlocks();

        Block block = emptyBlocks.get(random.nextInt(emptyBlocks.size()));
        // 90% chance of 2, 10% chance of 4
        board[block.row][block.col] = random.nextDouble() < 0.9 ? 2 : 4;
    }

    private List<Block> findEmptyBlocks() {
        List<Block> emptyBlocks = new ArrayList<>();

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
            int[] newLine = new int[size];
            int index = 0;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    newLine[index] = board[i][j];
                    index++;
                    isMoved = true;
                }
            }
            board[i] = newLine;
        }
        return isMoved;
    }

    // private int[] mergeLine() {

    // }

    private int[] getRow(int i) {
        return board[i];
    }

    public int[][] getBoard() {
        return board;
    }
}