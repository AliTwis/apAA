package model;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.LoginMenu;

public class SinglePlayerGame {
    private Player player;
    private Stage stage;
    TargetCircle targetCircle;

    public SinglePlayerGame(User user, int ballsAmount, Stage stage, Pane gameLayout) {
        this.stage = stage;
        player = new Player(user);
        targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 70);
        Ball newBall;
        for (int i = 0; i < ballsAmount; i++) {
            newBall = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, i, targetCircle);
            newBall.setBallAnimation(new BallAnimation(newBall, targetCircle));
            gameLayout.getChildren().add(newBall.getLine());
            player.getBalls().addLast(newBall);
        }
        player.getBalls().getFirst().getLine().setVisible(true);
        gameLayout.getChildren().addAll(targetCircle, player.getBalls().getFirst());
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

    public TargetCircle getTargetCircle() {
        return targetCircle;
    }

    public void setTargetCircle(TargetCircle targetCircle) {
        this.targetCircle = targetCircle;
    }
}
