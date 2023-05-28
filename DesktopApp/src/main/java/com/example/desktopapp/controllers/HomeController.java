package com.example.desktopapp.controllers;

import com.example.desktopapp.EditBox;
import com.example.desktopapp.Main;
import com.example.desktopapp.Utils;
import com.example.desktopapp.requests.Task;
import com.example.desktopapp.requests.Tasks;
import com.example.desktopapp.requests.User;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HomeController {
    @FXML
    private Text userName, userMoney;

    @FXML
    private VBox todo, done;

    @FXML
    private Button logout, create_task;
    public void initialize() {
        Tasks tasks;

        userName.setText("Hello " + Main.user.getUsername());
        userMoney.setText("Coins : " + Main.user.getMoney().toString());

        logout.setOnAction(event -> {
            Utils.rm("jwt");
            try {
                Main.goToLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        create_task.setOnAction(event -> createTask());

        String requestUrl = "http://" + Utils.get("db") + "/api/tasks?populate=*";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpGet httpGet = new HttpGet(requestUrl);
            httpGet.setHeader("Authorization", "Bearer " + Utils.get("jwt"));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpGet);

            // Vérification du code de réponse HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Traitement de la réponse en cas de succès
                HttpEntity responseEntity = response.getEntity();
                String jsonResponse = EntityUtils.toString(responseEntity);
                Gson gson = new Gson();
                tasks = gson.fromJson(jsonResponse, Tasks.class);

                if (!tasks.getTasks().isEmpty()) {
                    for (Task task: tasks.getTasks()) {
                        if (task.getResolved()) {
                            buildTask(done, task);
                        } else {
                            buildTask(todo, task);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildTask(VBox container, Task task) {

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-border-color: black;");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(100.0);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(100.0);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col3.setMinWidth(10.0);
        col3.setPrefWidth(100.0);

        RowConstraints row1 = new RowConstraints();
        row1.setMaxHeight(32.79277038574219);
        row1.setMinHeight(10.0);
        row1.setPrefHeight(22.569866180419922);
        row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints row2 = new RowConstraints();
        row2.setMaxHeight(47.6915397644043);
        row2.setMinHeight(10.0);
        row2.setPrefHeight(31.25665283203125);
        row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints row3 = new RowConstraints();
        row3.setMaxHeight(57.93488311767578);
        row3.setMinHeight(10.0);
        row3.setPrefHeight(28.24334716796875);
        row3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        Text text1 = new Text(task.getName());
        text1.setFont(new Font(16.0));
        GridPane.setColumnSpan(text1, 2);
        GridPane.setMargin(text1, new Insets(0, 0, 5, 0));

        Text text2 = new Text(task.getDesc());
        GridPane.setColumnSpan(text2, 3);
        GridPane.setRowIndex(text2, 1);

        MenuButton menuButton = new MenuButton("Actions");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem validateItem = new MenuItem("Validate");
        MenuItem deleteItem = new MenuItem("Delete");
        menuButton.getItems().addAll(editItem, validateItem, deleteItem);
        GridPane.setColumnSpan(menuButton, 3);
        GridPane.setRowIndex(menuButton, 2);
        GridPane.setMargin(menuButton, new Insets(0));

        gridPane.getChildren().addAll(text1, text2, menuButton);
        gridPane.setPadding(new Insets(3));

        container.getChildren().add(gridPane);

        Boolean completed = (container == done);

        if (completed) {
            validateItem.setVisible(false);
            editItem.setVisible(false);
        }

        editItem.setOnAction(event -> {
            editTask(task, completed);
        });

        validateItem.setOnAction(event -> {
            editTask(task, true);
        });

        deleteItem.setOnAction(event -> {
            deleteTask(task);
        });
    }

    private void createTask() {
        String[] datas = EditBox.edit(new Task());

        if (datas == null) return;

        String requestBodyJson = "{\n" +
                "  \"data\": {\n" +
                "    \"name\": \"" + datas[0] + "\",\n" +
                "    \"desc\": \"" + datas[1] + "\",\n" +
                "    \"resolved\": false,\n" +
                "    \"user\": " + Main.user.getId() + "\n" +
                "  }\n" +
                "}";

        String requestUrl = "http://" + Utils.get("db") + "/api/tasks/";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Authorization", "Bearer " + Utils.get("jwt"));
            httpPost.setEntity(new StringEntity(requestBodyJson, org.apache.http.entity.ContentType.APPLICATION_JSON));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpPost);

            // Vérification du code de réponse HTTP
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                try {
                    Main.goToApp();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void editTask(Task task, Boolean done) {
        String[] datas;

        if (done) {
            addUserMoney();
            datas = new String[]{task.getName(), task.getDesc()};
        } else {
            datas = EditBox.edit(task);
        }

        String requestBodyJson = "{\"data\": {\"name\": \"" + datas[0] + "\", \"desc\": \"" + datas[1] + "\", \"resolved\": " + done +"}}";
        String requestUrl = "http://" + Utils.get("db") + "/api/tasks/" + task.getId();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpPut httpPost = new HttpPut(requestUrl);
            httpPost.setHeader("Authorization", "Bearer " + Utils.get("jwt"));
            httpPost.setEntity(new StringEntity(requestBodyJson, org.apache.http.entity.ContentType.APPLICATION_JSON));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpPost);

            // Vérification du code de réponse HTTP
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                try {
                    Main.goToApp();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTask(Task task) {
        String requestUrl = "http://" + Utils.get("db") + "/api/tasks/" + task.getId();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpDelete httpPost = new HttpDelete(requestUrl);
            httpPost.setHeader("Authorization", "Bearer " + Utils.get("jwt"));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpPost);

            // Vérification du code de réponse HTTP
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                try {
                    Main.goToApp();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUserMoney() {
        String requestUrl = "http://" + Utils.get("db") + "/api/users/" + Main.user.getId();
        String requestBodyJson = "{\"money\": \"" + (Main.user.getMoney() + 20) + "\"}";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpPut httpGet = new HttpPut(requestUrl);
            httpGet.setHeader("Authorization", "Bearer " + Utils.get("jwt"));
            httpGet.setEntity(new StringEntity(requestBodyJson, org.apache.http.entity.ContentType.APPLICATION_JSON));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpGet);

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
