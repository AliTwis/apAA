package model;

import javafx.animation.Transition;
import javafx.util.Duration;

public class TargetCircleAnimation extends Transition {
    private TargetCircle targetCircle;

    public TargetCircleAnimation(TargetCircle targetCircle) {
        this.targetCircle = targetCircle;
        this.setCycleDuration(Duration.millis(3000));
        this.setCycleCount(10);
    }

    @Override
    protected void interpolate(double v) {
        targetCircle.setRotate((targetCircle.getCurrentAngle() + targetCircle.getRotationSpeed()) % 360);
        targetCircle.setCurrentAngle((targetCircle.getCurrentAngle() + targetCircle.getRotationSpeed()) % 360);
        for (Ball ball : targetCircle.getBalls()) {
            double desiredDistance = targetCircle.getRadius() + ball.getRadius() + 40;
            double tempAlpha = targetCircle.getCurrentAngle() - ball.getConnectedAngle() + 360;
            int alpha = (int) tempAlpha % 360;
            ball.setCenterX(targetCircle.getCenterX() - desiredDistance * Math.sin(alpha));
            ball.setCenterY(targetCircle.getCenterY() - desiredDistance * Math.cos(alpha));
        }
    }
}
