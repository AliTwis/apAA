package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.Ball;
import model.Player;
import model.TargetCircle;
import view.GameMenu;

import java.util.LinkedList;

public class GeneralGameController {
    GameMenu gameMenu;
    private boolean smallBall = true;

    public GeneralGameController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
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
            GameMenu.getGameController().increaseScore();
            GameMenu.getGameController().decreaseBall();
            GameMenu.getGameController().increaseIceProgress();
        }

    }

    public void checkPhase(int current) {
        int initial = gameMenu.getGame().getInitialBallsAmount();
        System.out.println(current);
        if (current == initial / 4) {
            changeDirectionPhase1();
            changeBallsSizePhase1();
        }
        else if (current == initial / 2) {

        }
        else if (current == (initial * 3) / 4) {

        }
    }

    public void shoot(Pane gameLayout, Player player) {
        gameMenu.getGame().increaseCurrentBall();
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            balls.getFirst().getLine().setVisible(true);
            gameLayout.getChildren().add(balls.getFirst());
        } else {
            //todo for finishing the game and winning
        }
        checkPhase(gameMenu.getGame().getCurrentBall());
    }

    public void freeze() {
        GameMenu.getGameController().setIceProgress(0);
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        targetCircle.setRotationSpeed(targetCircle.getRotationSpeed() / 2);
        targetCircle.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/images/game/ice1.png").toExternalForm())));
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::timing));
        timeline.setCycleCount(3);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                targetCircle.setRotationSpeed(targetCircle.getRotationSpeed() * 2);
                targetCircle.setFill(new ImagePattern(new Image(GameMenu.class.getResource(targetCircle.getImageAddress()).toExternalForm())));
            }
        });
    }

    private void changeDirectionPhase1() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(4500), this::changeDirection));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void changeBallsSizePhase1() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), this::changeBallsSize));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void changeBallsSize(ActionEvent actionEvent) {
        LinkedList<Ball> balls = gameMenu.getGame().getTargetCircle().getBalls();
        for (Ball ball : balls) {
            if (ball.isSmallBall()) {
                ball.setRadius(ball.getRadius() + 5);
                ball.setSmallBall(false);
            } else {
                ball.setRadius(ball.getRadius() - 5);
                ball.setSmallBall(true);
            }
        }

        for (int i = 0; i < balls.size(); i++) {
            Ball firstBall = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                if (firstBall.getBoundsInParent().intersects(balls.get(j).getBoundsInParent())) gameMenu.lose();
            }
        }
    }

    private void changeDirection(ActionEvent actionEvent) {
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        targetCircle.setRotationSpeed(-targetCircle.getRotationSpeed());
    }

    public void timing(ActionEvent actionEvent) {
    }
}
