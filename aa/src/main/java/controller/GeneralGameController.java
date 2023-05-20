package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.*;
import view.GameMenu;
import view.LoginMenu;
import view.MainMenu;
import view.MainMenuFXController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GeneralGameController {
    GameMenu gameMenu;
    private boolean smallBall = true;
    private boolean visible = true;
    private boolean windActive = false;
    private int initialMinute = 1;
    private int initialSecond = 5;
    private int minute = initialMinute;
    private int second = initialSecond;
    private MediaPlayer mediaPlayer;
    private HashMap<String, Timeline> timelines = new HashMap<>();

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
        mediaPlayer = new MediaPlayer(new Media(GameMenu.class.getResource("/sound/affects/shootSoundAffect.mp3").toExternalForm()));
        mediaPlayer.setVolume(1);
        mediaPlayer.play();
        gameMenu.getGame().increaseCurrentBall();
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            gameLayout.getChildren().add(balls.getFirst());
        } else {
            gameMenu.win();
        }
        checkPhase(gameMenu.getGame().getCurrentBall());
        if (windActive && balls.size() > 0) {
            int wind = new Random().nextInt(60) - 30;
            firstBall = balls.getFirst();
            firstBall.setxSpeed((int)(10 * Math.sin(Math.toRadians(wind))));
            GameMenu.getGameController().setWind(wind);
        }
    }

    public void freeze() {
        GameMenu.getGameController().setIceProgress(0);
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        targetCircle.setRotationSpeed(targetCircle.getRotationSpeed() / 2);
        targetCircle.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/images/game/ice1.png").toExternalForm())));
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Game.getLevel().getIceTime()), this::timing));
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
        timelines.put("change direction", timeline);
    }

    private void changeBallsSizePhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), this::changeBallsSize));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change ball size", timeline);
    }

    private void changeVisibilityPhase3() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::changeVisibility));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change visibility", timeline);
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
        GameMenu.getGameController().setTime(minute + ":" + second);
    }

    public void lose(User user, Level level) {
        int score = gameMenu.getGame().getCurrentBall() * 7;
        user.increaseScore(score);
        User.updateUsers();
        stopTimeLines();
        showFinalResult(false, level);
    }

    public void win(User user, Level level) {
        user.increaseScore(level.getNumber() * 150 + Game.getInitialBallsAmount() * 10);
        user.updateTime();
        User.updateUsers();
        stopTimeLines();
        showFinalResult(true, level);
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

    public void showFinalResult(boolean won, Level level) {
        Pane resultPane = new Pane();
        resultPane.setStyle("-fx-background-color: 'white';");
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
