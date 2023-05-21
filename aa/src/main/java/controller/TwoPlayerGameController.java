package controller;

import javafx.scene.layout.Pane;
import model.*;
import view.TwoPlayerGameMenu;

public class TwoPlayerGameController extends GeneralGameController {
    TwoPlayerGameMenu gameMenu;
    public void lose(Level level, User... users) {

    }

    public void win(Level level, User... users) {

    }


    public void isOutOfGame(Ball ball) {

    }
    public void addBallToCenter(Ball currentBall) {}

    public void shoot(Pane gameLayout, Player player) {
        super.shoot(gameLayout, player);
        //todo
    }

}
