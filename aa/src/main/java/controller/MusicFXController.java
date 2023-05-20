package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import view.LoginMenu;

import java.io.IOException;

public class MusicFXController {
    private static Pane musicPane;
    private static Pane gamePane;
    public static void selectMusic(Pane gamePane) throws IOException {
        MusicFXController.gamePane = gamePane;
        musicPane = FXMLLoader.load(MusicFXController.class.getResource("/fxml/selectMusic.fxml"));
        musicPane.setPrefWidth(450);
        musicPane.setPrefHeight(700);
        musicPane.setStyle("-fx-background-color: 'white';");
        gamePane.getChildren().add(musicPane);
    }
    public RadioButton dreams, creepy, ofeliadream, slowmotion, november, scifi, none;

    public void back(ActionEvent actionEvent) {
        if (dreams.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/dreams.mp3").toExternalForm());
        else if (creepy.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/creepy.mp3").toExternalForm());
        else if (ofeliadream.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/ofeliasdream.mp3").toExternalForm());
        else if (slowmotion.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/slowmotion.mp3").toExternalForm());
        else if (november.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/november.mp3").toExternalForm());
        else if (scifi.isSelected()) LoginMenu.setMusic(LoginMenu.class.getResource("/sound/scifi.mp3").toExternalForm());
        else LoginMenu.setMusic("none");
        gamePane.getChildren().remove(musicPane);
    }

    public void initialize() {
        creepy.setSelected(true);
    }
}
