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

    public SinglePlayerGameController getGeneralGameController() {
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
        gameController.setWind(0);
        gameController.setLevel("level " + Game.getLevel().getNumber());
        gameController.setScore(0);
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
                } else if (keyEvent.getCode().equals(Game.getGameKeys().get("freeze"))) {
                    if (gameController.getIceProgress() > 0.95) {
                        generalGameController.freeze();
                    }
                } else if (keyEvent.getCode().equals(Game.getGameKeys().get("pause"))) {
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
        GameTransitions.stopTransitions();
        for (Ball ball : game.getTargetCircle().getBalls()) {
            ball.setVisible(true);
            ball.getLine().setVisible(true);
        }
        gameLayout.getStyleClass().remove("game");
        gameLayout.getStyleClass().add("lose");
        Label label = new Label("You lost!");
        gameLayout.getChildren().add(label);
        generalGameController.lose(Game.getLevel(), user);
    }

    public void win() {
        gameLayout.getStyleClass().remove("game");
        gameLayout.getStyleClass().add("win");
        GameTransitions.stopTransitions();
        generalGameController.win(Game.getLevel(), user);
        gameLayout.setStyle("-fx-background-color: 'green';");
    }
}
