package controller;

import controller.Enums.SuccessfulResponses;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.User;
import view.LoginMenu;
import view.MainMenu;
import view.MainMenuFXController;
import view.ProfileMenu;

public class ProfileMenuController {
    private static ProfileMenu profileMenu;

    public static ProfileMenu getProfileMenu() {
        return profileMenu;
    }

    public static void setProfileMenu(ProfileMenu profileMenu) {
        ProfileMenuController.profileMenu = profileMenu;
    }

    public static void setupChanges(boolean changeUsername) {
        Pane usernamePane = new Pane();
        int width = 250;
        int height = 150;
        usernamePane.setPrefSize(width, height);
        usernamePane.setLayoutX((profileMenu.getMenuLayout().getWidth() - width) / 2);
        usernamePane.setLayoutY((profileMenu.getMenuLayout().getHeight() - height) / 2);
        usernamePane.setStyle("-fx-background-color: 'grey';");
        Text text = new Text();
        TextField textField = new TextField();
        if (changeUsername) {
            text.setText("Enter your new username:");
            textField.setPromptText("username");
        } else {
            text.setText("Enter your new password:");
            textField.setPromptText("password");
        }
        Button saveButton = new Button("Save");
        Button cancelButton = MainMenuFXController.addCancelButton(text, textField, saveButton);
        usernamePane.getChildren().addAll(text, textField, saveButton, cancelButton);
        profileMenu.getMenuLayout().getChildren().add(usernamePane);
        profileMenu.getMenuLayout().setMaxWidth(profileMenu.getMenuLayout().getWidth());
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user = MainMenu.user;
                String newUsername = textField.getText();
                ProfileController profileController = new ProfileController();
                String output;
                String goodResponse;
                if (changeUsername) {
                    output = profileController.changeUsername(newUsername, user);
                    goodResponse = SuccessfulResponses.CHANGE_USERNAME.getOutput();
                } else {
                    output = profileController.changePassword(newUsername, user);
                    goodResponse = SuccessfulResponses.CHANGE_PASSWORD.getOutput();
                }

                if (!output.equals(goodResponse)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(output);
                    alert.showAndWait();
                } else {
                    profileMenu.getMenuLayout().getChildren().remove(usernamePane);
                    User.updateUsers();
                    try {
                        new ProfileMenu().start(LoginMenu.gameStage);
                    } catch (Exception e) {
                        System.out.println("there was a problem");
                    }
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                profileMenu.getMenuLayout().getChildren().remove(usernamePane);
            }
        });
    }
}
