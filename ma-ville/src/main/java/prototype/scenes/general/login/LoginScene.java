package prototype.scenes.general.login;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.services.FirebaseCallback;
import prototype.services.ServiceSession;
import prototype.users.UserSession;
import prototype.users.Utilisateur;

/**
 * Scene permettant de s'authentifier
 * 
 * <p> Utilise {@link ApiController#authenticate(String, String, Label)} </p>
 * <p>Accessible par {@link prototype.scenes.general.menu.MenuScene MenuScene}</p>
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

    private ApiController apiController;

    /**
     * Constructeur
     * @param sceneController
     */
    public LoginScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.loginButton = new Button("Login");
        this.loginButton.setId("login");
        this.backButton = new Button("Retour");
        this.backButton.setId("back");
        this.statusLabel = new Label();
        this.usernameField = new TextField();
        this.usernameField.setId("username");

        this.passwordField = new PasswordField();
        this.passwordField.setId("password");

        this.usernameText = labelText("Email");
        this.passwordText = labelText("Password");
        this.apiController = ServiceSession.getInstance().getController();
    }

    public void setScene() {
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setStyle("-fx-text-fill: white;");
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
     * Méthode pour authentifier un utilisateur selon les indentifiants entrés avec {@link ApiController#authenticate(String, String)}
     *
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
            this.apiController.authenticate(email, password, new FirebaseCallback<Utilisateur>()  {
                @Override
                public void onSuccess() {
                    Platform.runLater(() -> {
                        System.out.println(UserSession.getInstance().getUser().getAddress().getBorough());
                        sceneController.newScene("menu");
                    });
                }

                @Override
                public void onSuccessReturn(Utilisateur sucess) {}

                @Override
                public void onFailureReturn(String message) {
                    Platform.runLater(() -> statusLabel.setText(message));
                }
            });
        }

        catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText(e.getMessage());
        }

    }

}
