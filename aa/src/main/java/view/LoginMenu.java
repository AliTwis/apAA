package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class LoginMenu extends Application {
    public static Stage gameStage;

    public static void main(String[] args) {
        User.loadSavedData();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(600);
        stage.setHeight(400);
        gameStage = stage;
        BorderPane borderPane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/fxml/loginMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
