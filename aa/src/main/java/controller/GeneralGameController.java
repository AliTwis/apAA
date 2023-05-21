package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.*;
import view.GameMenusFunctions;
import view.SinglePlayerGameMenu;
import view.TwoPlayerGameMenu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public abstract class GeneralGameController {
    protected boolean visible = true;
    protected boolean windActive = false;
    protected int initialMinute = 1;
    protected int initialSecond = 5;
    protected int minute = initialMinute;
    protected int second = initialSecond;
    protected MediaPlayer mediaPlayer;
    protected HashMap<String, Timeline> timelines = new HashMap<>();
    static GameMenusFunctions gameMenu;

    public abstract void win(Level level, User... users);
    public abstract void lose(Level level, User... users);
    public abstract void addBallToCenter(Ball currentBall);

    protected void changeDirectionPhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(4500), this::changeDirection));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change direction", timeline);
    }

    protected void changeBallsSizePhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), this::changeBallsSize));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change ball size", timeline);
    }

    protected void changeVisibilityPhase3() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::changeVisibility));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change visibility", timeline);
    }

    protected void changeBallsSize(ActionEvent actionEvent) {
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

    protected void changeDirection(ActionEvent actionEvent) {
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        targetCircle.setRotationSpeed(-targetCircle.getRotationSpeed());
    }

    protected void changeVisibility(ActionEvent actionEvent) {
        LinkedList<Ball> balls = gameMenu.getGame().getTargetCircle().getBalls();
        if (visible) {
            for (Ball ball : balls) {
                ball.setVisible(false);
                ball.getLine().setVisible(false);
                ball.getText().setVisible(false);
            }
            visible = false;
        } else {
            for (Ball ball : balls) {
                ball.setVisible(true);
                ball.getLine().setVisible(true);
                ball.getText().setVisible(true);
            }
            visible = true;
        }
    }

    public void timing(ActionEvent actionEvent) {
    }

    public void stopTimeLines() {
        for (Timeline timeline : timelines.values()) {
            timeline.stop();
        }
    }

    public void startTimeLines() {
        for (Timeline timeline : timelines.values()) {
            timeline.play();
        }
    }

    public void addTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::passTime));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("timer", timeline);
    }

    private void passTime(ActionEvent actionEvent) {
        if (second > 0) second--;
        else if (second == 0 && minute > 0) {
            second = 59;
            minute--;
        }
        else {
            gameMenu.lose();
            Timeline timeline = timelines.get("timer");
            timeline.stop();
            timelines.remove("timer");
        }
        if (SinglePlayerGameMenu.getGameController() != null) SinglePlayerGameMenu.getGameController().setTime(minute + ":" + second);
        if (TwoPlayerGameMenu.getGameController() != null) TwoPlayerGameMenu.getGameController().setTime(minute + ":" + second);
    }

    public void shoot(Pane gameLayout, Player player) {
        mediaPlayer = new MediaPlayer(new Media(SinglePlayerGameMenu.class.getResource("/sound/affects/shootSoundAffect.mp3").toExternalForm()));
        mediaPlayer.setVolume(1);
        mediaPlayer.play();
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            gameLayout.getChildren().addAll(balls.getFirst(), balls.getFirst().getText());
            balls.getFirst().getText().setVisible(true);
        } else {
            gameMenu.win();
        }
        if (windActive && balls.size() > 0) {
            int wind = new Random().nextInt(60) - 30;
            firstBall = balls.getFirst();
            firstBall.setxSpeed((int)(10 * Math.sin(Math.toRadians(wind))));
            SinglePlayerGameMenu.getGameController().setWind(wind);
        }
    }

    public void freeze() {
        if (SinglePlayerGameMenu.getGameController() != null) SinglePlayerGameMenu.getGameController().setIceProgress(0);
        if (TwoPlayerGameMenu.getGameController() != null) TwoPlayerGameMenu.getGameController().setIceProgress(0);
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        targetCircle.setRotationSpeed(targetCircle.getRotationSpeed() / 2);
        targetCircle.setFill(new ImagePattern(new Image(SinglePlayerGameMenu.class.getResource("/images/game/ice1.png").toExternalForm())));
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Game.getLevel().getIceTime()), this::timing));
        timeline.setCycleCount(3);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                targetCircle.setRotationSpeed(targetCircle.getRotationSpeed() * 2);
                targetCircle.setFill(new ImagePattern(new Image(SinglePlayerGameMenu.class.getResource(targetCircle.getImageAddress()).toExternalForm())));
            }
        });
    }

    public void isOutOfGame(Ball ball) {
        if (ball.getCenterY() <= 0 || ball.getCenterY() >= gameMenu.getGameStage().getHeight() ||
                ball.getCenterX() <= 0 || ball.getxSpeed() >= gameMenu.getGameStage().getWidth()) {
            gameMenu.lose();
        }
    }
}
