package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class LoginMenu extends Application {
    public static Stage gameStage;
    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        User.loadSavedData();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(600);
        stage.setHeight(400);
        Media media = new Media(LoginMenu.class.getResource("/sound/slowmotion.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        gameStage = stage;
        BorderPane borderPane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/fxml/loginMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void setMusic(String mediaString) {
        if (mediaString.equals("none")) {
            mediaPlayer.stop();
            return;
        }
        mediaPlayer = new MediaPlayer(new Media(mediaString));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }
}
