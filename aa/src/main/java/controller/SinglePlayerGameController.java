package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.*;
import view.*;

import java.util.LinkedList;
import java.util.Random;

public class SinglePlayerGameController extends GeneralGameController {
    SinglePlayerGameMenu gameMenu;

    public SinglePlayerGameController(SinglePlayerGameMenu gameMenu) {
        this.gameMenu = gameMenu;
        GeneralGameController.gameMenu = gameMenu;
    }

    public void checkPhase(int current) {
        int initial = gameMenu.getGame().getInitialBallsAmount();
        if (current == initial / 4) {
            changeDirectionPhase2();
            changeBallsSizePhase2();
        }
        else if (current == initial / 2) {
            changeVisibilityPhase3();
        }
        else if (current == (initial * 3) / 4) {
            gameMenu.setMovable(true);
            windActive = true;
        }
    }

    public void addBallToCenter(Ball currentBall) {
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        LinkedList<Ball> balls = targetCircle.getBalls();
        boolean collision = false;
        for (Ball ball : balls) {
            if (currentBall.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                collision = true;
                gameMenu.lose();
            }
        }

        if (!collision) {
            targetCircle.addBall(currentBall);
            SinglePlayerGameMenu.getGameController().increaseScore();
            SinglePlayerGameMenu.getGameController().increaseIceProgress();
            int currentBallsAmount = gameMenu.getGame().getCurrentBall();
            if (currentBallsAmount < Game.initialBallsAmount / 2) SinglePlayerGameMenu.getGameController().decreaseBall(Color.RED);
            else if (Game.initialBallsAmount - currentBallsAmount <= 2) SinglePlayerGameMenu.getGameController().decreaseBall(Color.GREEN);
            else SinglePlayerGameMenu.getGameController().decreaseBall(Color.BLUE);
        }

    }

    public void endingTransition() {
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        TranslateTransition transition = new TranslateTransition(Duration.millis(2000), targetCircle);
        transition.setByY(targetCircle.getCenterY() - 10);
        transition.setCycleCount(10);
    }

    public void shoot(Pane gameLayout, Player player) {
        super.shoot(gameLayout, player);
        gameMenu.getGame().increaseCurrentBall();
        checkPhase(gameMenu.getGame().getCurrentBall());
    }

    public void moveLeftPlayer() {
        Ball ball = gameMenu.getGame().getPlayer().getBalls().getFirst();
        if (ball.getCenterX() > 20) ball.setCenterX(ball.getCenterX() - 10);
    }

    public void moveRightPlayer() {
        Ball ball = gameMenu.getGame().getPlayer().getBalls().getFirst();
        if (ball.getCenterX() < gameMenu.getGameStage().getWidth() - 20) ball.setCenterX(ball.getCenterX() + 10);
    }

    public void lose(Level level, User... users) {
        User user = users[0];
        int score = gameMenu.getGame().getCurrentBall() * 7;
        user.increaseScore(score);
        User.updateUsers();
        stopTimeLines();
        showFinalResult(false, level);
    }

    public void win(Level level, User... users) {
        User user = users[0];
        user.increaseScore(level.getNumber() * 150 + Game.getInitialBallsAmount() * 10);
        user.updateTime();
        User.updateUsers();
        stopTimeLines();
        showFinalResult(true, level);
    }

    public void showFinalResult(boolean won, Level level) {
        Pane resultPane = new Pane();
//        resultPane.setStyle("-fx-background-color: 'white';");
        resultPane.getStylesheets().add(AvatarMenu.class.getResource("/css/game.css").toExternalForm());
        resultPane.getStyleClass().add("ending");
        resultPane.setLayoutX(75);//450
        resultPane.setLayoutY(250);//700
        resultPane.setPrefSize(300, 200);

        Text result = new Text();
        result.setTextAlignment(TextAlignment.CENTER);
        if (won) result.setText("You have won!");
        else result.setText("You have lost!");
        result.setLayoutX(102);
        result.setLayoutY(37);

        Text pointString = new Text("Points:");
        pointString.setTextAlignment(TextAlignment.CENTER);
        pointString.setLayoutX(70);
        pointString.setLayoutY(76);

        Text pointNum = new Text();
        pointNum.setTextAlignment(TextAlignment.CENTER);
        pointNum.setLayoutX(186);
        pointNum.setLayoutY(76);
        if (won) pointNum.setText(Integer.toString(level.getNumber() * 150 + Game.getInitialBallsAmount() * 10));
        else pointNum.setText(Integer.toString(gameMenu.getGame().getCurrentBall() * 7));

        Text timeString = new Text("Time:");
        timeString.setTextAlignment(TextAlignment.CENTER);
        timeString.setLayoutX(70);
        timeString.setLayoutY(130);

        Text timeNum = new Text();
        timeNum.setTextAlignment(TextAlignment.CENTER);
        timeNum.setLayoutX(186);
        timeNum.setLayoutY(130);
        timeNum.setText((initialMinute * 60 + initialSecond - minute * 60 - second) + " seconds");

        Button backButton = new Button("Back to main menu");
        backButton.setLayoutX(14);
        backButton.setLayoutY(147);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    User user = MainMenu.user;
                    new MainMenu(user).start(LoginMenu.gameStage);
                } catch (Exception e) {
                    System.out.println("We can't go to main menu there was a problem...");
                }
            }
        });

        Button rankingButton = new Button("Ranking");
        rankingButton.setLayoutX(200);
        rankingButton.setLayoutY(147);
        rankingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainMenuFXController.generalShowScoreboard(actionEvent);
            }
        });

        resultPane.getChildren().addAll(result, pointString, pointNum, timeString, timeNum, backButton, rankingButton);
        resultPane.setFocusTraversable(false);
        gameMenu.getGameLayout().getChildren().add(resultPane);
        gameMenu.getGameLayout().setPrefWidth(gameMenu.getGameLayout().getWidth());
    }
}
