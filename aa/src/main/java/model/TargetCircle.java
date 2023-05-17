package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import view.GameMenu;

import java.util.LinkedList;

public class TargetCircle extends Circle {
    private int currentAngle = 0;
    private int rotationSpeed = 1;
    private TargetCircleAnimation animation = new TargetCircleAnimation(this);
    private LinkedList<Ball> balls = new LinkedList<>();
    private String imageAddress;

    public TargetCircle(double v, double v1, double v2) {
        super(v, v1, v2);
        this.setFill(new ImagePattern(new Image(TargetCircle.class.getResource("/images/game/monster4.png").toExternalForm())));
        animation.play();
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public int getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(int currentAngle) {
        this.currentAngle = currentAngle;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
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
}
