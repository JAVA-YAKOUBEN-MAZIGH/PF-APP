package com.example.desktopapp.controllers;

import com.example.desktopapp.Main;
import com.example.desktopapp.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField username_input, email_input, password_input, db_input;

    @FXML
    private Text login_btn, error;

    @FXML
    private Button register_btn;

    @FXML
    public void initialize() {
        if (Utils.get("db") != null) {
            db_input.setText(Utils.get("db"));
        }

        register_btn.setOnAction(this::handleRegisterButtonClick);
        login_btn.setOnMouseClicked(this::handleLoginButtonClicked);
    }

    private void handleLoginButtonClicked(MouseEvent mouseEvent) {
        try {
            Main.goToLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRegisterButtonClick(ActionEvent actionEvent) {
        String username = username_input.getText();
        String email = email_input.getText();
        String password = password_input.getText();
        String db = db_input.getText();

        if (db.length() > 0) {
            Utils.add("db", db);
        } else {
            error.setText("Starpi needed");
            return;
        }

        String requestBodyJson = "{\n\t\"email\": \"" + email + "\",\n\t\"password\": \"" + password + "\",\n\t\"username\": \"" + username + "\"\n}";
        String requestUrl = "http://" + Utils.get("db") + "/api/auth/local/register?populate=*";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Création de la requête POST avec le corps JSON
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setEntity(new StringEntity(requestBodyJson, org.apache.http.entity.ContentType.APPLICATION_JSON));

            // Exécution de la requête et récupération de la réponse
            HttpResponse response = httpClient.execute(httpPost);

            // Vérification du code de réponse HTTP
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                // Traitement de la réponse en cas de succès
                HttpEntity responseEntity = response.getEntity();
                String jsonResponse = EntityUtils.toString(responseEntity);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(jsonResponse);
                String jwt = jsonNode.get("jwt").asText();

                Utils.add("jwt", jwt);

                Main.goToLogin();
            } else {
                // Traitement de la réponse en cas d'erreur
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String errorResponse = EntityUtils.toString(responseEntity);

                    String body = errorResponse.toString();

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode jsonNode = mapper.readTree(body);
                        String message = jsonNode.get("error").get("message").asText();

                        error.setText(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
