package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Ball;
import model.TargetCircle;

public class GameMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(300);
        stage.setHeight(500);
        Pane gameLayout = new Pane();
        TargetCircle targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 70);
        Ball ball = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, 1);
        Scene scene = new Scene(gameLayout);
        gameLayout.getChildren().addAll(targetCircle, ball);
        stage.setScene(scene);
        stage.show();
    }
}
