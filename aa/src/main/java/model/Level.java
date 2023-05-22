package model;

public enum Level {
    ONE(1, 1, 7000, 1.8),
    TWO(1.2, 2, 5000, 2.4),
    THREE(1.5, 3, 3000, 3),
    ;
    private int number;
    private int iceTime;
    private double rotationSpeed;
    private double windPower;
    Level (double windPower, int number, int iceTime, double rotationSpeed) {
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

    public double getWindPower() {
        return windPower;
    }
}
