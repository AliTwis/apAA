package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TwoPlayerGame extends Game{
    private Player player, player1;
    private Stage stage;
    private int currentBall = 0, currentBall1 = 0;
    private TargetCircle targetCircle;
    public TwoPlayerGame(User user, User user1, int ballsAmount, Stage stage, Pane gameLayout, Level level) {
        this.initialBallsAmount = ballsAmount;
        this.stage = stage;
        player = new Player(user);
        player1 = new Player(user1);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 100);
        targetCircle.setRotationSpeed(level.getRotationSpeed());
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, i, targetCircle, true);
            gameLayout.getChildren().add(newBall.getLine());
            player.getBalls().addLast(newBall);
        }

        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, 50, 10, i, targetCircle, false);
            gameLayout.getChildren().add(newBall.getLine());
            player1.getBalls().addLast(newBall);
        }
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst(), player1.getBalls().getFirst());
    }

    public Player getPlayer() {
        return player;
    }

    public void increaseCurrentBall() {
        currentBall++;
    }
    public void increaseCurrentBall1() {
        currentBall1++;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }

    public int getCurrentBall1() {
        return currentBall1;
    }

    public void setCurrentBall1(int currentBall1) {
        this.currentBall1 = currentBall1;
    }

    public TargetCircle getTargetCircle() {
        return targetCircle;
    }

    public void setTargetCircle(TargetCircle targetCircle) {
        this.targetCircle = targetCircle;
    }
}
