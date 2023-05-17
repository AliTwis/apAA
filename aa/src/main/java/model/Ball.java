package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Ball extends Circle {
    private int connectedAngle = -1;
    private Line line = new Line();
    private int xSpeed = 0;
    private int ySpeed = 10;
    private BallAnimation ballAnimation;
    private int number;

    public Ball(double v, double v1, double v2, int number) {
        super(v, v1, v2);
        this.number = number;
        this.setFill(new ImagePattern(new Image(Ball.class.getResource("/images/game/ball1.png").toExternalForm())));

    }

    public int getConnectedAngle() {
        return connectedAngle;
    }

    public void setConnectedAngle(int connectedAngle) {
        this.connectedAngle = connectedAngle;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public BallAnimation getBallAnimation() {
        return ballAnimation;
    }

    public void setBallAnimation(BallAnimation ballAnimation) {
        this.ballAnimation = ballAnimation;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
