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
import view.*;

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

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void changeDirectionPhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(4500), this::changeDirection));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change direction", timeline);
    }

    public void changeBallsSizePhase2() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), this::changeBallsSize));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change ball size", timeline);
    }

    public void changeVisibilityPhase3() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::changeVisibility));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change visibility", timeline);
    }

    public void changeWindPhase4() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e ->
                gameMenu.setWindAngle((int) ((new Random().nextInt(60) - 15) * Game.getLevel().getWindPower()))
        ));
        timeline.setCycleCount(-1);
        timeline.play();
        timelines.put("change wind", timeline);
    }

    protected void changeBallsSize(ActionEvent actionEvent) {
        LinkedList<Ball> balls = gameMenu.getGame().getTargetCircle().getBalls();
        for (Ball ball : balls) {
            if (ball.isSmallBall()) {
                ball.setRadius(ball.getRadius() + 2.5);
                ball.setSmallBall(false);
            } else {
                ball.setRadius(ball.getRadius() - 2.5);
                ball.setSmallBall(true);
            }
        }

        for (int i = 0; i < balls.size(); i++) {
            Ball firstBall = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                if (firstBall.getBoundsInParent().intersects(balls.get(j).getBoundsInParent())) {
                    gameMenu.lose();
                    if (gameMenu instanceof TwoPlayerGameMenu) {
                        TwoPlayerGameMenu twoPlayerGameMenu = (TwoPlayerGameMenu) gameMenu;
                        if (twoPlayerGameMenu.getGame().getCurrentBall() > twoPlayerGameMenu.getGame().getCurrentBall1()) {
                            twoPlayerGameMenu.getGeneralGameController().lose(Game.getLevel(),
                                    twoPlayerGameMenu.getGame().getPlayer().getUser(),
                                    twoPlayerGameMenu.getGame().getPlayer1().getUser()
                            );
                        } else {
                            twoPlayerGameMenu.getGeneralGameController().lose(Game.getLevel(),
                                    twoPlayerGameMenu.getGame().getPlayer1().getUser(),
                                    twoPlayerGameMenu.getGame().getPlayer().getUser()
                            );
                        }
                    }
                }
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
        } else {
            gameMenu.lose();
            Timeline timeline = timelines.get("timer");
            timeline.stop();
            timelines.remove("timer");
        }
        if (SinglePlayerGameMenu.getGameController() != null)
            SinglePlayerGameMenu.getGameController().setTime(minute + ":" + second);
        if (TwoPlayerGameMenu.getGameController() != null)
            TwoPlayerGameMenu.getGameController().setTime(minute + ":" + second);
    }

    public void shoot(Pane gameLayout, Player player) {
        if (player.getBalls().size() == 0) return;
        mediaPlayer = new MediaPlayer(new Media(SinglePlayerGameMenu.class.getResource("/sound/affects/shootSoundAffect.mp3").toExternalForm()));
        mediaPlayer.setVolume(1);
        if (!Game.isMute()) mediaPlayer.play();
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.setxSpeed((int) (10 * Math.sin(Math.toRadians(gameMenu.getWindAngle()))));
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            gameLayout.getChildren().addAll(balls.getFirst(), balls.getFirst().getText());
            balls.getFirst().getText().setVisible(true);
        }
    }

    public void freeze() {
        if (SinglePlayerGameMenu.getGameController() != null)
            SinglePlayerGameMenu.getGameController().setIceProgress(0);
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

    private Pane resultPane;

    private void setupFinalResult() {
        resultPane = new Pane();
        resultPane.getStylesheets().add(AvatarMenu.class.getResource("/css/game.css").toExternalForm());
        resultPane.getStyleClass().add("ending");
        resultPane.setLayoutX(75);//450
        resultPane.setLayoutY(250);//700
        resultPane.setPrefSize(300, 200);

        Text pointString = new Text("Points:");
        pointString.setTextAlignment(TextAlignment.CENTER);
        pointString.setLayoutX(70);
        pointString.setLayoutY(76);

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

        resultPane.getChildren().addAll(pointString, timeString, timeNum, backButton, rankingButton);
    }

    public void showFinalResult(boolean won, Level level) {
        setupFinalResult();
        Text result = new Text();
        result.setTextAlignment(TextAlignment.CENTER);
        if (won) result.setText("You have won!");
        else result.setText("You have lost!");
        result.setLayoutX(102);
        result.setLayoutY(37);

        Text pointNum = new Text();
        pointNum.setTextAlignment(TextAlignment.CENTER);
        pointNum.setLayoutX(186);
        pointNum.setLayoutY(76);
        if (won) pointNum.setText(Integer.toString(level.getNumber() * 150 + Game.getInitialBallsAmount() * 10));
        else pointNum.setText(Integer.toString(gameMenu.getGame().getCurrentBall() * 7));

        resultPane.getChildren().addAll(result, pointNum);
        resultPane.setFocusTraversable(false);
        gameMenu.getGameLayout().getChildren().add(resultPane);
        gameMenu.getGameLayout().setPrefWidth(gameMenu.getGameLayout().getWidth());
    }

    public void showFinalResult2(int firstUserPoint, int secondUserPoint, String username, String username1) {
        setupFinalResult();
        if (gameMenu instanceof SinglePlayerGameMenu) return;
        resultPane.getStylesheets().add(AvatarMenu.class.getResource("/css/game.css").toExternalForm());
        resultPane.getStyleClass().add("ending");
        resultPane.setLayoutX(75);//450
        resultPane.setLayoutY(250);//700
        resultPane.setPrefSize(300, 200);

        Text result = new Text();
        result.setTextAlignment(TextAlignment.CENTER);
        if (firstUserPoint > secondUserPoint) result.setText(username + " beats " + username1 + "!");
        else if (secondUserPoint > firstUserPoint) result.setText(username1 + " beats " + username + "!");
        else result.setText("Draw");
        result.setLayoutX(62);
        result.setLayoutY(37);

        Text user = new Text();
        user.setTextAlignment(TextAlignment.CENTER);
        user.setText(username);
        user.setLayoutX(166);
        user.setLayoutY(37);

        Text user1 = new Text();
        user1.setTextAlignment(TextAlignment.CENTER);
        user1.setText(username1);
        user1.setLayoutX(226);
        user1.setLayoutY(37);

        Text pointNum = new Text();
        pointNum.setTextAlignment(TextAlignment.CENTER);
        pointNum.setLayoutX(186);
        pointNum.setLayoutY(76);
        pointNum.setText(Integer.toString(firstUserPoint));

        Text pointNum1 = new Text();
        pointNum1.setTextAlignment(TextAlignment.CENTER);
        pointNum1.setLayoutX(246);
        pointNum1.setLayoutY(76);
        pointNum1.setText(Integer.toString(secondUserPoint));

        resultPane.getChildren().addAll(result, pointNum, pointNum1);
        resultPane.setFocusTraversable(false);
        gameMenu.getGameLayout().getChildren().add(resultPane);
        gameMenu.getGameLayout().setPrefWidth(gameMenu.getGameLayout().getWidth());
    }
}
