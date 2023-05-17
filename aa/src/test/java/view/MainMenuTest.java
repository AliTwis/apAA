package view;

import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {
    @Test
    void print() throws Exception {
        MainMenu mainMenu = new MainMenu(new User("ali", "password"));
        mainMenu.start(LoginMenu.gameStage);
    }

}