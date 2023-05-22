package view;

import model.User;
import org.junit.jupiter.api.Test;

class MainMenuTest {
    @Test
    void print() throws Exception {
        MainMenu mainMenu = new MainMenu(new User("ali", "password"));
        mainMenu.start(LoginMenu.gameStage);
    }

}