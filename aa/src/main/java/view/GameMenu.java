package view;

import controller.GeneralGameController;
import controller.PauseMenuController;
import controller.SinglePlayerFXController;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.LinkedList;

public class GameMenu extends Application {
    private Stage gameStage;
    private User user;
    private Pane pauseLayout = FXMLLoader.load(GameMenu.class.getResource("/fxml/pause.fxml"));
    private Pane gameLayout = FXMLLoader.load(GameMenu.class.getResource("/fxml/singlePlayerGame.fxml"));;
    private Pane wholeLayout = new Pane();
    private Scene scene;
    private int level = 1;
    private int wind = 0;
    private int score = 0;
    private int ballsAmount = 10;
    private String username = "ali";
    SinglePlayerGame game;
    private boolean paused = false;
    private boolean movable = false;
    private static SinglePlayerFXController gameController;

    public GameMenu(User user) throws IOException {
        this.user = user;
    }

    private GeneralGameController generalGameController = new GeneralGameController(this);

    public static SinglePlayerFXController getGameController() {
        return gameController;
    }

    public static void setGameController(SinglePlayerFXController gameController) {
        GameMenu.gameController = gameController;
    }

    public Pane getGameLayout() {
        return gameLayout;
    }

    public Pane getPauseLayout() {
        return pauseLayout;
    }

    public User getUser() {
        return user;
    }

    public Pane getWholeLayout() {
        return wholeLayout;
    }

    public Scene getScene() {
        return scene;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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

    public boolean isPaused() {
        return paused;
    }

    public GeneralGameController getGeneralGameController() {
        return generalGameController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameStage = stage;
        wholeLayout.getChildren().add(gameLayout);
        //initialize game
        stage.setWidth(450);
        stage.setHeight(700);
        generalGameController.addTimer();
        PauseMenuController.setGameMenu(this);
        gameController.setIceProgress(0);
        gameController.setUsername(user.getUsername());
        gameController.setBallsAmount(Game.getInitialBallsAmount());
        gameController.setWind(wind);
        gameController.setLevel("level " + Game.getLevel().getNumber());
        gameController.setScore(score);
        game = new SinglePlayerGame(null, Game.getInitialBallsAmount(), stage, gameLayout, Game.getLevel());
        for (Ball ball : game.getPlayer().getBalls()) {
            ball.setBallAnimation(new BallAnimation(ball, game.getTargetCircle(), generalGameController));
        }
        scene = new Scene(wholeLayout);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(Game.getGameKeys().get("shoot"))) {
                    generalGameController.shoot(gameLayout, game.getPlayer());
                }
                else if (keyEvent.getCode().equals(Game.getGameKeys().get("freeze"))) {
                    if (gameController.getIceProgress() > 0.95) {
                        generalGameController.freeze();
                    }
                }
                else if (keyEvent.getCode().equals(Game.getGameKeys().get("pause"))) {
                    if (paused) {
                        generalGameController.startTimeLines();
                        gameLayout.getChildren().remove(pauseLayout);
                        game.getTargetCircle().getAnimation().play();
                        gameLayout.setOpacity(1);
                        paused = false;
                    } else {
                        generalGameController.stopTimeLines();
                        GameTransitions.stopTransitions();
                        gameLayout.getChildren().add(pauseLayout);
                        pauseLayout.setLayoutX(25);
                        pauseLayout.setLayoutY((pauseLayout.getHeight()) / 2);
                        pauseLayout.setFocusTraversable(false);
                        paused = true;
                    }
                }

                else if (keyEvent.getCode().equals(KeyCode.LEFT) && movable) {
                    generalGameController.moveLeft();
                }

                else if (keyEvent.getCode().equals(KeyCode.RIGHT) && movable) {
                    generalGameController.moveRight();
                }
            }

        });
        stage.setScene(scene);
        stage.show();
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public SinglePlayerGame getGame() {
        return game;
    }

    public void lose() {
        GameTransitions.stopTransitions();
        gameLayout.setStyle("-fx-background-color: 'red';");
        Label label = new Label("You lost!");
        gameLayout.getChildren().add(label);
        generalGameController.lose(user, Game.getLevel());
    }

    public void win() {
        GameTransitions.stopTransitions();
        generalGameController.win(user, Game.getLevel());
        gameLayout.setStyle("-fx-background-color: 'green';");
    }
}
