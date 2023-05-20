package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(500);
        stage.setWidth(600);
        Pane settingPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/settings.fxml"));
        Scene scene = new Scene(settingPane);
        stage.setScene(scene);
        stage.show();
    }
}
