package controller;

import model.User;
import static controller.Enums.SuccessfulResponses.*;

public class LoginController {
    public String register(String username, String password) {
        if (!checkUsername(username)) return "Bad username!";
        if (!checkPassword(password)) return "Bad password! At least 4 letters including capital and small letters.";
        if (User.getUserByName(username) != null) return "There is a user with this username.";
        new User(username, password);
        return REGISTER.getOutput();
    }

    private boolean checkUsername(String username) {
        return !username.contains(" ");
    }

    private boolean checkPassword(String password) {
        if (password.length() < 4 || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*"))
            return false;
        return true;
    }

    public String login(String username, String password) {
        User user = User.getUserByName(username);
        if (user == null) return "There is no user with this username.";
        if (!user.getPassword().equals(password)) return "Password doesn't match.";
        return LOGIN.getOutput();
    }
}
