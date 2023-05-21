package controller;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.*;
import view.SinglePlayerGameMenu;
import view.TwoPlayerGameMenu;

import java.util.LinkedList;

public class TwoPlayerGameController extends GeneralGameController {
    TwoPlayerGameMenu gameMenu;
    private int currentPhase = 1;

    public TwoPlayerGameController (TwoPlayerGameMenu gameMenu) {
        this.gameMenu = gameMenu;
        GeneralGameController.gameMenu = gameMenu;
    }

    public void lose(Level level, User... users) {

    }

    @Override
    public void addBallToCenter(Ball currentBall) {

    }

    public void win(Level level, User... users) {

    }


    public void isOutOfGame(Ball ball) {

    }
    public void addBallToCenter(Ball currentBall, Player player) {
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
            TwoPlayerGameMenu.getGameController().increaseIceProgress();
            targetCircle.addBall(currentBall);
            if (player.equals(gameMenu.getGame().getPlayer())) {
                TwoPlayerGameMenu.getGameController().increaseScore();
                int currentBallsAmount = gameMenu.getGame().getCurrentBall();
                if (currentBallsAmount < Game.initialBallsAmount / 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall(Color.RED);
                else if (Game.initialBallsAmount - currentBallsAmount <= 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall(Color.GREEN);
                else TwoPlayerGameMenu.getGameController().decreaseBall(Color.BLUE);

            }
            else {
                TwoPlayerGameMenu.getGameController().increaseScore1();
                int currentBallsAmount = gameMenu.getGame().getCurrentBall();
                if (currentBallsAmount < Game.initialBallsAmount / 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall1(Color.RED);
                else if (Game.initialBallsAmount - currentBallsAmount <= 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall1(Color.GREEN);
                else TwoPlayerGameMenu.getGameController().decreaseBall1(Color.BLUE);
            }
        }
    }

    public void shoot(Pane gameLayout, Player player) {
        super.shoot(gameLayout, player);
        if (player.equals(gameMenu.getGame().getPlayer())) {
            gameMenu.getGame().increaseCurrentBall();
            checkPhase(gameMenu.getGame().getCurrentBall());
        }
        else {
            gameMenu.getGame().increaseCurrentBall1();
            checkPhase(gameMenu.getGame().getCurrentBall1());
        }
        //todo
    }

    public void checkPhase(int current) {
        int initial = gameMenu.getGame().getInitialBallsAmount();
        if (current == initial / 4 && currentPhase == 1) {
            changeDirectionPhase2();
            changeBallsSizePhase2();
            currentPhase = 2;
        }
        else if (current == initial / 2 && currentPhase == 2) {
            changeVisibilityPhase3();
            currentPhase = 3;
        }
        else if (current == (initial * 3) / 4 && currentPhase == 3) {
            gameMenu.setMovable(true);
            windActive = true;
            currentPhase = 4;
        }
    }

}
