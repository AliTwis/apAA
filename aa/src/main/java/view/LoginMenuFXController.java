package view;

import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;

import static controller.Enums.SuccessfulResponses.*;

public class LoginMenuFXController {
    public TextField username;
    public PasswordField password;
    private final LoginController loginController = new LoginController();

    public void register(ActionEvent actionEvent) throws Exception {
        String response = loginController.register(username.getText(), password.getText());
        if (!response.equals(REGISTER.getOutput())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DUMB!!!!!!!!!!!!");
            alert.setHeaderText("ERROR IN " + "REGISTER");
            alert.setContentText(response);
            alert.showAndWait();
        } else {
            new AvatarMenu(User.getUserByName(username.getText())).start(LoginMenu.gameStage);
        }
    }

    public void login(ActionEvent actionEvent) {
        String response = loginController.login(username.getText(), password.getText());
        if (!response.equals(LOGIN.getOutput())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DUMB!!!!!!!!!!!!");
            alert.setHeaderText("ERROR IN " + "LOGIN");
            alert.setContentText(response);
            alert.showAndWait();
        } else {

        }
    }
}
