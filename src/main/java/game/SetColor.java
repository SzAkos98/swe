package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static game.View.TILE_SIZE;


public class SetColor extends Rectangle {
    /**
     * Lépés után a mező átszínezését végzi a függvény.
     *
     * @param light A mező új színét meghatározó érték.
     * @param x     A mező X koordinátája a táblán.
     * @param y     A mező Y koordinátája a táblán.
     */

    public SetColor(boolean light, int x, int y) {
        setWidth(View.TILE_SIZE);
        setHeight(View.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.RED : Color.BLUE);
    }
}
