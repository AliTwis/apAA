package model;

public enum Level {
    ONE(1, 7000, 1.8),
    TWO(2, 5000, 2.4),
    THREE(3, 3000, 3),
    ;
    private int number;
    private int iceTime;
    private double rotationSpeed;
    private int windPower;
    Level (int number, int iceTime, double rotationSpeed) {
        this.iceTime = iceTime;
        this.rotationSpeed = rotationSpeed;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getIceTime() {
        return iceTime;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public int getWindPower() {
        return windPower;
    }
}
