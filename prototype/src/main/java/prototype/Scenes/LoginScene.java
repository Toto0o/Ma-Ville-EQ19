package prototype.Scenes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Users.UserSession;

public class LoginScene extends Scenes {
    private VBox vBox;
    private Button loginButton, backButton;
    private Text usernameText, passwordText;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    public LoginScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.loginButton = new Button("Log In");
        this.backButton = new Button("Back");
        this.statusLabel = new Label();
        this.usernameField = new TextField();
        this.usernameText = new Text("Email");
        this.passwordField = new PasswordField();
        this.passwordText = new Text("Password");
    }

    public void setScene() {
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(usernameText, usernameField, passwordText, passwordField, loginButton,
                statusLabel, backButton);
        this.usernameField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.vBox.setSpacing(10);

        backButton.setOnMouseClicked(backAction -> {
            this.sceneController.newScene("launch");
        });

        loginButton.setOnMouseClicked(loginAction -> handleLogin());
        this.root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String email = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty()) {
            statusLabel.setText("Email cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            statusLabel.setText("Password cannot be empty.");
            return;
        }

        authenticateWithFirebase(email, password);
    }

    private void authenticateWithFirebase(String email, String password) {
        new Thread(() -> {
            try {
                String apiKey = "AIzaSyD95B1nhXm9xVTn_QJjXpQD-FDEqlG6cKM";
                URL authUrl = new URL(
                        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
                HttpURLConnection authConnection = (HttpURLConnection) authUrl.openConnection();
                authConnection.setRequestMethod("POST");
                authConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                authConnection.setDoOutput(true);

                String jsonInputString = String.format(
                        "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);

                try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
                        authConnection.getOutputStream(),
                        "UTF-8")) {
                    writer.write(jsonInputString);
                    writer.flush();
                }

                int authResponseCode = authConnection.getResponseCode();
                if (authResponseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(authConnection.getInputStream(), "UTF-8"))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Extract user ID (localId) from the response
                        String responseBody = response.toString();
                        String userId = extractFieldFromJson(responseBody, "localId");

                        // Save UID in UserSession
                        UserSession.getInstance().setUserId(userId);

                        // Check user role in Firebase Realtime Database
                        checkUserRole(userId);
                    }
                } else {
                    Platform.runLater(() -> {
                        statusLabel.setText("Login failed.");
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    private void checkUserRole(String userId) {
        new Thread(() -> {
            try {
                String databaseUrl = "https://maville-18aa2-default-rtdb.firebaseio.com/";

                // Check the 'residents' folder
                String residentUrl = databaseUrl + "residents/" + userId + ".json";
                System.out.println(residentUrl);
                if (isUserInFolder(residentUrl)) {
                    Platform.runLater(() -> {
                        statusLabel.setText("Welcome, Resident!");
                        this.sceneController.newScene("residentMenu");
                    });
                    return;
                }

                // Check the 'intervenants' folder
                String intervenantUrl = databaseUrl + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    Platform.runLater(() -> {
                        statusLabel.setText("Welcome, Intervenant!");
                        this.sceneController.newScene("intervenantMenu");
                    });
                    return;
                }

                // If user not found in either folder
                Platform.runLater(() -> {
                    statusLabel.setText("Error: User not found in any folder.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    private boolean isUserInFolder(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Check if the response is not null (user exists)
                    return !response.toString().equals("null");
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking folder: " + e.getMessage());
        }
        return false;
    }

    private String extractFieldFromJson(String json, String field) {
        try {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public Scene getScene() {
        return this.scene;
    }
}
