package game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static game.View.TILE_SIZE;

public class Piece extends StackPane {

    private PieceType type;
    private double mousex, mousey;
    private double oldx, oldy;

    public PieceType getType() {
        return type;
    }

    public double getOldx() {
        return oldx;
    }

    public double getOldy() {
        return oldy;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.GRAY);
        bg.setStrokeWidth(TILE_SIZE * 0.03);
        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);

        if (type == PieceType.RED) {
            ellipse.setFill(Color.RED);
            ellipse.setStroke(Color.GRAY);
            ellipse.setStrokeWidth(TILE_SIZE * 0.03);

            ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
            ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        } else if (type == PieceType.BLUE) {
            ellipse.setFill(Color.BLUE);
            ellipse.setStroke(Color.GRAY);
            ellipse.setStrokeWidth(TILE_SIZE * 0.03);

            ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
            ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        } else if (type == PieceType.GHOSTR) {
            bg.setFill(Color.TRANSPARENT);
            ellipse.setFill(Color.TRANSPARENT);
        } else if (type == PieceType.GHOSTB) {
            bg.setFill(Color.TRANSPARENT);
            ellipse.setFill(Color.TRANSPARENT);
        }

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mousex = e.getSceneX();
            mousey = e.getSceneY();
        });
        if (type == PieceType.BLUE || type == PieceType.RED) {
            setOnMouseDragged(e -> {
                relocate(e.getSceneX() - mousex + oldx, e.getSceneY() - mousey + oldy);
            });
        }
    }

    public void move(int x, int y) {
        oldx = x * TILE_SIZE;
        oldy = y * TILE_SIZE;
        relocate(oldx, oldy);
    }

    public void abortMove() {
        relocate(oldx, oldy);
    }
}
