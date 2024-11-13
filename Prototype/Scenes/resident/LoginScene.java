package prototype.scenes.resident;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.interfaces.CredentialsVerifier;
import prototype.scenes.Scenes;

public class LoginScene extends Scenes {
    private VBox vBox;
    private Button loginButton;

    private Text usernamText, passwordText;
    private TextField usernameField, passwordField;
    private Label error;

    private CredentialsVerifier credentialsVerifier;

    public LoginScene(SceneController sceneController) {
        super(sceneController);
        
        /* this.credentialsVerifier = new CredentialsVerifier(); */
        
        this.vBox = new VBox();
        
        this.loginButton = new Button("Log In");

        this.error = new Label("Username or Password are incorrect. Try again");
        
        this.usernameField = new TextField(null);
        this.usernamText = new Text("Username");
        
        this.passwordField = new TextField(null);
        this.passwordText = new Text("Password");
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(usernamText, usernameField, passwordText, passwordField, loginButton);
        this.usernameField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.vBox.setSpacing(10);
        this.loginButton.setOnMouseClicked((loginAction) -> {
            if (this.usernameField.getText().equals("email@example.com") && this.passwordField.getText().equals("1234")) {
                this.sceneController.newScene("menu");
            }
            else {
                this.vBox.getChildren().add(error);
                this.usernameField.clear();
                this.passwordField.clear();
            }
        });
        this.root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (this.usernameField.getText().equals("email@example.com") && this.passwordField.getText().equals("1234")) {
                    this.sceneController.newScene("menu");
                }
                else {
                    this.vBox.getChildren().add(error);
                    this.usernameField.clear();
                    this.passwordField.clear();
                }
            }
        });
    }

    public Scene getScene() { return this.scene; }

}
