package model;

public enum Level {
    ONE(7000, 1.8),
    TWO(5000, 2.4),
    THREE(3000, 3),
    ;
    private int iceTime;
    private double rotationSpeed;
    private int windPower;
    Level (int iceTime, double rotationSpeed) {
        this.iceTime = iceTime;
        this.rotationSpeed = rotationSpeed;
    }
}
