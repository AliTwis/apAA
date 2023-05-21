package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.LinkedList;

public class TargetCircle extends Circle {
    private double currentAngle = 0;
    private double rotationSpeed = 2.4;//easy:1.8 medium:2.4 hard:3
    private TargetCircleAnimation animation = new TargetCircleAnimation(this);
    private LinkedList<Ball> balls = new LinkedList<>();
    private String imageAddress = "/images/game/monster4.png";

    public TargetCircle(double v, double v1, double v2) {
        super(v, v1, v2);
        this.setFill(new ImagePattern(new Image(Game.getTargetCircleImageAddress())));
        animation.play();
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(double currentAngle) {
        this.currentAngle = currentAngle;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public LinkedList<Ball> getBalls() {
        return balls;
    }

    public void addBall(Ball currentBall) {
        balls.addLast(currentBall);
    }

    public void setBalls(LinkedList<Ball> balls) {
        this.balls = balls;
    }

    public TargetCircleAnimation getAnimation() {
        return animation;
    }
}
