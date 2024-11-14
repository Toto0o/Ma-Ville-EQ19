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

public class LoginScene extends Scenes {
    private VBox vBox;
    private Button loginButton;
    private Text usernameText, passwordText;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    public LoginScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.loginButton = new Button("Log In");
        this.statusLabel = new Label();
        this.usernameField = new TextField();
        this.usernameText = new Text("Username");
        this.passwordField = new PasswordField();
        this.passwordText = new Text("Password");
    }

    public void setScene() {
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(usernameText, usernameField, passwordText, passwordField, loginButton,
                statusLabel);
        this.usernameField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.vBox.setSpacing(10);

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
                URL url = new URL(
                        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);

                String jsonInputString = String.format(
                        "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);

                try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(connection.getOutputStream(),
                        "UTF-8")) {
                    writer.write(jsonInputString);
                    writer.flush();
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Parse the response (if needed) and handle successful authentication
                        Platform.runLater(() -> {
                            statusLabel.setText("Login successful!");
                            this.sceneController.newScene("menu");
                        });
                    }
                } else {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream(), "UTF-8"))) {
                        StringBuilder errorResponse = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            errorResponse.append(line);
                        }

                        Platform.runLater(() -> {
                            statusLabel.setText("Login failed: " + errorResponse.toString());
                            usernameField.clear();
                            passwordField.clear();
                        });
                    }
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    public Scene getScene() {
        return this.scene;
    }
}
