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
import java.util.Random;

public class GeneralGameController {
    GameMenu gameMenu;
    private boolean smallBall = true;
    private boolean visible = true;
    private boolean windActive = false;

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

    public void isOutOfGame(Ball ball) {
        if (ball.getCenterY() < gameMenu.getGame().getTargetCircle().getCenterY() - 40) {
            gameMenu.lose();
        }
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

    public void shoot(Pane gameLayout, Player player) {
        gameMenu.getGame().increaseCurrentBall();
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            gameLayout.getChildren().add(balls.getFirst());
        } else {
            gameMenu.win();
            //todo for finishing the game and winning
        }
        checkPhase(gameMenu.getGame().getCurrentBall());
        if (windActive && balls.size() > 0) {
            int wind = new Random().nextInt(60) - 30;
            firstBall = balls.getFirst();
            firstBall.setxSpeed((int)(10 * Math.sin(Math.toRadians(wind))));
            System.out.println("wind: " + wind + " x: " + Math.sin(Math.toRadians(wind)));
            GameMenu.getGameController().setWind(wind);
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

    private void changeDirectionPhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(4500), this::changeDirection));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void changeBallsSizePhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), this::changeBallsSize));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void changeVisibilityPhase3() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::changeVisibility));
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

    private void changeVisibility(ActionEvent actionEvent) {
        LinkedList<Ball> balls = gameMenu.getGame().getTargetCircle().getBalls();
        if (visible) {
            for (Ball ball : balls) {
                ball.setVisible(false);
                ball.getLine().setVisible(false);
            }
            visible = false;
        } else {
            for (Ball ball : balls) {
                ball.setVisible(true);
                ball.getLine().setVisible(true);
            }
            visible = true;
        }
    }

    public void timing(ActionEvent actionEvent) {
    }

    public void moveLeft() {
        Ball ball = gameMenu.getGame().getPlayer().getBalls().getFirst();
        if (ball.getCenterX() > 20) ball.setCenterX(ball.getCenterX() - 10);
    }

    public void moveRight() {
        Ball ball = gameMenu.getGame().getPlayer().getBalls().getFirst();
        if (ball.getCenterX() < gameMenu.getGameStage().getWidth() - 20) ball.setCenterX(ball.getCenterX() + 10);
    }
}
