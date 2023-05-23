package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.*;
import view.*;

import java.util.LinkedList;

public class SinglePlayerGameController extends GeneralGameController {
    private static Pane resultPane;
    private int phase = 1;
    SinglePlayerGameMenu gameMenu;

    public SinglePlayerGameController(SinglePlayerGameMenu gameMenu) {
        this.gameMenu = gameMenu;
        GeneralGameController.gameMenu = gameMenu;
    }

    public void checkPhase(int current) {
        int initial = Game.getInitialBallsAmount();
        if (current >= initial / 4 && phase == 1){
            phase++;
            changeDirectionPhase2();
            changeBallsSizePhase2();
        } else if (current >= initial / 2 && phase == 2) {
            changeVisibilityPhase3();
            phase++;
        } else if (current >= ((initial / 4) * 3) && phase == 3) {
            phase++;
            changeWindPhase4();
            gameMenu.setMovable(true);
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
            if (currentBallsAmount < Game.initialBallsAmount / 2)
                SinglePlayerGameMenu.getGameController().decreaseBall(Color.RED);
            else if (Game.initialBallsAmount - currentBallsAmount <= 2)
                SinglePlayerGameMenu.getGameController().decreaseBall(Color.GREEN);
            else SinglePlayerGameMenu.getGameController().decreaseBall(Color.BLUE);
            if (gameMenu.getGame().getPlayer().getBalls().size() == 0) gameMenu.win();
        }
        else if (gameMenu.getGame().getPlayer().getBalls().size() == 0) gameMenu.lose();
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
        if (ball.getCenterX() > 20) {
            ball.setCenterX(ball.getCenterX() - 10);
            ball.getText().setX(ball.getCenterX());
        }
    }

    public void moveRightPlayer() {
        Ball ball = gameMenu.getGame().getPlayer().getBalls().getFirst();
        if (ball.getCenterX() < gameMenu.getGameStage().getWidth() - 20) {
            ball.setCenterX(ball.getCenterX() + 10);
            ball.getText().setX(ball.getCenterX());
        }
    }

    public void lose(Level level, User... users) {
        User user = users[0];
        int score = gameMenu.getGame().getCurrentBall() * 7;
        user.increaseScore(score);
        User.updateUsers();
        stopTimeLines();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            gameMenu.getGeneralGameController().timing(e);
        }));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFinalResult(false, level);
            }
        });
    }

    public void win(Level level, User... users) {
        User user = users[0];
        user.increaseScore(level.getNumber() * 150 + Game.getInitialBallsAmount() * 10);
        user.updateTime();
        User.updateUsers();
        stopTimeLines();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            gameMenu.getGeneralGameController().timing(e);
        }));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showFinalResult(true, level);
            }
        });
    }
}
