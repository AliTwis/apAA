package view;

import controller.ProfileController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenuFXController {
    public ImageView profileImage;
    public Button newGame, loadGame, twoPlayer, scoreboard, setting, exit;

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
        loadGame.setText(Output.LOAD_GAME.getOutput());
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

    public void loadGame(ActionEvent actionEvent) throws Exception {
        if (MainMenu.user.getSavedGame() == null) return;
        SinglePlayerGameMenu gameMenu = new SinglePlayerGameMenu(MainMenu.user);
        gameMenu.setSavedGame(true);
        gameMenu.start(LoginMenu.gameStage);
    }

    public void startTwoPlayerGame(ActionEvent actionEvent) {
        Pane newGamePane = new Pane();
        MainMenu mainMenu = MainMenu.getMainMenu();
        Pane pane = MainMenu.getMainMenu().getPane();
        int width = 250;
        int height = 150;
        newGamePane.setPrefSize(width, height);
        newGamePane.setLayoutX((pane.getWidth() - width) / 2);
        newGamePane.setLayoutY((pane.getHeight() - height) / 2);
        newGamePane.setStyle("-fx-background-color: 'black';");
        Text text = new Text();
        TextField textField = new TextField();
        textField.getStyleClass().add("chooseSecondPlayer");
        text.setText("Enter your enemy's username");
        textField.setPromptText(Output.USERNAME.getOutput());
        Button acceptButton = new Button("Accept");
        Button cancelButton = addCancelButton(text, textField, acceptButton);;
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User enemy = User.getUserByName(textField.getText());
                if (enemy == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There is no user with this username.");
                    alert.showAndWait();
                }
                else if (enemy.equals(MainMenu.user)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You can't play with yourself!");
                    alert.showAndWait();
                }
                else {
                    try {
                        new TwoPlayerGameMenu(MainMenu.user, enemy).start(LoginMenu.gameStage);
                    } catch (Exception e) {
                        System.out.println("Couldn't start a new game with that player.");
                    }
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    pane.getChildren().remove(newGamePane);
                    new MainMenu(MainMenu.user).start(LoginMenu.gameStage);
                } catch (Exception e) {
                    System.out.println("pain");
                }
            }
        });
        newGamePane.getChildren().addAll(text, textField, acceptButton, cancelButton);
        pane.getChildren().add(newGamePane);
    }

    public static Button addCancelButton(Text text, TextField textField, Button acceptButton) {
        Button cancelButton = new Button("Cancel");
        text.setLayoutX(20);
        text.setLayoutY(45);
        textField.setLayoutX(70);
        textField.setLayoutY(63);
        textField.setAlignment(Pos.CENTER);
        acceptButton.setLayoutX(61);
        acceptButton.setLayoutY(105);
        cancelButton.setLayoutX(135);
        cancelButton.setLayoutY(105);
        return cancelButton;
    }
}
