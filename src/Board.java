public class Board {
    private int id;
    private int boardLength;
    private int[][] board;

    public Board(int id, int boardLength) {
        this.id = id;
        this.boardLength = boardLength;
        this.board = new int[boardLength][boardLength];
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                this.board[i][j] = 0;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getBoardLength() {
        return boardLength;
    }
}
