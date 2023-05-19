package controller;

import model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import static controller.Enums.SuccessfulResponses.*;

public class ProfileController {
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

    public String changeUsername(String username, User user) {
        if (!checkUsername(username) || username.length() == 0) return "Bad username!";
        if (User.getUserByName(username) != null) return "There is a user with this username";
        user.setUsername(username);
        return CHANGE_USERNAME.getOutput();
    }

    public String changePassword(String password, User user) {
        if (!checkPassword(password)) return "Bad password! At least 4 letters including capital and small letters.";
        user.setPassword(password);
        return CHANGE_PASSWORD.getOutput();
    }

    public static ArrayList<String> showRankings() {
        Collections.sort(User.getUsers());
        ArrayList<User> users = User.getUsers();
        ArrayList<String> outputs = new ArrayList<>();
        User user;
        for (int i = 0; i < Math.min(10, users.size()); i++) {
            user = users.get(i);
            int[] lastUpdate = user.getLastUpdate();
            outputs.add((i + 1) + "." + user.getUsername() + ", score: " + user.getScore() + ", last game: " +
                    "date: " + lastUpdate[0] + "/" + lastUpdate[1] + "/" + lastUpdate[2] +
                    " time: " + lastUpdate[3] + ":" + lastUpdate[4]);
        }

        return outputs;
    }

}
