package controller;

import javafx.scene.layout.Pane;
import model.Ball;
import model.Player;
import model.TargetCircle;
import view.GameMenu;

import java.util.LinkedList;

public class GeneralGameController {
    GameMenu gameMenu;

    public GeneralGameController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public void addBallToCenter(Ball currentBall) {
        TargetCircle targetCircle = gameMenu.getGame().getTargetCircle();
        LinkedList<Ball> balls = targetCircle.getBalls();
        boolean collision = false;
        for (Ball ball : balls) {
            if (currentBall.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                collision = true;
                gameMenu.lose();
            }
        }

        if (!collision) {
            targetCircle.addBall(currentBall);
        }

    }

    public void shoot(Pane gameLayout, Player player) {
        LinkedList<Ball> balls = player.getBalls();
        Ball firstBall = balls.getFirst();
        firstBall.getBallAnimation().play();
        balls.removeFirst();
        if (balls.size() > 0) {
            balls.getFirst().getLine().setVisible(true);
            gameLayout.getChildren().add(balls.getFirst());
        }
    }
}
