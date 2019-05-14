package game;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.geometry.Pos;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.guice.PersistenceModule;

public class View extends Application {

    public static Stage window;
    public static Scene scene1, scene2, scene4;
    public static final int TILE_SIZE = 72;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    Logger logger = LoggerFactory.getLogger(View.class);

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
        MainMenu(layout1);
        window.setResizable(false);
        window.setScene(scene1);
        window.show();
    }

    protected void MainMenu(Pane menuLayout) {

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
        btnStart.setOnAction(actionEvent -> {
            window.setScene(scene2);
            logger.info("Clicked Start button. Going to the Start Game scene.");
        });

        //exit button
        Button btnExit = new Button("Exit");
        btnExit.setFont(font);
        btnExit.setOnAction(actionEvent -> {
            System.exit(0);
            logger.info("Clicked Exit button, exiting.");
        });

        //display
        VBox btns = new VBox(50, btnStart, btnExit);
        btns.setAlignment(Pos.CENTER);


        menuLayout.getChildren().addAll(menu, btns);
        scene1 = new Scene(menuLayout, 1280, 720);
        AnchorPane asd = new AnchorPane();
        asd.getChildren().add(bg);
        gameStart(asd);
    }

    protected void gameStart(AnchorPane gameStart) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);

        String nick = "Enter your nickname";
        Font font = Font.font(25);

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
        btnStartGame.setOnAction(actionEvent -> {
            window.setScene(GameLogic.scene3);
            logger.info("Clicked Start Game button. Game starts.");
            Injector injector = Guice.createInjector(new PersistenceModule("jpa-persistence-unit-1"));
            TopTenDao gameDao = injector.getInstance(TopTenDao.class);
            TopTen topten1 = TopTen.builder()
                    .name(player1.getText())
                    .winCount(0)
                    .build();
            gameDao.persist(topten1);
/*
            TopTen topten2 = TopTen.builder()
                    .name(player2.getText())
                    .winCount(0)
                    .build();
            gameDao.persist(topten2);*/
        });

        //back to menu button
        Button btnBack = new Button("Back");
        btnBack.setFont(Font.font(25));
        btnBack.setAlignment(Pos.CENTER);
        btnBack.setOnAction(actionEvent -> {
            window.setScene(scene1);
            logger.info("Clicked Back button. Going back to the Menu scene.");
        });

        VBox btns = new VBox(30, btnStartGame, btnBack);
        btns.setLayoutX(500);
        btns.setLayoutY(420);


        gameStart.getChildren().addAll(p1, p2, btns);
        scene2 = new Scene(gameStart, 1280, 720);
        StackPane asd = new StackPane();
        asd.getChildren().add(bg);
        new GameLogic().game(asd);


    }

    public void gameEnd(Pane gmEnd) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        Font font = Font.font(72);

        AnchorPane nmenu = new AnchorPane();
        nmenu.setPrefSize(1280, 100);

        if (GameLogic.whoWin.equals("RED")) {
            //Congrats Player 1
            logger.info("Red won");
            Label label = new Label("Red Player Won!");
            label.setFont(font);
            label.setTextFill(Color.RED);
            label.setLayoutX(310);
            label.setLayoutY(10);
            nmenu.getChildren().add(label);
        } else if (GameLogic.whoWin.equals("BLUE")) {
            //Congrats Player2
            logger.info("Blue won");
            Label label = new Label("Blue Player Won!");
            label.setFont(font);
            label.setTextFill(Color.BLUE);
            label.setLayoutX(310);
            label.setLayoutY(10);
            nmenu.getChildren().add(label);
        }

        AnchorPane asd = new AnchorPane();
        asd.getChildren().add(bg);

        //start button
        Button btnNewGame = new Button("New Game");
        btnNewGame.setFont(font);
        btnNewGame.setOnAction(actionEvent -> {
            new View().gameStart(asd);
            window.setScene(scene2);
        });
        //exit button
        Button btnExit = new Button("Exit");
        btnExit.setFont(font);
        btnExit.setOnAction(actionEvent -> System.exit(0));

        //display
        VBox nbtns = new VBox(50, btnNewGame, btnExit);
        nbtns.setAlignment(Pos.CENTER);

        GameLogic.whoWin = "NONE";

        gmEnd.getChildren().addAll(nmenu, nbtns);
        scene4 = new Scene(gmEnd, 1280, 720);
        window.setScene(scene4);
    }
}
