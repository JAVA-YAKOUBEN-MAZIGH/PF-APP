package com.example.desktopapp;

import com.example.desktopapp.requests.User;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main extends Application {
    static Stage window;

    public static User user = new User();
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;

        goToLogin();
    }
    public static void main(String[] args) {
        launch();
    }

    public static void goToLogin() throws IOException {
        if (Utils.get("jwt") != null) {
            goToApp();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Login !");

        window.setScene(scene);
        window.show();
    }

    public static void goToRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Register !");

        window.setScene(scene);
        window.show();
    }

    public static void goToApp() throws IOException {
        loadUser();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Hello !");

        window.setScene(scene);
        window.show();
    }

    private static void loadUser() {
        String requestUrl = "http://" + Utils.get("db") + "/api/users/me";

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
                user = gson.fromJson(jsonResponse, User.class);
            } else if (statusCode == 401) {
                Utils.rm("jwt");
                goToLogin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}