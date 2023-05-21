package view;

import javafx.stage.Stage;
import model.Game;

public interface GameMenusFunctions {
    void lose();
    void win();
    Game getGame();
    Stage getGameStage();
}
