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

    Logger logger = LoggerFactory.getLogger(GameLogic.class);

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[][] bd = new Tile[WIDTH][HEIGHT];
    private SetColor[][] newColorBoard = new SetColor[WIDTH][HEIGHT];

    /**
     * {@code scene3}A játék fő játék ablakának változója.
     */
    public static Scene scene3;
    /**
     * {@code whoWin}A nyertes meghatározására használt globális változó, mejnek alap értéka "NONE".
     */
    public static String whoWin = "NONE";
    /**
     * {@code whoPlays}A kör figyeléséhez használt globális változó. Az értéke meghatározza, hogy melyik játékos következik éppen.
     */
    public static String whoPlays = "RED";

    private int blueWinRow = 0;
    private int redWinRow = 0;
    private int blueWinColumn = 0;
    private int redWinColumn = 0;

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
        board.getChildren().addAll(tileGroup, pieceGroup);
        logger.info("Building game board.");

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                bd[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y == 0 || y == 9) {
                    if (y == 0 && x == 0) {
                        int xx = (int) (Math.random() * 9 + 0);
                        piece = makePiece(PieceType.RED, xx, y);
                    }

                    if (piece == null) {
                        if (y == 9 && x == 0) {
                            int xx = (int) (Math.random() * 9 + 0);
                            piece = makePiece(PieceType.BLUE, xx, y);
                        }
                    }

                    if (piece != null) {
                        tile.setPiece(piece);
                        pieceGroup.getChildren().add(piece);
                    }
                }
            }
        }
        logger.info("Building game board. DONE");
/*
        Label leaderBoard = new Label("Leader Board\n" + View.gameDao .findBest(5) + "\n");
        leaderBoard.setFont(Font.font(30));
        leaderBoard.setAlignment(Pos.CENTER_RIGHT);/*
        leaderBoard.setLayoutX(730);
        leaderBoard.setLayoutY(110);*/

        gm.getChildren().addAll(board);
        scene3 = new Scene(gm, 720, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);

        scene3.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            WW();
        });
    }

    /**
     * A mozgatásért hitelességét ellenőrző függvény.
     *
     * @param piece A mozgatandó "karakter".
     * @param newx Az elmozgatás végpontjának X koordinátája.
     * @param newy Az elmozgatás végpontjának Y koordinátája.
     * @return A mozgatás típusát adjauk vissza.
     */
    private MoveResult tryMove(Piece piece, int newx, int newy) {
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
     * @param pixel A valódi koordináta nagysága pixelben.
     * @return Vissza adjuk a valódi koordináta Táblabeli megfelelőjét, hogy melyik négyzetnek felel meg a koordináta.
     */
    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    /**
     * A játék állás ellenőrzésért felelős függvény.
     *
     * Megállapítja, hogy nyert-e az egyik játékos és ha igan akkor, hogy melyik az.
     */
    private void WW() {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB && whoWin.equals("NONE")) {
                    ++blueWinRow;
                    if (blueWinRow == 5) {
                        whoWin = "BLUE";
                        new View().gameEnd(asd);
                        break;
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR && whoWin.equals("NONE")) {
                    ++redWinRow;
                    if (redWinRow == 5) {
                        whoWin = "RED";
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
                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB && whoWin == "NONE") {
                    ++blueWinColumn;
                    if (blueWinColumn == 5) {
                        whoWin = "BLUE";
                        new View().gameEnd(asd);
                        break;
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR && whoWin == "NONE") {
                    ++redWinColumn;
                    if (redWinColumn == 5) {
                        whoWin = "RED";
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
     * @param type A létrehozandó/mozgatandó "karakter" típusa.
     * @param x A létrehozandó/mozgatandó "karakter" X koordinátája a táblán.
     * @param y A létrehozandó/mozgatandó "karakter" Y koordinátája a táblán.
     * @return Vissza adjuk a "karaktert".
     */
    private Piece makePiece(PieceType type, int x, int y) {
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
