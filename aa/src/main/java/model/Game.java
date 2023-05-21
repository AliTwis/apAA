package model;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public abstract class Game{
    private static Level level = Level.TWO;
    public static int initialBallsAmount = 10;
    private static String targetCircleImageAddress = TargetCircle.class.getResource("/images/game/monster4.png").toExternalForm();
    protected TargetCircle targetCircle;
    private static HashMap<String, KeyCode> gameKeys = new HashMap<>() {{
        put("shoot", KeyCode.SPACE);
        put("shoot2", KeyCode.ENTER);
        put("freeze", KeyCode.TAB);
        put("pause", KeyCode.ESCAPE);
    }};



    public TargetCircle getTargetCircle() {
        return targetCircle;
    }
    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        Game.level = level;
    }

    public static int getInitialBallsAmount() {
        return initialBallsAmount;
    }

    public static void setInitialBallsAmount(int initialBallsAmount) {
        Game.initialBallsAmount = initialBallsAmount;
    }

    public static String getTargetCircleImageAddress() {
        return targetCircleImageAddress;
    }

    public static void setTargetCircleImageAddress(String address) {
        targetCircleImageAddress = address;
    }

    public static HashMap<String, KeyCode> getGameKeys() {
        return gameKeys;
    }

    public static void setGameKeys(HashMap<String, KeyCode> gameKeys) {
        Game.gameKeys = gameKeys;
    }
}
