package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.util.Random;

public class AvatarMenu extends Application {
    User user;
    public AvatarMenu(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        File directory = new File("src/main/resources/images/avatars");
        VBox vBox = new VBox();
        Text text = new Text();
        vBox.setAlignment(Pos.CENTER);
        text.setText("Hello " + user.getUsername() + " please choose your avatar:");
        vBox.getChildren().add(text);
        GridPane gridPane = FXMLLoader.load(AvatarMenu.class.getResource("/fxml/avatar.fxml"));
        for (int i = 1; i <= directory.listFiles().length; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(AvatarMenu.class.getResource("/images/avatars/" + i + ".png").toExternalForm()));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(imageView, (i - 1) / 3, (i - 1) % 3, 1, 1);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    user.setAvatarAddress(imageView.getImage().getUrl());
                    User.updateUsers();
                    stage.close();
                }
            });
        }
        vBox.getChildren().add(gridPane);
        Button randomAvatar = new Button();
        randomAvatar.setText("random");
        randomAvatar.setAlignment(Pos.CENTER);
        randomAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Random random = new Random();
                int randomPic = random.nextInt(directory.listFiles().length);
                ImageView imageView = (ImageView) gridPane.getChildren().get(randomPic);
                user.setAvatarAddress(imageView.getImage().getUrl());
                User.updateUsers();
                stage.close();
            }
        });
        vBox.getChildren().add(randomAvatar);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }
}
