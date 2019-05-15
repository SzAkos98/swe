package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static game.View.TILE_SIZE;

public class Tile extends Rectangle {
    /**
     * A játéktábla vizuális reprezentálását végzi.
     */

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

    /**
     * A játéktáblán megjelenő négyzeteket készíti el a függvény.
     *
     * @param light A mező eredeti színét meghatározó érték.
     * @param x     A mező X koordinátája a táblán.
     * @param y     A mező Y koordinátája a táblán.
     */
    public Tile(boolean light, int x, int y) {
        setWidth(View.TILE_SIZE);
        setHeight(View.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.BLACK : Color.WHITE);
    }
}
