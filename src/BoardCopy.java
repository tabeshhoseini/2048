public class BoardCopy {
    public int[][] board;
    public int point;
    public int mergeNumber;
    public int moveNumber;

    public BoardCopy(int[][] board, int point, int mergeNumber, int moveNumber) {
        this.board = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.board[i][j] = board[i][j];
            }
        }

        this.point = point;
        this.mergeNumber = mergeNumber;
        this.moveNumber = moveNumber;
    }
}
