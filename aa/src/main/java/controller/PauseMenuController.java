package controller;

import javafx.event.ActionEvent;
import model.GameSaver;
import view.GameMenusFunctions;
import view.LoginMenu;
import view.MainMenu;
import view.SinglePlayerGameMenu;

import java.io.IOException;

public class PauseMenuController {
    private static GameMenusFunctions gameMenu;

    public static void setGameMenu(GameMenusFunctions gameMenu) {
        PauseMenuController.gameMenu = gameMenu;
    }

    public void resume(ActionEvent actionEvent) {
        gameMenu.getGeneralGameController().startTimeLines();
        gameMenu.getGameLayout().getChildren().remove(gameMenu.getPauseLayout());
        gameMenu.getGame().getTargetCircle().getAnimation().play();
        gameMenu.getGameLayout().setOpacity(1);
        gameMenu.setPaused(false);
    }

    public void quit(ActionEvent actionEvent) throws Exception {
        new MainMenu(gameMenu.getUser()).start(LoginMenu.gameStage);
    }

    public void showGuide(ActionEvent actionEvent) {
        GuideFXController.showGuide(gameMenu.getGameLayout(), gameMenu.getScene());
    }

    public void restart(ActionEvent actionEvent) throws Exception {
        new SinglePlayerGameMenu(gameMenu.getUser()).start(LoginMenu.gameStage);
    }

    public void selectMusic(ActionEvent actionEvent) throws IOException {
        MusicFXController.selectMusic(gameMenu.getGameLayout());
    }

    public void save(ActionEvent actionEvent) throws Exception {
        if (gameMenu instanceof SinglePlayerGameMenu) {
            GameSaver.saveGame((SinglePlayerGameMenu) gameMenu, MainMenu.user);
            new MainMenu(MainMenu.user).start(LoginMenu.gameStage);
        }
    }
}
