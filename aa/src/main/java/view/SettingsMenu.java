package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(1000);
        stage.setWidth(600);
        Pane settingPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/settings.fxml"));
        Scene scene = new Scene(settingPane);
        stage.setScene(scene);
        stage.show();
    }
}
