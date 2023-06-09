package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Game;
import model.Level;
import view.LoginMenu;
import view.MainMenu;
import view.Output;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SettingsMenuFXController implements Initializable {
    public RadioButton level1, level2, level3;
    public ChoiceBox<Integer> ballsAmount;
    public Label freeze;
    public ChoiceBox<KeyCode> firstShoot;
    public ChoiceBox<KeyCode> secondShoot;
    public ChoiceBox<KeyCode> ice;
    public Circle firstMonster, secondMonster, thirdMonster;
    public Rectangle firstMap, secondMap, thirdMap, fourthMap;
    public CheckBox mute;
    public Label setting;

    public Button back;
    public Label ballsAmountLabel, pickLevelLabel, firstShootLabel, secondShootLabel, freezeLabel, monsterLabel, mapLabel;
    public CheckBox english;

    public void back(ActionEvent actionEvent) throws Exception {
        if (level1.isSelected()) Game.setLevel(Level.ONE);
        else if (level2.isSelected()) Game.setLevel(Level.TWO);
        else Game.setLevel(Level.THREE);
        Game.setInitialBallsAmount(ballsAmount.getValue());
        HashMap<String, KeyCode> keys = Game.getGameKeys();
        keys.replace("shoot", firstShoot.getValue());
        keys.replace("shoot2", secondShoot.getValue());
        keys.replace("freeze", ice.getValue());
        if (Game.isMute() && !mute.isSelected()) LoginMenu.setMusic(
                LoginMenu.class.getResource("/sound/slowmotion.mp3").toExternalForm()
        );
        Game.setMute(mute.isSelected());
        if (mute.isSelected()) LoginMenu.setMusic("none");
        Output.setIsEnglish(english.isSelected());
        new MainMenu(MainMenu.user).start(LoginMenu.gameStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ballsAmountLabel.setText(Output.BALLS_AMOUNT.getOutput());
        pickLevelLabel.setText(Output.PICK_LEVEL.getOutput());
        firstShootLabel.setText(Output.FIRST_PLAYER_SHOOT.getOutput());
        secondShootLabel.setText(Output.SECOND_PLAYER_SHOOT.getOutput());
        freezeLabel.setText(Output.FREEZE.getOutput());
        monsterLabel.setText(Output.MONSTER.getOutput());
        mapLabel.setText(Output.MAP.getOutput());
        mute.setText(Output.MUTE.getOutput());
        back.setText(Output.BACK.getOutput());
        setting.setText(Output.SETTING.getOutput());
        english.setSelected(Output.isIsEnglish());

        if (Game.getLevel().equals(Level.ONE)) level1.setSelected(true);
        else if (Game.getLevel().equals(Level.TWO)) level2.setSelected(true);
        else level3.setSelected(true);

        mute.setSelected(Game.isMute());
        firstMonster.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/monster1.png").toExternalForm())));
        secondMonster.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/monster4.png").toExternalForm())));
        thirdMonster.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/monster3.png").toExternalForm())));
        firstMap.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/map0.png").toExternalForm())));
        secondMap.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/map1.png").toExternalForm())));
        thirdMap.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/map2.png").toExternalForm())));
        fourthMap.setFill(new ImagePattern(new Image(Game.class.getResource("/images/game/map3.png").toExternalForm())));
        for (int i = 8; i <= 40; i++) ballsAmount.getItems().add(i);
        firstShoot.getItems().addAll(KeyCode.values());
        secondShoot.getItems().addAll(KeyCode.values());
        ice.getItems().addAll(KeyCode.values());
        firstShoot.setValue(KeyCode.SPACE);
        secondShoot.setValue(KeyCode.ENTER);
        ice.setValue(KeyCode.TAB);
        ballsAmount.setValue(10);
    }

    public void chooseMonster1(MouseEvent mouseEvent) {
        Game.setTargetCircleImageAddress(Game.class.getResource("/images/game/monster1.png").toExternalForm());
    }

    public void chooseMonster2(MouseEvent mouseEvent) {
        Game.setTargetCircleImageAddress(Game.class.getResource("/images/game/monster4.png").toExternalForm());
    }

    public void chooseMonster3(MouseEvent mouseEvent) {
        Game.setTargetCircleImageAddress(Game.class.getResource("/images/game/monster3.png").toExternalForm());
    }


    public void chooseFirstMap(MouseEvent mouseEvent) {
        Game.setTargetCircleMap(0);
    }

    public void chooseSecondMap(MouseEvent mouseEvent) {
        Game.setTargetCircleMap(1);
    }

    public void chooseThirdMap(MouseEvent mouseEvent) {
        Game.setTargetCircleMap(2);
    }

    public void chooseFourthMap(MouseEvent mouseEvent) {
        Game.setTargetCircleMap(3);
    }
}
