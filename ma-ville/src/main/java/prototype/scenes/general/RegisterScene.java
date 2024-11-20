package prototype.scenes.general;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.interfaces.CredentialsVerifier;
import prototype.scenes.Scenes;

public class RegisterScene extends Scenes {

    private TextField nameField, lastNameField, birthdayField, addressField, emailField, phoneField, password1Field, password2Field;
    private Text nameText, lastNameText, birthdayText, addressText, emailText, phoneText, password1Text, password2Text;
    private TextField cityIDField;
    private Text cityIDText;
    private Button registerButton, menu;
    private Boolean intervenant;
    protected VBox vBox;
    private CredentialsVerifier credentialsVerifier;

    public RegisterScene(SceneController sceneController, boolean intervenant) {
        
        super(sceneController);

        this.nameField = new TextField(null);
        this.lastNameField = new TextField(null);
        this.birthdayField = new TextField(null);
        this.addressField = new TextField(null);
        this.emailField = new TextField(null);
        this.phoneField = new TextField(null);
        this.password1Field = new TextField(null);
        this.password2Field = new TextField(null);
        this.cityIDField = new TextField(null);

        this.nameText = new Text("Name");
        this.lastNameText = new Text("Last name");
        this.birthdayText = new Text("Birthday (YYYY-MM-DD)");
        this.addressText = new Text("Address");
        this.emailText = new Text("Email");
        this.phoneText = new Text("Phone number");
        this.password1Text = new Text("Enter a password");
        this.password2Text = new Text("Enter password again");
        this.cityIDText = new Text("Enter city ID number");
        this.nameField = new TextField(null);
        this.nameText = new Text("Prenom");

        this.vBox = new VBox();

        this.registerButton = new Button("Register");
        this.menu = new Button("Retour");

        this.intervenant = intervenant;

        /* this.credentialsVerifier = new CredentialsVerifier(); */ 

    }

    @Override
    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(10);
        this.vBox.getChildren().addAll(
            this.nameText,
            this.nameField,
            this.lastNameText,
            this.lastNameField,
            this.birthdayText,
            this.birthdayField,
            this.addressText,
            this.addressField,
            this.emailText,
            this.emailField,
            this.phoneText,
            this.phoneField,
            this.password1Text,
            this.password1Field,
            this.password2Text,
            this.password2Field,
            this.cityIDText,
            this.cityIDField,
            this.registerButton
            );
            this.nameField.setMaxWidth(250);
            this.lastNameField.setMaxWidth(250);
            this.birthdayField.setMaxWidth(250);
            this.addressField.setMaxWidth(250);
            this.emailField.setMaxWidth(250);
            this.phoneField.setMaxWidth(250);
            this.password1Field.setMaxWidth(250);
            this.password2Field.setMaxWidth(250);
            this.cityIDField.setMaxWidth(250);

            if (!this.intervenant) {
                this.vBox.getChildren().removeAll(this.cityIDText, this.cityIDField);
            }

            registerButton.setOnMouseClicked((registerAction) -> {
                try {
                    if (this.credentialsVerifier.verifyRegisterInfo(
                        birthdayField.getText(), 
                        emailField.getText(), 
                        password1Field.getText(),
                        password2Field.getText(),
                        addressField.getText()
                        )) {
                        if (this.intervenant) {
                            this.credentialsVerifier.verifyCityID(cityIDField.getText());
                        }
                        this.sceneController.newScene("menu");}

                } catch (IllegalArgumentException e) {
                    Text error = new Text(e.getMessage());
                    error.setFill(Color.RED);
                    this.vBox.getChildren().add(error);
                }
            });

            this.menu.setOnMouseClicked((retourAction) -> {
                this.sceneController.newScene("launch");
            });

    }

}
