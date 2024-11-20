package prototype.scenes.general;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.interfaces.CredentialsVerifier;
import prototype.scenes.Scenes;

public class LoginScene extends Scenes {
    private VBox vBox;
    private Button residentLoginButton, intervenantLoginButton;
    private CheckBox intervenant;

    private Text usernamText, passwordText, cityIDText, error;
    private TextField usernameField, passwordField, cityIDField;

    private CredentialsVerifier credentialsVerifier;

    public LoginScene(SceneController sceneController) {
        super(sceneController);
        
        /* this.credentialsVerifier = new CredentialsVerifier(); */
        
        this.vBox = new VBox();
        
        this.residentLoginButton = new Button("Login");
        this.intervenantLoginButton = new Button("Login ");

        this.error = new Text("Username or Password are incorrect. Try again");
        
        this.usernameField = new TextField(null);
        this.usernamText = new Text("Username");
        
        this.passwordField = new TextField(null);
        this.passwordText = new Text("Password");

        this.cityIDField = new TextField(null);
        this.cityIDText = new Text("Entrez votre identifiant de la vile");

        this.intervenant = new CheckBox("Se connecter en tant qu'intervenant");

    }

    @Override
    public void setScene() {
        this.root.setTop(this.menuButton);
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(
            this.usernamText, 
            this.usernameField, 
            this.passwordText, 
            this.passwordField, 
            this.intervenant,
            this.residentLoginButton,
            this.intervenantLoginButton
        );
        
        this.intervenantLoginButton.setVisible(false);
        this.intervenantLoginButton.setManaged(false);

        this.usernameField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.vBox.setSpacing(10);

        this.intervenant.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            this.intervenantLoginButton.setVisible(isSelected);
            this.intervenantLoginButton.setManaged(isSelected);

            this.residentLoginButton.setVisible(!isSelected);
            this.residentLoginButton.setManaged(!isSelected);
        });


        this.residentLoginButton.setOnMouseClicked((loginAction) -> {
            if (this.usernameField.getText().equals("email@example.com") && this.passwordField.getText().equals("1234")) {
                this.sceneController.newScene("menu");
            }
            else {
                this.vBox.getChildren().add(this.error);
                this.usernameField.clear();
                this.passwordField.clear();
            }
        });

        this.intervenantLoginButton.setOnMouseClicked((intervenantLoginAction) -> {
            if (this.usernameField.getText().equals("email@example.com") && this.passwordField.getText().equals("1234")) {
                this.sceneController.setIntervenant(true);
                this.sceneController.newScene("menu");
                
            }
            else {
                this.vBox.getChildren().add(this.error);
                this.usernameField.clear();
                this.passwordField.clear();
            }
        });

        this.menuButton.setOnMouseClicked((backAction) -> {
            this.sceneController.newScene("launch");
        });


    }

}
