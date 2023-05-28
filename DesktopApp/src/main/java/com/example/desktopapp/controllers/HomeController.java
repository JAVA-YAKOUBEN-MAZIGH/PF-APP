package com.example.desktopapp.controllers;

import com.example.desktopapp.Main;
import com.example.desktopapp.Utils;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Text userName;

    public void initialize() {
        userName.setText(Main.user.getUsername());
    }
}
