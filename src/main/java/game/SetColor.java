package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static game.Main.TILE_SIZE;


public class SetColor extends Rectangle {

    public SetColor(boolean light, int x, int y) {
        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.RED : Color.BLUE);
    }
}
