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
import prototype.api.firebase.UserFirebase;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.controllers.UserController;
import prototype.scenes.Scenes;
import prototype.users.Utilisateur;

/**
 * Scene permettant de s'authentifier
 * 
 * <p> Utilise {@link UserController#}
 * 
 * @param sceneController
 * @param userController
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class LoginScene extends Scenes {

    private VBox vBox;
    private Button loginButton, backButton;
    private Text usernameText, passwordText;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    private UserController userController;

    public LoginScene(SceneController sceneController, UserController userController) {
        super(sceneController);

        this.vBox = new VBox();
        this.loginButton = new Button("Log In");
        this.backButton = new Button("Back");
        this.statusLabel = new Label();
        this.usernameField = new TextField();
        this.usernameText = new Text("Email");
        this.passwordField = new PasswordField();
        this.passwordText = new Text("Password");
        this.userController = userController;
    }

    public void setScene() {
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(usernameText, usernameField, passwordText, passwordField, loginButton, statusLabel, backButton);
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
    /**
     * Méthode pour authentifier un utilisateur selon les éentrées; Les indentifiants
     * sont vérifié par {@link SceneController}
     * 
     * <p> Si l'utilisateur est un intervenant, set le champ boolean a vrai dans @link prototype.controllers.ScenceController 
     */
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
            Utilisateur user = this.userController.login(email, password);
            this.statusLabel.setText("Welcome, " + user.getName());
            
            if (user.isIntervenant()) {
                this.sceneController.setIntervenant(true);
                this.sceneController.newScene("menu");
            }
            else {
                this.statusLabel.setText("Welcome, " + user.getName());
                this.sceneController.newScene("menu");
            }
        }

        catch (Exception e) {
            statusLabel.setText(e.getMessage());
            usernameField.clear();
            passwordField.clear();
            
        }
    }
    

    public Scene getScene() {
        return this.scene;
    }
}
