package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static game.Main.TILE_SIZE;


public class SetColor extends Rectangle {

    private Piece piece;
    private PieceType type;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public SetColor(boolean light, int x, int y) {
        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.RED : Color.BLUE);
    }

  /*  public void setColor(Piece piece) {
        double x = (getPiece().getOldx()) / TILE_SIZE;
        double y = getPiece().getOldy() / TILE_SIZE;
        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);
        type = getPiece().getType();
        setFill(type == PieceType.RED ? Color.RED : Color.BLUE);
        this.piece = piece;
    }*/
}
