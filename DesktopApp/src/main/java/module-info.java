module com.example.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.databind;

    opens com.example.desktopapp to javafx.fxml;
    exports com.example.desktopapp;
    exports com.example.desktopapp.controllers;
    opens com.example.desktopapp.controllers to javafx.fxml;
    opens com.example.desktopapp.requests to com.google.gson;
}