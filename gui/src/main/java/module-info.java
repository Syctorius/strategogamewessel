module GUI {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires gson;
    requires javax.websocket.api;
    requires messages;
    exports controllers;
    opens controllers;
    exports gui;
    exports websocketshared;
    opens gui;
    opens websocketshared;

}