package view;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class TwoPlayerGameMenu extends Application implements GameMenusFunctions{
    private Stage gameStage;
    private User user, user1;
    private Pane pauseLayout = FXMLLoader.load(SinglePlayerGameMenu.class.getResource("/fxml/pause.fxml"));
    private Pane gameLayout = FXMLLoader.load(SinglePlayerGameMenu.class.getResource("/fxml/twoPlayerGame.fxml"));;
    private Pane wholeLayout = new Pane();
    private Scene scene;
    TwoPlayerGame game;
    private boolean paused = false;
    private boolean movable = false;
    private static TwoPlayerFXController gameController;

    public TwoPlayerGameMenu(User user, User user1) throws IOException {
        this.user = user;
        this.user1 = user1;
    }

    private TwoPlayerGameController generalGameController = new TwoPlayerGameController();

    public static SinglePlayerFXController getGameController() {
        return gameController;
    }

//    public static void setGameController(SinglePlayerFXController gameController) {
//        SinglePlayerGameMenu.gameController = gameController;
//    }

    public TwoPlayerGame getGame() {
        return game;
    }

    public Pane getGameLayout() {
        return gameLayout;
    }

    public Stage getGameStage() {
        return gameStage;}
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

    public TwoPlayerGameController getGeneralGameController() {
        return generalGameController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameStage = stage;
        wholeLayout.getChildren().add(gameLayout);
        //initialize game
        stage.setWidth(450);
        stage.setHeight(700);
//        generalGameController.addTimer();
//        PauseMenuController.setGameMenu(this);
        gameController.setIceProgress(0);
        gameController.setUsernames(user.getUsername(), user1.getUsername());
        gameController.setBallsAmount(Game.getInitialBallsAmount());
        gameController.setWind(0);
        gameController.setLevel("level " + Game.getLevel().getNumber());
        gameController.setScore(0);
        game = new TwoPlayerGame(null, null, Game.getInitialBallsAmount(), stage, gameLayout, Game.getLevel());
        for (Ball ball : game.getPlayer().getBalls()) {
//            ball.setBallAnimation(new BallAnimation(ball, game.getTargetCircle(), generalGameController));
        }
        scene = new Scene(wholeLayout);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(Game.getGameKeys().get("shoot"))) {
//                    generalGameController.shoot(gameLayout, game.getPlayer());
                }
                else if (keyEvent.getCode().equals(Game.getGameKeys().get("freeze"))) {
                    if (gameController.getIceProgress() > 0.95) {
//                        generalGameController.freeze();
                    }
                }
                else if (keyEvent.getCode().equals(Game.getGameKeys().get("pause"))) {
                    if (paused) {
//                        generalGameController.startTimeLines();
                        gameLayout.getChildren().remove(pauseLayout);
                        game.getTargetCircle().getAnimation().play();
                        gameLayout.setOpacity(1);
                        paused = false;
                    } else {
//                        generalGameController.stopTimeLines();
                        GameTransitions.stopTransitions();
                        gameLayout.getChildren().add(pauseLayout);
                        pauseLayout.setLayoutX(25);
                        pauseLayout.setLayoutY((pauseLayout.getHeight()) / 2);
                        pauseLayout.setFocusTraversable(false);
                        paused = true;
                    }
                }

                else if (keyEvent.getCode().equals(KeyCode.LEFT) && movable) {
//                    generalGameController.moveLeft();
                }

                else if (keyEvent.getCode().equals(KeyCode.RIGHT) && movable) {
//                    generalGameController.moveRight();
                }
            }

        });
        stage.setScene(scene);
        stage.show();
    }

    public void lose() {
        //todo
    }

    public void win() {
        //todo
    }
}
