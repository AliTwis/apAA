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

public class SinglePlayerGame extends Game{
    private Player player;
    private Stage stage;
    private int currentBall = 0;

    public SinglePlayerGame(User user, int ballsAmount, Stage stage, Pane gameLayout, Level level) {
        this.initialBallsAmount = ballsAmount;
        this.stage = stage;
        player = new Player(user);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 100);
        targetCircle.setRotationSpeed(level.getRotationSpeed());
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, i, targetCircle, true);
            //setting colors
            if (i < ballsAmount / 2) newBall.setFill(Color.MEDIUMVIOLETRED);
            else if (ballsAmount - i <= 2) newBall.setFill(Color.GREEN);
            else newBall.setFill(Color.YELLOW);
            gameLayout.getChildren().add(newBall.getLine());
            player.getBalls().addLast(newBall);
        }
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst());
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void increaseCurrentBall() {
        this.currentBall++;
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
}
