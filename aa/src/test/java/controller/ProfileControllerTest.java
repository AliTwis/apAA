package controller;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {
    ArrayList<User> users = User.getUsers();
    ProfileController profileController = new ProfileController();

    @BeforeEach
    void setup() {
        User user2 = new User("pouria", "pouria");
        user2.setScore(700);
        User user3 = new User("mehran", "Mehran");
        user3.setScore(700);
        User user1 = new User("Ali", "1234");
        user1.setScore(1000);
    }

    @Test
    void checkRankings() {
        ArrayList<String> outputs = profileController.showRankings();
        for (int i = 0; i < outputs.size(); i++) {
            System.out.println(outputs.get(i));
        }
    }

}