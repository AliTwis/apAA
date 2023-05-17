module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports view;
    opens view to javafx.fxml;
    opens model to com.google.gson;
    exports controller;
    opens controller to javafx.fxml;

}