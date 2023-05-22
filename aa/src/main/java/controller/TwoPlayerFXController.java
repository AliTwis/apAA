package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;
import view.TwoPlayerGameMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class TwoPlayerFXController extends SinglePlayerFXController implements Initializable {
    public Label username2, username1;
    public Text level;
    public Text score, score1;
    public Text wind;
    public Text ballsAmount, ballsAmount1;
    public ProgressBar ice;
    public Text time;

    private int ballsLeft1 = Game.getInitialBallsAmount();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TwoPlayerGameMenu.setGameController(this);
    }

    public void setUsernames(String name, String name1) {
        username1.setText(name);
        username2.setText(name1);
    }

    public void setScore1(int scoreString) {
        score1.setText(Integer.toString(scoreString));
    }

    public void increaseScore1() {
        score1.setText(Integer.toString(Integer.parseInt(score1.getText()) + super.getEachPoint()));
    }

    public void setBallsAmount1(int number) {
        ballsAmount1.setText(Integer.toString(number));
        ballsLeft1 = number;
    }

    public void decreaseBall1(Color color) {
        ballsLeft1--;
        ballsAmount1.setText(Integer.toString(ballsLeft1));
        ballsAmount1.setFill(color);
    }
}
