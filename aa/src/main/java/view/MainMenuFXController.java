package view;

import controller.ProfileController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
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
    }

    public void showSettings(ActionEvent actionEvent) throws IOException {
        Pane settingPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/settings.fxml"));
        Scene scene = new Scene(settingPane);
        Stage stage = LoginMenu.gameStage;
        stage.setScene(scene);
        stage.show();
    }
}
