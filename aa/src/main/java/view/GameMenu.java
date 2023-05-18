package view;

import controller.GeneralGameController;
import controller.SinglePlayerFXController;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Ball;
import model.BallAnimation;
import model.GameTransitions;
import model.SinglePlayerGame;

import java.util.LinkedList;

public class GameMenu extends Application {
    private Stage gameStage;
    private Pane gameLayout;
    private int level = 1;
    private int wind = 0;
    private int score = 0;
    private int ballsAmount = 10;
    private String username = "ali";
    SinglePlayerGame game;
    private static SinglePlayerFXController gameController;

    private GeneralGameController generalGameController = new GeneralGameController(this);

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
        gameLayout = FXMLLoader.load(GameMenu.class.getResource("/fxml/singlePlayerGame.fxml"));
        gameController.setIceProgress(0);
        gameController.setUsername(username);
        gameController.setBallsAmount(ballsAmount);
        gameController.setWind(wind);
        gameController.setLevel(Integer.toString(level));
        gameController.setScore(score);
        game = new SinglePlayerGame(null, 10, stage, gameLayout);
        for (Ball ball : game.getPlayer().getBalls()) {
            ball.setBallAnimation(new BallAnimation(ball, game.getTargetCircle(), generalGameController));
        }
        Scene scene = new Scene(gameLayout);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                    generalGameController.shoot(gameLayout, game.getPlayer());
                }
            }

        });
        stage.setScene(scene);
        stage.show();
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public int getLevel() {
        return level;
    }

    public int getWind() {
        return wind;
    }

    public int getScore() {
        return score;
    }

    public int getBallsAmount() {
        return ballsAmount;
    }

    public String getUsername() {
        return username;
    }

    public SinglePlayerGame getGame() {
        return game;
    }

    public GeneralGameController getGeneralGameController() {
        return generalGameController;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public void lose() {
        for (Transition transition : GameTransitions.getTransitions()) {
            transition.stop();
        }
        gameLayout.setStyle("-fx-background-color: 'red';");
        Label label = new Label("You lost!");
        label.setStyle("-fx-background-color: 'white';");
        gameLayout.getChildren().add(label);
        //todo
    }
}
