package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class MainMenu extends Application {
    public static User user;
    private static MainMenu mainMenu;
    private Pane pane;

    public MainMenu(User user) {
        MainMenu.user = user;
        MainMenu.mainMenu = this;
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    public Pane getPane() {
        return pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/fxml/mainMenu.fxml");
        pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.show();
    }
}
