package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GuideFXController implements Initializable {
    private static Pane guidePane;
    private static Pane gamePane;
    public Label shoot1;
    public Label shoot2;
    public Label freeze;
    public Label pause;
    public Text description;

    public static void showGuide(Pane pane, Scene scene) {
        try {
            guidePane = FXMLLoader.load(GuideFXController.class.getResource("/fxml/guide.fxml"));
        } catch (IOException e) {
            System.out.println("there was a problem with loading guide.fxml");
        }
        gamePane = pane;
        pane.getChildren().add(guidePane);
        guidePane.setLayoutX(0);
        guidePane.setLayoutY(0);
        guidePane.setStyle("-fx-background-color: 'white';");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoot1.setText("First player shoot button: " + Game.getGameKeys().get("shoot"));
        shoot2.setText("Second player shoot button: " + Game.getGameKeys().get("shoot2"));
        freeze.setText("Freeze button: " + Game.getGameKeys().get("freeze"));
        pause.setText("Pause button: " + Game.getGameKeys().get("pause"));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setText("Shoot the balls to the center and make sure there\nwill not be a crash between them!\nYou can freeze " +
                "the monster for a short period of time\nwith your freeze button. Make sure that you won't lose the time!");
    }

    public void back(ActionEvent actionEvent) {
        gamePane.getChildren().remove(guidePane);
    }
}
