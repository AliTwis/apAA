package view;

import controller.ProfileController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ArrayList;

public class MainMenu extends Application {
    static User user;

    public MainMenu(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MainMenu.class.getResource("/fxml/mainMenu.fxml");
        VBox vBox = FXMLLoader.load(url);
//        Button profileButton = new Button("profile", new ImageView(
//                new Image(MainMenu.class.getResource("/images/icons/profileMenu.png").toExternalForm())));

//        Button profileButton = new Button("...!!!...");
//        profileButton.setStyle("-fx-background-size: 20; -fx-background-image: url('../../resources/images/icons/profileMenu.png');");
//        profileButton.setAlignment(Pos.TOP_LEFT);
//        vBox.getChildren().add(0, profileButton);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }
}
