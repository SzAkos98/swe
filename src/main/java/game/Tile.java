package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static game.GameMain.TILE_SIZE;

public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(GameMain.TILE_SIZE);
        setHeight(GameMain.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.BLACK : Color.WHITE);
    }
}
