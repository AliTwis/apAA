package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameFXController;
import view.SinglePlayerGameMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlayerFXController extends GameFXController implements Initializable {
    public Text username;
    public Text level;
    public Text score;
    public Text wind;
    public Text ballsAmount;
    public ProgressBar ice;
    public Text time;

    private int eachPoint = 10;
    private double eachIceProgress = 0.5;
    private int ballsLeft;
    private static Color ballsAmountColor = Color.RED;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SinglePlayerGameMenu.setGameController(this);
    }

    public void setUsername(String name) {
        username.setText(name);
    }

    public void setLevel(String levelString) {
        level.setText(levelString);
    }

    public void setScore(int scoreString) {
        score.setText(Integer.toString(scoreString));
    }

    public void increaseScore() {
        score.setText(Integer.toString(Integer.parseInt(score.getText()) + eachPoint));
    }

    public void setIceProgress(double iceProgress) {
        ice.setProgress(iceProgress);
    }

    public double getIceProgress() {
        return ice.getProgress();
    }

    public void increaseIceProgress() {
        ice.setProgress(Math.min(1, ice.getProgress() + eachIceProgress));
    }

    public void setBallsAmount(int number) {
        ballsAmount.setText(Integer.toString(number));
        ballsLeft = number;
    }

    public void decreaseBall(Color color) {
        ballsLeft--;
        ballsAmount.setText(Integer.toString(ballsLeft));
        ballsAmount.setFill(color);
    }

    public void setWind(int windString) {
        wind.setText(Integer.toString(windString));
    }

    public void setTime(String timeString) {
        time.setText(timeString);
    }

    public int getEachPoint() {
        return eachPoint;
    }

    public Text getUsername() {
        return username;
    }

    public Text getLevel() {
        return level;
    }

    public Text getScore() {
        return score;
    }

    public Text getWind() {
        return wind;
    }

    public Text getBallsAmount() {
        return ballsAmount;
    }

    public ProgressBar getIce() {
        return ice;
    }

    public Text getTime() {
        return time;
    }

    public void setEachPoint(int eachPoint) {
        this.eachPoint = eachPoint;
    }

    public double getEachIceProgress() {
        return eachIceProgress;
    }

    public void setEachIceProgress(double eachIceProgress) {
        this.eachIceProgress = eachIceProgress;
    }

    public int getBallsLeft() {
        return ballsLeft;
    }

    public void setBallsLeft(int ballsLeft) {
        this.ballsLeft = ballsLeft;
        ballsAmount.setText(Integer.toString(ballsLeft));
    }

    public static Color getBallsAmountColor() {
        return ballsAmountColor;
    }

    public static void setBallsAmountColor(Color ballsAmountColor) {
        SinglePlayerFXController.ballsAmountColor = ballsAmountColor;
    }
}
