package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class LoginMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/fxml/loginMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
