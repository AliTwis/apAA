package model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Ball extends Circle {
    private double connectedAngle = -1;
    private Line line = new Line();
    private int xSpeed = 0;
    private int ySpeed = 10;
    private BallAnimation ballAnimation;
    private Rotate rotate;
    private int number;

    private Pane gameLayout;

    public Ball(double v, double v1, double v2, int number, TargetCircle center) {
        super(v, v1, v2);
        this.number = number;
        this.setFill(new ImagePattern(new Image(Ball.class.getResource("/images/game/ball1.png").toExternalForm())));
        rotate = new Rotate();
        rotate.setPivotX(center.getCenterX());
        rotate.setPivotY(center.getCenterY());
        this.getTransforms().add(rotate);
        line.getTransforms().add(rotate);
        this.getLine().setStartX(this.getCenterX());
        this.getLine().setStartY(this.getCenterY());
        this.getLine().setEndX(center.getCenterX());
        this.getLine().setEndY(center.getCenterY());
        line.setVisible(false);
    }

    public Pane getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(Pane gameLayout) {
        this.gameLayout = gameLayout;
    }

    public Rotate getNewRotate() {
        return rotate;
    }

    public double getConnectedAngle() {
        return connectedAngle;
    }

    public void setConnectedAngle(double connectedAngle) {
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
