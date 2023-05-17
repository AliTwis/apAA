package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class MainMenu extends Application {
    private User user;

    public MainMenu(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/fxml/mainMenu.fxml");
        VBox vBox = FXMLLoader.load(url);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }
}
