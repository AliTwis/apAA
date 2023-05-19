package model;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import view.LoginMenu;

import java.util.LinkedList;

public class SinglePlayerGame {
    private Player player;
    private Stage stage;
    private int initialBallsAmount;
    private int currentBall = 0;
    private TargetCircle targetCircle;
    private boolean finished = false;

    public SinglePlayerGame(User user, int ballsAmount, Stage stage, Pane gameLayout) {
        this.initialBallsAmount = ballsAmount;
        this.stage = stage;
        player = new Player(user);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 100);
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, i, targetCircle);
            //setting colors
            if (i < ballsAmount / 2) newBall.setFill(Color.MEDIUMVIOLETRED);
            else if (ballsAmount - i <= 2) newBall.setFill(Color.GREEN);
            else newBall.setFill(Color.YELLOW);
            gameLayout.getChildren().add(newBall.getLine());
            player.getBalls().addLast(newBall);
        }
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst());
    }



    public boolean checkCollision(Ball currentBall) {
        Shape intersect;
        for (Ball ball : targetCircle.getBalls()) {
            if (!ball.equals(currentBall)) {
                int distance = (int)(Math.abs(targetCircle.getCurrentAngle() - ball.getConnectedAngle())) % 360;
                if (distance < 30) return true;
            }
        }
        return false;
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }

    public void increaseCurrentBall() {
        this.currentBall++;
    }

    public int getInitialBallsAmount() {
        return initialBallsAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TargetCircle getTargetCircle() {
        return targetCircle;
    }

    public void setTargetCircle(TargetCircle targetCircle) {
        this.targetCircle = targetCircle;
    }
}
