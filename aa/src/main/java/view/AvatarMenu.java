package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class AvatarMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        File directory = new File("src/main/resources/images/avatars");
        GridPane gridPane = FXMLLoader.load(AvatarMenu.class.getResource("/fxml/avatar.fxml"));
        for (int i = 1; i <= directory.listFiles().length; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(AvatarMenu.class.getResource("/images/avatars/" + i + ".png").toExternalForm()));
            gridPane.getChildren().add(imageView);
        }
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
}
