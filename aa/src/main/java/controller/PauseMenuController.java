package controller;

import javafx.event.ActionEvent;
import view.GameMenu;
import view.LoginMenu;
import view.MainMenu;

import java.io.IOException;

public class PauseMenuController {
    private static GameMenu gameMenu;

    public static GameMenu getGameMenu() {
        return gameMenu;
    }

    public static void setGameMenu(GameMenu gameMenu) {
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

    public void restart(ActionEvent actionEvent) throws Exception{
        new GameMenu(gameMenu.getUser()).start(LoginMenu.gameStage);
    }

    public void selectMusic(ActionEvent actionEvent) throws IOException {
        MusicFXController.selectMusic(gameMenu.getGameLayout());
    }
}
