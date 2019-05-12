package game;

public enum PieceType {
    RED(1), BLUE(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}