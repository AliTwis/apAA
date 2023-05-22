package model;

import java.util.LinkedList;

public class Player {
    private User user;
    private LinkedList<Ball> balls = new LinkedList<>();

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LinkedList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(LinkedList<Ball> balls) {
        this.balls = balls;
    }
}
