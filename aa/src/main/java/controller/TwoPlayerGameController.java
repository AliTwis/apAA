package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.*;
import view.TwoPlayerGameMenu;

import java.util.LinkedList;

public class TwoPlayerGameController extends GeneralGameController {
    TwoPlayerGameMenu gameMenu;
    private int currentPhase = 1;

    public TwoPlayerGameController(TwoPlayerGameMenu gameMenu) {
        this.gameMenu = gameMenu;
        GeneralGameController.gameMenu = gameMenu;
    }

    public void lose(Level level, User... users) {
        endGame(level, users);
    }

    public void win(Level level, User... users) {
        endGame(level, users);
    }

    public void endGame(Level level, User... users) {
        int winnerPoint, loserPoint;
        String username = gameMenu.getGame().getPlayer().getUser().getUsername();
        String username1 = gameMenu.getGame().getPlayer1().getUser().getUsername();
        if (gameMenu.getGame().getPlayer().getUser().equals(users[0])) {
            winnerPoint = level.getNumber() * 150 + gameMenu.getGame().getCurrentBall() * 10;
            loserPoint = 7 * gameMenu.getGame().getCurrentBall1();
            showFinalResultCaller(winnerPoint, loserPoint, username, username1);
        } else {
            winnerPoint = level.getNumber() * 150 + gameMenu.getGame().getCurrentBall1() * 10;
            loserPoint = 7 * gameMenu.getGame().getCurrentBall();
            showFinalResultCaller(loserPoint, winnerPoint, username, username1);
        }
        users[0].increaseScore(winnerPoint);
        users[1].increaseScore(loserPoint);
        User.updateUsers();
        stopTimeLines();
    }

    private void showFinalResultCaller(int firstPoint, int secondPoint, String username, String username1) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            gameMenu.getGeneralGameController().timing(e);
        }));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gameMenu.getGeneralGameController().showFinalResult2(firstPoint, secondPoint, username, username1);
                } catch (Exception e) {
                    System.out.println("hell");
                }
            }
        });

    }

    @Override
    public void addBallToCenter(Ball currentBall) {

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
                if (player.equals(gameMenu.getGame().getPlayer()))
                    lose(Game.getLevel(),gameMenu.getGame().getPlayer1().getUser(), player.getUser());
                else {
                    lose(Game.getLevel(), gameMenu.getGame().getPlayer().getUser(), player.getUser());
                }
            }
        }

        if (!collision) {;
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

                if (player.getBalls().size() == 0) {
                    gameMenu.win();
                    win(Game.getLevel(), player.getUser(), gameMenu.getGame().getPlayer1().getUser());
                }

            } else {
                TwoPlayerGameMenu.getGameController().increaseScore1();
                int currentBallsAmount = gameMenu.getGame().getCurrentBall();
                if (currentBallsAmount < Game.initialBallsAmount / 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall1(Color.RED);
                else if (Game.initialBallsAmount - currentBallsAmount <= 2)
                    TwoPlayerGameMenu.getGameController().decreaseBall1(Color.GREEN);
                else TwoPlayerGameMenu.getGameController().decreaseBall1(Color.BLUE);
                if (player.getBalls().size() == 0) {
                    gameMenu.win();
                    win(Game.getLevel(), player.getUser(), gameMenu.getGame().getPlayer().getUser());
                }
            }
        }
        else if (gameMenu.getGame().getPlayer().getBalls().size() == 0) {
            gameMenu.lose();
            if (player.equals(gameMenu.getGame().getPlayer()))
                lose(Game.getLevel(),gameMenu.getGame().getPlayer1().getUser(), player.getUser());
            else {
                lose(Game.getLevel(), gameMenu.getGame().getPlayer().getUser(), player.getUser());
            }
        }
    }

    public void shoot(Pane gameLayout, Player player) {
        super.shoot(gameLayout, player);
        if (player.equals(gameMenu.getGame().getPlayer())) {
            gameMenu.getGame().increaseCurrentBall();
            checkPhase(gameMenu.getGame().getCurrentBall());
        } else {
            gameMenu.getGame().increaseCurrentBall1();
            checkPhase(gameMenu.getGame().getCurrentBall1());
        }
    }

    public void checkPhase(int current) {
        int initial = Game.getInitialBallsAmount();
        if (current == initial / 4 && currentPhase == 1) {
            changeDirectionPhase2();
            changeBallsSizePhase2();
            currentPhase = 2;
        } else if (current == initial / 2 && currentPhase == 2) {
            changeVisibilityPhase3();
            currentPhase = 3;
        } else if (current == (initial * 3) / 4 && currentPhase == 3) {
            gameMenu.setMovable(true);
            windActive = true;
            currentPhase = 4;
        }
    }

}
