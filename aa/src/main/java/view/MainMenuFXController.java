package view;

import controller.ProfileController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.User;

import java.util.ArrayList;

public class MainMenuFXController {
    public ImageView profileImage;
    private User user;

    public void showScoreboard(ActionEvent actionEvent) {
        user = MainMenu.user;
        VBox vBox = new VBox();
        Button button = new Button("back");
        button.setAlignment(Pos.TOP_LEFT);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new MainMenu(user).start(LoginMenu.gameStage);
                } catch (Exception e) {
                    System.out.println("problem happened in main menu line 28.");;
                }
            }
        });
        vBox.getChildren().add(button);
        ArrayList<String> scores = ProfileController.showRankings();
        for (String output : scores) {
            Text text = new Text();
            text.setText(output);
            text.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().add(text);
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
    }
}
