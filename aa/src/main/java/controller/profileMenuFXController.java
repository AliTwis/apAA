package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.User;
import view.AvatarMenu;
import view.LoginMenu;
import view.MainMenu;
import view.ProfileMenu;

public class profileMenuFXController {
    public ImageView avatar;
    public Text usernameText;
    private User user = MainMenu.user;

    public void changeUsername(ActionEvent actionEvent) {
        ProfileMenuController.setupChanges(true);
    }

    public void changePassword(ActionEvent actionEvent) {
        ProfileMenuController.setupChanges(false);
    }

    public void back(ActionEvent actionEvent) throws Exception {
        new MainMenu(user).start(LoginMenu.gameStage);

    }

    public void changeAvatar(ActionEvent actionEvent) throws Exception {
        new AvatarMenu(user).start(LoginMenu.gameStage);
    }

    @FXML
    public void initialize() {
        avatar.setImage(new Image(user.getAvatarAddress()));
        usernameText.setText(user.getUsername());
    }

    public void logout(ActionEvent actionEvent) throws Exception {
        new LoginMenu().start(LoginMenu.gameStage);
    }

    public void deleteAccount(ActionEvent actionEvent) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete your account?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) {
            User.getUsers().remove(user);
            MainMenu.user = null;
            User.updateUsers();
            new LoginMenu().start(LoginMenu.gameStage);
        }
    }
}
