package model;

import javafx.util.Duration;

public class TargetCircleAnimation extends GameTransitions {
    private final TargetCircle targetCircle;

    public TargetCircleAnimation(TargetCircle targetCircle) {
        super.addTransition(this);
        this.targetCircle = targetCircle;
        this.setCycleDuration(Duration.millis(10000));
        this.setCycleCount(10);
    }

    @Override
    protected void interpolate(double v) {
        targetCircle.setRotate((targetCircle.getCurrentAngle() + targetCircle.getRotationSpeed()) % 360);
        targetCircle.setCurrentAngle((targetCircle.getCurrentAngle() + targetCircle.getRotationSpeed()) % 360);
        for (Ball ball : targetCircle.getBalls()) {
            ball.getNewRotate().setAngle(targetCircle.getCurrentAngle() - ball.getConnectedAngle());
        }
    }
}
