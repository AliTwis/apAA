package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Ball extends Circle {
    private int connectedAngle = 0;
    private Line line = new Line();
    private int number;

    public Ball(double v, double v1, double v2, int number) {
        super(v, v1, v2);
        this.number = number;
        this.setFill(new ImagePattern(new Image(Ball.class.getResource("/images/game/ball1.png").toExternalForm())));

    }
}
