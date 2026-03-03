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
        this.point = 0;
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
            int[] newLine = mergeLine(board[i]);
            if (!arrayEquals(newLine, board[i])) {
                isMoved = true;
                board[i] = newLine;
            }
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

    private int[] getRow(int i) {
        return board[i];
    }

    public int[][] getBoard() {
        return board;
    }
}