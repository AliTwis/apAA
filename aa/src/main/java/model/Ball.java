package model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Ball extends Circle {
    private double connectedAngle = -1;
    private Line line = new Line();
    private final Text text = new Text();
    private int xSpeed = 0;
    private int ySpeed = 10;
    private BallAnimation ballAnimation;
    private final Rotate rotate;
    private int number;
    private boolean smallBall = true;

    private Pane gameLayout;

    public Ball(double v, double v1, double v2, int number, TargetCircle center, boolean isFirst) {
        super(v, v1, v2);
        if (isFirst) ySpeed = 10;
        else ySpeed = -10;
        this.number = number;
        text.setText(Integer.toString(number));
        text.setX(v - 5);
        text.setY(v1 + 5);
        if (isFirst)
            this.setFill(new ImagePattern(new Image(Ball.class.getResource("/images/game/ball1.png").toExternalForm())));
        else
            this.setFill(new ImagePattern(new Image(Ball.class.getResource("/images/game/ball2.png").toExternalForm())));
        rotate = new Rotate();
        rotate.setPivotX(center.getCenterX());
        rotate.setPivotY(center.getCenterY());
        this.getTransforms().add(rotate);
        line.getTransforms().add(rotate);
        text.getTransforms().add(rotate);
        this.getLine().setStartX(this.getCenterX());
        this.getLine().setStartY(this.getCenterY());
        this.getLine().setEndX(center.getCenterX());
        this.getLine().setEndY(center.getCenterY());
        line.setVisible(false);
        text.setVisible(false);
    }


    public boolean isSmallBall() {
        return smallBall;
    }

    public void setSmallBall(boolean smallBall) {
        this.smallBall = smallBall;
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

    public Text getText() {
        return text;
    }
}
