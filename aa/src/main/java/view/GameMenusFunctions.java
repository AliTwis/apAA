package view;

import controller.GeneralGameController;
import controller.SinglePlayerFXController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;
import model.User;

public interface GameMenusFunctions {
    void lose();
    void win();
    int getWindAngle();
    void setWindAngle(int windAngle);
    Game getGame();
    Stage getGameStage();

    GeneralGameController getGeneralGameController();

    Object getPauseLayout();

    Pane getGameLayout();

    Scene getScene();

    User getUser();

    void setPaused(boolean b);
}
