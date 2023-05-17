package model;

import javafx.animation.Transition;
import javafx.application.Application;
import javafx.util.Duration;

public class BallAnimation extends Transition {
    private Ball ball;
    private TargetCircle centerBall;
    private double desiredDistance;

    public BallAnimation(Ball ball, TargetCircle targetCircle) {
        this.ball = ball;
        centerBall = targetCircle;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        desiredDistance = centerBall.getRadius() + ball.getRadius() + 40;
    }
    @Override
    protected void interpolate(double v) {
        ball.setCenterY(ball.getCenterY() - ball.getySpeed());
        ball.setCenterX(ball.getCenterX() - ball.getxSpeed());
        ball.getLine().setStartX(ball.getCenterX());
        ball.getLine().setStartY(ball.getCenterY());
        ball.getLine().setEndX(centerBall.getCenterX());
        ball.getLine().setEndY(centerBall.getCenterY());
        if (doesIntersect()) {
            ball.setConnectedAngle(centerBall.getCurrentAngle());
            this.stop();
            centerBall.getBalls().addLast(ball);
            ball.getLine().setVisible(true);
        }
    }

    private boolean doesIntersect() {
        double x = ball.getCenterX() - centerBall.getCenterX();
        double y = ball.getCenterY() - centerBall.getCenterY();
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (distance < desiredDistance + 1);
    }
}
