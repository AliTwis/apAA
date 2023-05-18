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

    public void shoot(Pane gameLayout, Player player) {
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

    public void timing(ActionEvent actionEvent) {
    }
}
