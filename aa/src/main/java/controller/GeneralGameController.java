package controller;

import model.Ball;
import model.TargetCircle;
import view.GameMenu;

import java.util.LinkedList;

public class GeneralGameController {
    GameMenu gameMenu;

    public GeneralGameController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public void addBallToCenter(Ball currentBall, TargetCircle center) {
        LinkedList<Ball> balls = center.getBalls();
        boolean collision = false;
        for (Ball ball : balls) {
            if (currentBall.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                collision = true;
                System.out.println("done stupid ->");
                gameMenu.getGameStage().close();
            }
        }

        if (!collision) {
            center.addBall(currentBall);
        }

    }
}
