package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene scene1, scene2, scene3, scene4;
    String whoWin = null;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ProjectSWE");
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);

        StackPane layout1 = new StackPane();
        layout1.getChildren().add(bg);
        initMainMenu(layout1);
        window.setResizable(false);
        window.setScene(scene1);
        window.show();
    }

    protected void initMainMenu(Pane menuLayout) {
        //background and font size fixed to 72
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        Font font = Font.font(72);

        //lapel
        AnchorPane menu = new AnchorPane();
        menu.setPrefSize(1280, 100);
        Label mLabel = new Label("Welcome to the ProjectSWE!");
        mLabel.setFont(font);
        mLabel.setLayoutX(110);
        mLabel.setLayoutY(10);
        menu.getChildren().add(mLabel);

        //start button
        Button btnStart = new Button("Start");
        btnStart.setFont(font);
        btnStart.setOnAction(actionEvent -> window.setScene(scene2));

        //exit button
        Button btnExit = new Button("Exit");
        btnExit.setFont(font);
        btnExit.setOnAction(actionEvent -> System.exit(0));

        //display
        VBox btns = new VBox(50, btnStart, btnExit);
        btns.setAlignment(Pos.CENTER);


        menuLayout.getChildren().addAll(menu, btns);
        scene1 = new Scene(menuLayout, 1280, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);
        gameStart(asd);
    }

    protected void gameStart(Pane gameStart) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);

        String nick = "Enter your nickname";
        Font font = Font.font(25);
        AnchorPane nicks = new AnchorPane();
        nicks.setPrefSize(1280, 720);

        //player 1 nickname
        TextField player1 = new TextField();
        player1.setPromptText(nick);
        player1.setFont(font);
        player1.setMaxHeight(60);
        player1.setMaxWidth(300);
        Label plyr1 = new Label("Player 1");
        plyr1.setFont(font);
        VBox p1 = new VBox(50, plyr1, player1);
        p1.setAlignment(Pos.CENTER);
        p1.setLayoutX(200);
        p1.setLayoutY(150);

        //player 2 nickname
        TextField player2 = new TextField();
        player2.setPromptText(nick);
        player2.setFont(font);
        player2.setMaxHeight(60);
        player2.setMaxWidth(300);
        Label plyr2 = new Label("Player 2");
        plyr2.setFont(font);
        VBox p2 = new VBox(50, plyr2, player2);
        p2.setAlignment(Pos.CENTER);
        p2.setLayoutX(780);
        p2.setLayoutY(150);

        //game starter button
        Button btnStartGame = new Button("Start Game");
        btnStartGame.setFont(Font.font(30));
        btnStartGame.setAlignment(Pos.CENTER);
        btnStartGame.setOnAction(actionEvent -> window.setScene(scene3));

        //back to menu button
        Button btnBack = new Button("Back");
        btnBack.setFont(Font.font(25));
        btnBack.setAlignment(Pos.CENTER);
        btnBack.setOnAction(actionEvent -> window.setScene(scene1));

        VBox btns = new VBox(30, btnStartGame, btnBack);
        btns.setAlignment(Pos.BOTTOM_CENTER);

        nicks.getChildren().addAll(p1, p2);

        gameStart.getChildren().addAll(nicks, btns);
        scene2 = new Scene(gameStart, 1280, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);
        game(asd);

    }

    public static final int TILE_SIZE = 72;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[][] bd = new Tile[WIDTH][HEIGHT];
    private SetColor[][] newColorBoard = new SetColor[WIDTH][HEIGHT];

    protected void game(Pane gm) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);

        Pane board = new Pane();
        board.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board.getChildren().addAll(tileGroup, pieceGroup);

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
                        xx = 0;
                    }

                    if (piece == null) {
                        if (y == 9 && x == 0) {
                            int xx = (int) (Math.random() * 9 + 0);
                            piece = makePiece(PieceType.BLUE, xx, y);
                            xx = 0;
                        }
                    }

                    if (piece != null) {
                        tile.setPiece(piece);
                        pieceGroup.getChildren().add(piece);
                    }
                }
            }
        }

        gm.getChildren().addAll(board);
        scene3 = new Scene(gm, 1280, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);

        int blueWinRow = 0;
        int redWinRow = 0;
        int blueWinColumn = 0;
        int redWinColumn = 0;

        // do {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB) {
                    blueWinRow++;
                    if (blueWinRow == 5) {
                        whoWin = "BLUE";
                        gameEnd(asd);
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR) {
                    redWinRow++;
                    if (blueWinRow == 5) {
                        whoWin = "RED";
                        gameEnd(asd);
                    }
                }
                if (x == 9 && blueWinRow < 5 && redWinRow < 5) {
                    redWinRow = 0;
                    blueWinRow = 0;
                }

            }
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTB) {
                    blueWinColumn++;
                    if (blueWinColumn == 5) {
                        whoWin = "BLUE";
                        gameEnd(asd);
                    }
                }

                if (bd[x][y].hasPiece() && bd[x][y].getPiece().getType() == PieceType.GHOSTR) {
                    redWinColumn++;
                    if (blueWinColumn == 5) {
                        whoWin = "RED";
                        gameEnd(asd);
                    }
                }
                if (x == 9 && blueWinColumn < 5 && redWinColumn < 5) {
                    redWinColumn = 0;
                    blueWinColumn = 0;
                }

            }
        }
        //} while(whoWin == null);
    }

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
                return new MoveResult(MoveType.NORMAL);
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

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
                        piece.abortMove();
                        break;
                    case NORMAL:
                        if (!bd[newx][newy].hasPiece()) {
                            Piece ghost;
                            piece.move(newx, newy);
                            Tile tile = new Tile((x0 + y0) % 2 == 0, x0, y0);
                            ghost = (piece.getType() == PieceType.RED ? makePiece(PieceType.GHOSTR, x0, y0) : makePiece(PieceType.GHOSTB, x0, y0));
                            tile.setPiece(ghost);
                            pieceGroup.getChildren().add(ghost);
                            bd[x0][y0].setPiece(null);
                            bd[x0][y0] = tile;
                            bd[newx][newy].setPiece(piece);
                            break;
                        }
                }
            });
        }

        return piece;
    }

    protected void gameEnd(Pane gmEnd) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        Font font = Font.font(72);

        AnchorPane menu = new AnchorPane();
        menu.setPrefSize(1280, 100);

        if (whoWin.equals("RED")) {
            //Congrats Player 1
            System.out.println("Red won");
            Label label = new Label("Red Player Won!");
            label.setFont(font);
            label.setTextFill(Color.RED);
            label.setLayoutX(110);
            label.setLayoutY(10);
            menu.getChildren().add(label);
        } else if (whoWin.equals("BLUE")) {
            //Congrats Player2
            System.out.println("Blue won");
            Label label = new Label("Blue Player Won!");
            label.setFont(font);
            label.setTextFill(Color.BLUE);
            label.setLayoutX(110);
            label.setLayoutY(10);
            menu.getChildren().add(label);
        }

        //start button
        Button btnStart = new Button("New Game");
        btnStart.setFont(font);
        btnStart.setOnAction(actionEvent -> window.setScene(scene2));

        //exit button
        Button btnExit = new Button("Exit");
        btnExit.setFont(font);
        btnExit.setOnAction(actionEvent -> System.exit(0));

        //display
        VBox btns = new VBox(50, btnStart, btnExit);
        btns.setAlignment(Pos.CENTER);



        /*
        A ki nyert részt a pieceGroup hasPeace/getPeace szarjával valahogy,
         de csak a Ghost okat és valahogy színt is GHOSTR GHOSTB jó lenne try it

         */
        whoWin = null;

        gmEnd.getChildren().addAll(menu, btns);
        scene4 = new Scene(gmEnd, 1280, 720);
    }
}
