package prototype.scenes.general.login;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.api.firebase.UserFireBase;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class LoginScene extends Scenes {
    private VBox vBox;
    private Button loginButton, backButton;
    private Text usernameText, passwordText;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;
    private boolean intervenant;

    private UserFireBase authenticate;

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
        this.authenticate = new UserFireBase();
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

        try {
            if (authenticate.authenticateWithFirebase(email, password)) {
                if (authenticate.getIntervenant()) {
                    statusLabel.setText("Welcome, Intervenant!");
                    this.sceneController.setIntervenant(true);
                    this.sceneController.newScene("menu");
                }
    
                else {
                    statusLabel.setText("Welcome, Resident!");
                    this.sceneController.newScene("menu");
                }
            }

        } catch (Exception e) {
            statusLabel.setText(e.getMessage());
            usernameField.clear();
            passwordField.clear();
            
        }
    }
    

    public Scene getScene() {
        return this.scene;
    }
}
