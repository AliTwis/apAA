package view;

import controller.SinglePlayerFXController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Ball;
import model.SinglePlayerGame;

import java.util.LinkedList;

public class GameMenu extends Application {
    private Stage gameStage;
    private int level = 1;
    private int wind = 0;
    private int score = 0;
    private int ballsAmount = 10;
    private String username = "ali";
    SinglePlayerGame game;
    private static SinglePlayerFXController gameController;

    public static SinglePlayerFXController getGameController() {
        return gameController;
    }

    public static void setGameController(SinglePlayerFXController gameController) {
        GameMenu.gameController = gameController;
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        gameStage = stage;
        //initialize game
        stage.setWidth(300);
        stage.setHeight(700);
        Pane gameLayout = FXMLLoader.load(GameMenu.class.getResource("/fxml/singlePlayerGame.fxml"));
        gameController.setIceProgress(0);
        gameController.setUsername(username);
        gameController.setBallsAmount(ballsAmount);
        gameController.setWind(wind);
        gameController.setLevel(Integer.toString(level));
        gameController.setScore(score);
        game = new SinglePlayerGame(null, 10, stage, gameLayout);
        Scene scene = new Scene(gameLayout);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                    String output = game.shoot(gameLayout);
                }
            }

        });
        stage.setScene(scene);
        stage.show();
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    //    public static void lose() {
//        System.out.println("loser");
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText("You lost stupid!");
//        alert.showAndWait();
//        gameStage.close();
//    }
}
