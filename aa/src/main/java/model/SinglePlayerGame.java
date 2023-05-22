package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SinglePlayerGame extends Game {
    private Player player;
    private Stage stage;
    private int currentBall = 0;

    public SinglePlayerGame(User user, int ballsAmount, Stage stage, Pane gameLayout, Level level) {
        this.stage = stage;
        player = new Player(user);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 100);
        targetCircle.setRotationSpeed(level.getRotationSpeed());
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, ballsAmount - i, targetCircle, true);
            //setting colors
            if (i < initialBallsAmount / 2) newBall.setFill(Color.MEDIUMVIOLETRED);
            else if (initialBallsAmount - i <= 3) newBall.setFill(Color.GREEN);
            else newBall.setFill(Color.YELLOW);
            player.getBalls().addLast(newBall);
        }
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst(), player.getBalls().getFirst().getText());
        gameLayout.getChildren().addAll(targetCircle.getBalls());
        for (Ball ball : targetCircle.getBalls()) gameLayout.getChildren().add(ball.getLine());
        for (Ball ball : player.getBalls()) {
            gameLayout.getChildren().add(ball.getLine());
        }
        player.getBalls().getFirst().getText().setVisible(true);
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void increaseCurrentBall() {
        this.currentBall++;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }
}
