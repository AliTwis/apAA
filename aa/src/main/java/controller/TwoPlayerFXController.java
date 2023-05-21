package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;
import model.GameFXController;

import java.net.URL;
import java.util.ResourceBundle;

public class TwoPlayerFXController extends SinglePlayerFXController implements Initializable {
    public Label username, username1;
    public Text level;
    public Text score, score1;
    public Text wind;
    public Text ballsAmount, ballsAmount1;
    public ProgressBar ice;
    public Text time;

    private int ballsLeft1 = Game.getInitialBallsAmount();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsernames(String name, String name1) {
        username.setText(name);
        username1.setText(name1);
    }

    public void setScore1(int scoreString) {
        score1.setText(Integer.toString(scoreString));
    }

    public void increaseScore1() {
        score1.setText(Integer.toString(Integer.parseInt(score.getText()) + super.getEachPoint()));
    }

    public void setBallsAmount1(int number) {
        ballsAmount.setText(Integer.toString(number));
        ballsLeft1 = number;
    }

    public void decreaseBall1(Color color) {
        ballsLeft1--;
        ballsAmount.setText(Integer.toString(ballsLeft1));
        ballsAmount.setFill(color);
    }
}
