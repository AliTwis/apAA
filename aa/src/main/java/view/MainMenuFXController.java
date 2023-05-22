package view;

import controller.ProfileController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.User;

import java.util.ArrayList;

public class MainMenuFXController {
    public ImageView profileImage;
    public Button newGame, scoreboard, setting, exit;

    public static void generalShowScoreboard(ActionEvent actionEvent) {
        LoginMenu.gameStage.setHeight(400);
        LoginMenu.gameStage.setWidth(600);
        User user = MainMenu.user;
        VBox vBox = new VBox();
        vBox.getStylesheets().add(MainMenu.class.getResource("/css/background.css").toExternalForm());
        vBox.getStyleClass().add("scoreboard");
        vBox.setSpacing(10);
        Button button = new Button("back");
        button.setAlignment(Pos.TOP_LEFT);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new MainMenu(user).start(LoginMenu.gameStage);
                } catch (Exception e) {
                    System.out.println("problem happened in main menu line 28.");
                }
            }
        });
        vBox.getChildren().add(button);
        ArrayList<String> scores = ProfileController.showRankings();
        Label text;
        int i = 1;
        for (String output : scores) {
            text = new Label();
            text.setPrefWidth(600);
            if (i == 1) text.setStyle("-fx-background-color: 'gold';");
            else if (i == 2) text.setStyle("-fx-background-color: 'silver';");
            else if (i == 3) text.setStyle("-fx-background-color: 'brown';");
            else text.setStyle("-fx-background-color: 'grey';");
            text.setText(output);
            text.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().add(text);
            i++;
        }
        Scene scoreboardScene = new Scene(vBox);
        LoginMenu.gameStage.setScene(scoreboardScene);
    }

    public void exit(ActionEvent actionEvent) {
        LoginMenu.gameStage.close();
    }

    public void showProfileMenu(ActionEvent actionEvent) throws Exception {
        new ProfileMenu().start(LoginMenu.gameStage);
    }

    @FXML
    public void initialize() {
        profileImage.setImage(new Image(MainMenu.user.getAvatarAddress()));
        newGame.setText(Output.NEW_GAME.getOutput());
        scoreboard.setText(Output.SCOREBOARD.getOutput());
        setting.setText(Output.SETTING.getOutput());
        exit.setText(Output.EXIT.getOutput());
    }

    public void showSettings(ActionEvent actionEvent) throws Exception {
        new SettingsMenu().start(LoginMenu.gameStage);
    }

    public void startNewGame(ActionEvent actionEvent) throws Exception {
        new SinglePlayerGameMenu(MainMenu.user).start(LoginMenu.gameStage);
    }

    public void showScoreboard(ActionEvent actionEvent) {
        generalShowScoreboard(actionEvent);
    }
}
