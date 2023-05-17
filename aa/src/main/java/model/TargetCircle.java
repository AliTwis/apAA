package model;

import javafx.scene.shape.Circle;

import java.util.LinkedList;

public class TargetCircle extends Circle {
    private int currentAngle = 0;
    private int rotationSpeed = 0;
    private LinkedList<Ball> balls = new LinkedList<>();

    public TargetCircle(double v, double v1, double v2) {
        super(v, v1, v2);
    }
}
