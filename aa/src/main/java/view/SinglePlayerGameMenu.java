package view;

import controller.PauseMenuController;
import controller.SinglePlayerFXController;
import controller.SinglePlayerGameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class SinglePlayerGameMenu extends Application implements GameMenusFunctions {
    private Stage gameStage;
    private final User user;
    private final Pane pauseLayout = FXMLLoader.load(SinglePlayerGameMenu.class.getResource("/fxml/pause.fxml"));
    private final Pane gameLayout = FXMLLoader.load(SinglePlayerGameMenu.class.getResource("/fxml/singlePlayerGame.fxml"));
    private final Pane wholeLayout = new Pane();
    private Scene scene;
    SinglePlayerGame game;
    private boolean paused = false;
    private boolean movable = false;
    private boolean savedGame = false;
    private boolean isFinished = false;
    private int windAngle = 0;
    private static SinglePlayerFXController gameController;

    public SinglePlayerGameMenu(User user) throws IOException {
        this.user = user;
    }

    private final SinglePlayerGameController generalGameController = new SinglePlayerGameController(this);

    public static SinglePlayerFXController getGameController() {
        return gameController;
    }

    public static void setGameController(SinglePlayerFXController gameController) {
        SinglePlayerGameMenu.gameController = gameController;
    }

    public boolean isSavedGame() {
        return savedGame;
    }

    public void setSavedGame(boolean savedGame) {
        this.savedGame = savedGame;
    }



    public int getWindAngle() {
        return windAngle;
    }

    public void setWindAngle(int windAngle) {
        this.windAngle = windAngle;
        gameController.setWind(windAngle);
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

    public Scene getScene() {
        return scene;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isMovable() {
        return movable;
    }

    public SinglePlayerGameController getGeneralGameController() {
        return generalGameController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        SinglePlayerGameMenu gameMenu = this;
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
        gameController.setWind(0);
        gameController.setLevel("level " + Game.getLevel().getNumber());
        gameController.setScore(0);
        if (!isSavedGame()) game = new SinglePlayerGame(null, Game.getInitialBallsAmount(), stage, gameLayout, Game.getLevel());
        else {
            game = GameSaver.loadSavedGame(this);
        }
        for (Ball ball : game.getPlayer().getBalls()) {
            ball.setBallAnimation(new BallAnimation(ball, game.getTargetCircle(), generalGameController));
        }
        scene = new Scene(wholeLayout);
        gameLayout.requestFocus();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isFinished) return;
                if (keyEvent.getCode().equals(Game.getGameKeys().get("shoot"))) {
                    generalGameController.shoot(gameLayout, game.getPlayer());
                } else if (keyEvent.getCode().equals(Game.getGameKeys().get("freeze"))) {
                    if (gameController.getIceProgress() > 0.95) {
                        generalGameController.freeze();
                    }
                } else if (keyEvent.getCode().equals(Game.getGameKeys().get("pause"))) {
                    if (!paused) {
                        generalGameController.stopTimeLines();
                        GameTransitions.stopTransitions();
                        gameLayout.getChildren().add(pauseLayout);
                        pauseLayout.setLayoutX(25);
                        pauseLayout.setLayoutY((pauseLayout.getHeight()) / 2);
                        pauseLayout.setFocusTraversable(false);
                        gameLayout.setOpacity(0.9);
                        paused = true;
                    } else {
                        generalGameController.startTimeLines();
                        gameLayout.getChildren().remove(pauseLayout);
                        game.getTargetCircle().getAnimation().play();
                        gameLayout.setOpacity(1);
                        paused = false;
                    }
                } else if (keyEvent.getCode().equals(KeyCode.LEFT) && movable) {
                    generalGameController.moveLeftPlayer();
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT) && movable) {
                    generalGameController.moveRightPlayer();
                }
            }

        });
        stage.setScene(scene);
        stage.show();
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
        isFinished = true;
//        gameLayout.setDisable(true);
        GameTransitions.stopTransitions();
        for (Ball ball : game.getTargetCircle().getBalls()) {
            ball.setVisible(true);
            ball.getLine().setVisible(true);
        }
        gameLayout.getStyleClass().remove("game");
        gameLayout.getStyleClass().add("lose");
        generalGameController.lose(Game.getLevel(), user);
    }

    public void win() {
        isFinished = true;
        gameLayout.getStyleClass().remove("game");
        gameLayout.getStyleClass().add("win");
        GameTransitions.stopTransitions();
        generalGameController.win(Game.getLevel(), user);
        gameLayout.setStyle("-fx-background-color: 'green';");
    }
}
