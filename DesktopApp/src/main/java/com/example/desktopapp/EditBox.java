package com.example.desktopapp;

import com.example.desktopapp.requests.Task;
import com.example.desktopapp.requests.Tasks;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditBox {
    public static String[] edit(Task task) {
        // Création de la fenêtre de la popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setResizable(false);

        // Création du AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // Création des TextField
        TextField textField1 = new TextField(task.getName());
        textField1.setLayoutX(23.0);
        textField1.setLayoutY(29.0);
        textField1.setPrefHeight(36.0);
        textField1.setPrefWidth(362.0);
        textField1.setPromptText("Task name");

        TextField textField2 = new TextField(task.getDesc());
        textField2.setLayoutX(24.0);
        textField2.setLayoutY(92.0);
        textField2.setPrefHeight(92.0);
        textField2.setPrefWidth(362.0);
        textField2.setPromptText("Task description");

        // Création du bouton Save
        Button button = new Button("Save");
        button.setLayoutX(330.0);
        button.setLayoutY(196.0);

        // Action du bouton Save pour fermer la popup et retourner les valeurs des TextField
        button.setOnAction(event -> {
            popupStage.close();
        });

        // Ajout des éléments au AnchorPane
        anchorPane.getChildren().addAll(textField1, textField2, button);

        // Ajout du AnchorPane à la scène
        Scene scene = new Scene(anchorPane, 409, 236);

        // Configuration de la scène dans la popupStage
        popupStage.setScene(scene);

        if (task.getName() == null) {
            popupStage.setTitle("Create a new task !");

        } else {
            popupStage.setTitle("Edit task : " + task.getName());

        }
        // Affichage de la popup
        popupStage.showAndWait();

        // Récupération des valeurs modifiées des TextField
        String modifiedTitle = textField1.getText();
        String modifiedContent = textField2.getText();

        // Retour des valeurs modifiées dans un tableau de String
        return new String[]{modifiedTitle, modifiedContent};
    }
}
