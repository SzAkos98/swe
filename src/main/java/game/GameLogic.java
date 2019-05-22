package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static game.View.*;


/**
 * A játék logikáért felelős osztály.
 */
public class GameLogic {
    /**
     * {@code whoWin}A nyertes meghatározására használt globális változó, mejnek alap értéka "NONE".
     */
    static String whoWin = "NONE";


    private Logger logger = LoggerFactory.getLogger(GameLogic.class);

    /**
     * {@code scene3}A játék fő játék ablakának változója.
     */
    static Scene scene3;

    /**
     * A tábla kirajzolásáért felelős függvény.
     *
     * Betölti az elkészített táblát a képernyőre.
     *
     * @param gm A Tábla programablakja.
     */
    public void game(Pane gm) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);

        Pane board = new Pane();
        board.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board.getChildren().addAll(MakePiece.tileGroup, MakePiece.pieceGroup);
        logger.info("Building game board.");

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                MakePiece.bd[x][y] = tile;

                MakePiece.tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y == 0 || y == 9) {
                    if (y == 0 && x == 0) {
                        int xx = (int) (Math.random() * 9 + 0);
                        piece = MakePiece.makePiece(PieceType.RED, xx, y);
                    }

                    if (piece == null) {
                        if (y == 9 && x == 0) {
                            int xx = (int) (Math.random() * 9 + 0);
                            piece = MakePiece.makePiece(PieceType.BLUE, xx, y);
                        }
                    }

                    if (piece != null) {
                        tile.setPiece(piece);
                        MakePiece.pieceGroup.getChildren().add(piece);
                    }
                }
            }
        }
        logger.info("Building game board. DONE");

        gm.getChildren().addAll(board);
        scene3 = new Scene(gm, 720, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);

        scene3.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            MakePiece.WW();
            whoWin = MakePiece.Winner(MakePiece.whowinBoard);
            if (whoWin.equals("RED") || whoWin.equals("BLUE")) new View().gameEnd(asd);
        });
    }




}
