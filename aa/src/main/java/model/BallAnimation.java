package model;

import controller.GeneralGameController;
import controller.SinglePlayerGameController;
import javafx.util.Duration;

public class BallAnimation extends GameTransitions {
    protected Ball ball;
    protected TargetCircle centerBall;
    protected double desiredDistance;

    protected GeneralGameController gameController;

    public BallAnimation(Ball ball, TargetCircle targetCircle, GeneralGameController gameController) {
        super.addTransition(this);
        this.gameController = gameController;
        this.ball = ball;
        centerBall = targetCircle;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        desiredDistance = centerBall.getRadius() + ball.getRadius() + 40;
    }
    @Override
    protected void interpolate(double v) {
        ball.setCenterY(ball.getCenterY() - ball.getySpeed());
        ball.setCenterX(ball.getCenterX() + ball.getxSpeed());
        ball.getLine().setStartX(ball.getCenterX());
        ball.getLine().setStartY(ball.getCenterY());
        ball.getLine().setEndX(centerBall.getCenterX());
        ball.getLine().setEndY(centerBall.getCenterY());
        ball.getText().setX(ball.getCenterX() - 10);
        ball.getText().setY(ball.getCenterY() + 10);
        gameController.isOutOfGame(ball);
        if (doesIntersect()) {
            ball.setConnectedAngle(centerBall.getCurrentAngle());
            this.stop();
            if (gameController instanceof SinglePlayerGameController) gameController.addBallToCenter(ball);
            ball.getLine().setVisible(true);
            ball.getText().setVisible(true);
            GameTransitions.getTransitions().remove(this);
        }
    }

    protected boolean doesIntersect() {
        double x = ball.getCenterX() - centerBall.getCenterX();
        double y = ball.getCenterY() - centerBall.getCenterY();
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (distance < desiredDistance + 1);
    }
}
