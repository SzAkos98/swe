
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku the Game");


        StackPane root = new StackPane();
        initMainMenu(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    protected void initMainMenu(Pane mainMenuRoot) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setStroke(Color.DARKCYAN);
        bg.setFill(Color.ANTIQUEWHITE);
        Font font = Font.font(72);
        Button btnStart = new Button("Start");
        btnStart.setFont(font);
        btnStart.setAlignment(Pos.CENTER);
        //btnStart.setOnAction(  );

        Button btnExit = new Button("Exit");
        btnExit.setFont(font);
        btnExit.setAlignment(Pos.CENTER);
        btnExit.setOnAction(actionEvent -> System.exit(0));
        VBox vBox = new VBox(50, btnStart, btnExit);

        vBox.setAlignment(Pos.CENTER);
        mainMenuRoot.getChildren().addAll(bg, vBox);
    }
}


