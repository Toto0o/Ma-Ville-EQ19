package Scenes;

import Controllers.SceneController;
import Interfaces.CredentialsVerifier;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginScene {

    private BorderPane root;
    private Scene scene;
    private VBox vBox;
    private Button loginButton;

    private Text usernamText, passwordText;
    private TextField usernameField, passwordField;
    private Label error;

    private SceneController sceneController;

    private CredentialsVerifier credentialsVerifier;

    public LoginScene(SceneController sceneController) {
        this.sceneController = sceneController;
        this.credentialsVerifier = new CredentialsVerifier();
        this.root = new BorderPane();
        this.scene = new Scene(this.root);
        this.vBox = new VBox();
        
        this.loginButton = new Button("Log In");

        this.error = new Label("Username or Password are incorrect. Try again");
        
        this.usernameField = new TextField(null);
        this.usernamText = new Text("Username");
        
        this.passwordField = new TextField(null);
        this.passwordText = new Text("Password");
    }

    public void setScene() {
        this.root.setCenter(this.vBox);

        this.vBox.getChildren().addAll(usernamText, usernameField, passwordText, passwordField, loginButton);

        this.loginButton.setOnMouseClicked((loginAction) -> {
            if (this.credentialsVerifier.verify(this.usernameField.getText(), this.passwordField.getText())) {
                this.sceneController.newScene("menu");
            }
            else {
                this.vBox.getChildren().add(error);
                this.usernameField.clear();
                this.passwordField.clear();
            }
        });
    }

    public Scene getScene() { return this.scene; }

}
