module aa {
    requires javafx.controls;
    requires javafx.fxml;

    exports view;
    opens view to javafx.fxml;

}