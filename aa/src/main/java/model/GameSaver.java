package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.GeneralGameController;
import controller.SinglePlayerGameController;
import javafx.scene.paint.Color;
import view.LoginMenu;
import view.MainMenu;
import view.SinglePlayerGameMenu;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameSaver {
    private int level, ballsLeft, score, minute, second, currentBall;
    private boolean paused, movable;
    private int initialBallsAmount;
    private double progressBar;
    private String targetCircleImageAddress;
    private ArrayList<Double> angles = new ArrayList<>();
    private ArrayList<Integer> targetBallsNumbers = new ArrayList<>();
    private ArrayList<Integer> leftBallsNumbers = new ArrayList<>();

    private GameSaver(SinglePlayerGameMenu gameMenu) {
        currentBall = gameMenu.getGame().getCurrentBall();
        paused = gameMenu.isPaused();
        movable = gameMenu.isMovable();
        minute = gameMenu.getGeneralGameController().getMinute();
        second = gameMenu.getGeneralGameController().getSecond();
        initialBallsAmount = Game.getInitialBallsAmount();
        level = Game.getLevel().getNumber();
        ballsLeft = SinglePlayerGameMenu.getGameController().getBallsLeft();
        score = Integer.parseInt(SinglePlayerGameMenu.getGameController().getScore().getText());
        progressBar = SinglePlayerGameMenu.getGameController().getIceProgress();
        targetCircleImageAddress = gameMenu.getGame().getTargetCircle().getImageAddress();
        for (Ball ball : gameMenu.getGame().getTargetCircle().getBalls()) {
            angles.add(ball.getConnectedAngle());
            targetBallsNumbers.add(ball.getNumber());
        }
        for (Ball ball : gameMenu.getGame().getPlayer().getBalls()) {
            leftBallsNumbers.add(ball.getNumber());
        }
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson;
    }

    public static void saveGame(SinglePlayerGameMenu gameMenu, User user) {
        GameSaver gameSaver = new GameSaver(gameMenu);
        Gson gson = getGson();
        String data = gson.toJson(gameSaver);
        user.setSavedGame(data);
        User.updateUsers();
    }

    public static SinglePlayerGame loadSavedGame(SinglePlayerGameMenu gameMenu) throws IOException {
        String data = MainMenu.user.getSavedGame();
        MainMenu.user.setSavedGame(null);
        User.updateUsers();
        Gson gson = getGson();
        GameSaver gameSaver = gson.fromJson(data, GameSaver.class);
        gameMenu.getGeneralGameController().setMinute(gameSaver.minute);
        gameMenu.getGeneralGameController().setSecond(gameSaver.second);
        gameMenu.setMovable(gameSaver.movable);

        SinglePlayerGameMenu.getGameController().setBallsLeft(gameSaver.ballsLeft);
        SinglePlayerGameMenu.getGameController().setScore(gameSaver.score);
        SinglePlayerGameMenu.getGameController().setIceProgress(gameSaver.progressBar);
        SinglePlayerGameMenu.getGameController().setBallsLeft(gameSaver.ballsLeft);

        Level levelNum;
        switch (gameSaver.level) {
            case 1 :{
                levelNum = Level.ONE;
                break;
            }
            case 2 : {
                levelNum = Level.TWO;
                break;
            }
            default: levelNum = Level.THREE;
        }

        Game.setInitialBallsAmount(gameSaver.initialBallsAmount);
        SinglePlayerGame game = new SinglePlayerGame(MainMenu.user, gameSaver.ballsLeft, LoginMenu.gameStage, gameMenu.getGameLayout(), levelNum);
        game.setCurrentBall(gameSaver.currentBall);
        TargetCircle targetCircle = game.getTargetCircle();
        int desiredDistance = 155;
        for (int i = 0; i < gameSaver.angles.size(); i++) {
            double ballAngle = Math.toRadians(gameSaver.angles.get(i));
            Ball newBall = new Ball(targetCircle.getCenterX() + desiredDistance * Math.sin(ballAngle),
                    targetCircle.getCenterY() - desiredDistance * Math.cos(ballAngle),
                    10, gameSaver.targetBallsNumbers.get(i), targetCircle, true
            );
            newBall.setVisible(true);
            newBall.getLine().setVisible(true);
            if (newBall.getNumber() != 0) newBall.getText().setText(Integer.toString(newBall.getNumber()));
            gameMenu.getGameLayout().getChildren().addAll(newBall, newBall.getLine(), newBall.getText());
            targetCircle.getBalls().addLast(newBall);
        }

        int initial = Game.getInitialBallsAmount();
        int current = gameSaver.currentBall;
        if (current >= initial / 4) {
            gameMenu.getGeneralGameController().changeDirectionPhase2();
            gameMenu.getGeneralGameController().changeBallsSizePhase2();
        }
        if (current >= initial / 2) {
            gameMenu.getGeneralGameController().changeVisibilityPhase3();
        }
        if (current >= ((initial / 4) * 3)) {
            gameMenu.getGeneralGameController().changeWindPhase4();
            gameMenu.setMovable(true);
        }

        return game;
    }
}
