package view;

import controller.ProfileController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

import static controller.Enums.SuccessfulResponses.LOGIN;
import static controller.Enums.SuccessfulResponses.REGISTER;

public class LoginMenuFXController {
    public TextField username;
    public PasswordField password;
    private final ProfileController profileController = new ProfileController();
    public Button login, register, guest;

    public void register(ActionEvent actionEvent) throws Exception {
        String response = profileController.register(username.getText(), password.getText());
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

    public void login(ActionEvent actionEvent) throws Exception {
        String response = profileController.login(username.getText(), password.getText());
        if (!response.equals(LOGIN.getOutput())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DUMB!!!!!!!!!!!!");
            alert.setHeaderText("ERROR IN " + "LOGIN");
            alert.setContentText(response);
            alert.showAndWait();
        } else {
            User user = User.getUserByName(username.getText());
            MainMenu.user = user;
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.start(LoginMenu.gameStage);
        }
    }

    public void enterAsGuest(ActionEvent actionEvent) {
    }

    public void initialize() {
        username.setPromptText(Output.USERNAME.getOutput());
        password.setPromptText(Output.PASSWORD.getOutput());
        register.setText(Output.REGISTER.getOutput());
        login.setText(Output.LOGIN.getOutput());
        guest.setText(Output.GUEST.getOutput());
    }
}
