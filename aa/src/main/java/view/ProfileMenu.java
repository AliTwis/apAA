package view;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfileMenu extends Application {
    private Pane menuLayout;
    private Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenuController.setProfileMenu(this);
        stage.setWidth(600);
        stage.setHeight(500);
        menuLayout = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/profileMenu.fxml"));
        scene = new Scene(menuLayout);
        stage.setScene(scene);
        stage.show();
    }

    public Pane getMenuLayout() {
        return menuLayout;
    }

    public void setMenuLayout(Pane menuLayout) {
        this.menuLayout = menuLayout;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
