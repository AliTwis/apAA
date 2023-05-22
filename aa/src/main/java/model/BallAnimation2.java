package model;

import controller.TwoPlayerGameController;

public class BallAnimation2 extends BallAnimation {
    private final Player player;
    TwoPlayerGameController gameController;

    public BallAnimation2(Ball ball, TargetCircle targetCircle, TwoPlayerGameController gameController, Player player) {
        super(ball, targetCircle, gameController);
        this.gameController = gameController;
        this.player = player;
    }

    @Override
    protected void interpolate(double v) {
        ball.setCenterY(ball.getCenterY() - ball.getySpeed());
        ball.setCenterX(ball.getCenterX() + ball.getxSpeed());
        ball.getLine().setStartX(ball.getCenterX());
        ball.getLine().setStartY(ball.getCenterY());
        ball.getLine().setEndX(centerBall.getCenterX());
        ball.getLine().setEndY(centerBall.getCenterY());
        gameController.isOutOfGame(ball);
        if (doesIntersect()) {
            ball.setConnectedAngle(centerBall.getCurrentAngle());
            this.stop();
            gameController.addBallToCenter(ball, player);
            ball.getLine().setVisible(true);
            GameTransitions.getTransitions().remove(this);
        }
    }
}
