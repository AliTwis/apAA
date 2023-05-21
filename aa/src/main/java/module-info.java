module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports view;
    opens model to com.google.gson;
    exports controller;
    opens controller to javafx.fxml;
    opens view to com.google.gson, javafx.fxml;

}