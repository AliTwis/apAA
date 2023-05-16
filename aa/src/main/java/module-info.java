module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports view;
    opens view to javafx.fxml;

}