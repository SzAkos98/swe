package game;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static game.View.*;

public class MakePiece {

    static Logger logger = LoggerFactory.getLogger(MakePiece.class);


    public static Group tileGroup = new Group();
    public static Group pieceGroup = new Group();

    public static Tile[][] bd = new Tile[WIDTH][HEIGHT];
    public static SetColor[][] newColorBoard = new SetColor[WIDTH][HEIGHT];

    /**
     * {@code whoPlays}A kör figyeléséhez használt globális változó. Az értéke meghatározza, hogy melyik játékos következik éppen.
     */
    public static String whoPlays = "RED";

    private static int blueWinRow = 0;
    private static int redWinRow = 0;
    private static int blueWinColumn = 0;
    private static int redWinColumn = 0;

    /**
     * A mozgatásért hitelességét ellenőrző függvény.
     *
     * @param piece A mozgatandó "karakter".
     * @param newx  Az elmozgatás végpontjának X koordinátája.
     * @param newy  Az elmozgatás végpontjának Y koordinátája.
     * @return A mozgatás típusát adjauk vissza.
     */
    public static MoveResult tryMove(Piece piece, int newx, int newy) {
        if (piece.getType() == PieceType.BLUE || piece.getType() == PieceType.RED) {

            int x0 = toBoard(piece.getOldx());
            int y0 = toBoard(piece.getOldy());

            if (bd[newx][newy].hasPiece()) {

                return new MoveResult(MoveType.NONE);

            } else if (!bd[newx][newy].hasPiece() && Math.abs(newx - x0) == 1 && Math.abs(newy - y0) == 2 ||
                    !bd[newx][newy].hasPiece() && Math.abs(newx - x0) == 2 && Math.abs(newy - y0) == 1) {

                SetColor setcolor = new SetColor(piece.getType() == PieceType.RED, x0, y0);
                newColorBoard[x0][y0] = setcolor;
                tileGroup.getChildren().add(setcolor);
                if (whoPlays.equals("RED") && piece.getType() == PieceType.RED) {
                    whoPlays = "BLUE";
                    return new MoveResult(MoveType.NORMAL);
                } else if (!whoPlays.equals("RED") && piece.getType() == PieceType.RED) {
                    return new MoveResult(MoveType.NONE);
                } else if (whoPlays.equals("BLUE") && piece.getType() == PieceType.BLUE) {
                    whoPlays = "RED";
                    return new MoveResult(MoveType.NORMAL);
                } else if (!whoPlays.equals("BLUE") && piece.getType() == PieceType.BLUE) {
                    return new MoveResult(MoveType.NONE);
                }
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    /**
     * A valódi X és Y koordináták Tábla koordinátává alakításáért felelős függvény.
     *
     * @param pixel A valódi koordináta nagysága pixelben.
     * @return Vissza adjuk a valódi koordináta Táblabeli megfelelőjét, hogy melyik négyzetnek felel meg a koordináta.
     */
    public static int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    /**
     * A játék állás ellenőrzésért felelős függvény.
     * <p>
     * Megállapítja, hogy nyert-e az egyik játékos és ha igan akkor, hogy melyik az.
     */
    public static void WW() {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB && GameLogic.whoWin.equals("NONE")) {
                    ++blueWinRow;
                    if (blueWinRow == 5) {
                        GameLogic.whoWin = "BLUE";
                        new View().gameEnd(asd);
                        break;
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR && GameLogic.whoWin.equals("NONE")) {
                    ++redWinRow;
                    if (redWinRow == 5) {
                        GameLogic.whoWin = "RED";
                        new View().gameEnd(asd);
                        break;
                    }
                }
            }
            redWinRow = 0;
            blueWinRow = 0;
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB && GameLogic.whoWin == "NONE") {
                    ++blueWinColumn;
                    if (blueWinColumn == 5) {
                        GameLogic.whoWin = "BLUE";
                        new View().gameEnd(asd);
                        break;
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR && GameLogic.whoWin == "NONE") {
                    ++redWinColumn;
                    if (redWinColumn == 5) {
                        GameLogic.whoWin = "RED";
                        new View().gameEnd(asd);
                        break;
                    }
                }
            }
            redWinColumn = 0;
            blueWinColumn = 0;
        }
    }


    /**
     * A "karakter" létrehotásáért felelős függvény. Ebben a függvényben kezeljük le továbbá a mozgást is.
     *
     * @param type A létrehozandó/mozgatandó "karakter" típusa.
     * @param x    A létrehozandó/mozgatandó "karakter" X koordinátája a táblán.
     * @param y    A létrehozandó/mozgatandó "karakter" Y koordinátája a táblán.
     * @return Vissza adjuk a "karaktert".
     */
    public static Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        if (piece.getType() != PieceType.GHOSTR && piece.getType() != PieceType.GHOSTB) {

            piece.setOnMouseReleased(e -> {
                int newx = toBoard(piece.getLayoutX());
                int newy = toBoard(piece.getLayoutY());


                MoveResult result = tryMove(piece, newx, newy);

                int x0 = toBoard(piece.getOldx());
                int y0 = toBoard(piece.getOldy());

                switch (result.getType()) {
                    case NONE:
                        logger.warn("Invalid step!");
                        piece.abortMove();
                        break;
                    case NORMAL:
                        if (!bd[newx][newy].hasPiece()) {
                            logger.info("Step Succeed.");
                            Piece ghost;
                            piece.move(newx, newy);
                            Tile tile = new Tile((x0 + y0) % 2 == 0, x0, y0);
                            ghost = (piece.getType() == PieceType.RED ? makePiece(PieceType.GHOSTR, x0, y0) : makePiece(PieceType.GHOSTB, x0, y0));
                            pieceGroup.getChildren().add(ghost);
                            bd[x0][y0].setPiece(null);
                            bd[x0][y0] = tile;
                            bd[x0][y0].setPiece(ghost);
                            bd[newx][newy].setPiece(piece);
                            break;
                        }
                }
            });
        }
        return piece;
    }
}
