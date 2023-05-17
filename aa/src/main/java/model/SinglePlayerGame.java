package model;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import view.LoginMenu;

import java.util.LinkedList;

public class SinglePlayerGame {
    private Player player;
    private Stage stage;
    private Ball currentBall;
    private TargetCircle targetCircle;
    private boolean finished = false;

    public SinglePlayerGame(User user, int ballsAmount, Stage stage, Pane gameLayout) {
        this.stage = stage;
        player = new Player(user);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 70);
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, i, targetCircle);
            newBall.setBallAnimation(new BallAnimation(newBall, targetCircle));
            gameLayout.getChildren().add(newBall.getLine());
            player.getBalls().addLast(newBall);
        }
        player.getBalls().getFirst().getLine().setVisible(true);
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst());
    }

    public String shoot(Pane gameLayout) {
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            balls.getFirst().getLine().setVisible(true);
            gameLayout.getChildren().add(balls.getFirst());
        }
        else stage.close();
        return "";
    }

    public boolean checkCollision(Ball currentBall) {
        Shape intersect;
        for (Ball ball : targetCircle.getBalls()) {
//            if (!ball.equals(currentBall) && ball.getBoundsInParent().intersects(currentBall.getBoundsInParent())) return true;
            if (!ball.equals(currentBall)) {
                int distance = (int)(Math.abs(targetCircle.getCurrentAngle() - ball.getConnectedAngle())) % 360;
                if (distance < 30) return true;
//                intersect = Shape.intersect(ball, currentBall);
//                if (intersect.getBoundsInParent().getWidth() > 0) return true;
            }
        }
        return false;
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
