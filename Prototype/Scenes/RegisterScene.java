package Prototype.Scenes;

import Prototype.Controllers.SceneController;
import Prototype.Interfaces.CredentialsVerifier;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterScene extends Scenes{

    private TextField nameField, lastNameField, birthdayField, addressField, emailField, phoneField, password1Field, password2Field;
    private Text nameText, lastNameText, birthdayText, addressText, emailText, phoneText, password1Text, password2Text;

    private Button registerButton, menu;

    private VBox vBox;

    private CredentialsVerifier credentialsVerifier;

    private Text errorAddress, errorPassword;

    public RegisterScene(SceneController sceneController) {
        super(sceneController);

        this.nameField = new TextField(null);
        this.lastNameField = new TextField(null);
        this.birthdayField = new TextField(null);
        this.addressField = new TextField(null);
        this.emailField = new TextField(null);
        this.phoneField = new TextField(null);
        this.password1Field = new TextField(null);
        this.password2Field = new TextField(null);

        this.nameText = new Text("Name");
        this.lastNameText = new Text("Last name");
        this.birthdayText = new Text("Birthday (YYYY-MM-DD)");
        this.addressText = new Text("Address");
        this.emailText = new Text("Email");
        this.phoneText = new Text("Phone number");
        this.password1Text = new Text("Enter a password");
        this.password2Text = new Text("Enter password again");

        this.vBox = new VBox();

        this.registerButton = new Button("Register");
        this.menu = new Button("Retour");

        /* this.credentialsVerifier = new CredentialsVerifier(); */

        this.errorAddress = new Text("Address is invalid, please enter a valid address");
        this.errorPassword = new Text("Passwords are not matching... please enter matching passwords"); 

    }

    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
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


            registerButton.setOnMouseClicked((registerAction) -> {
                if (this.credentialsVerifier.verifyAdress(this.addressField.getText())) {
                    if (this.credentialsVerifier.verifiyMatchingPasswords(this.password1Field.getText(), this.password2Field.getText())) {
                        this.sceneController.newScene("menu");
                    }
                    else {
                        this.vBox.getChildren().add(this.errorPassword);
                        this.password1Field.clear();
                        this.password2Field.clear();
                    }
                }
                else {
                    this.vBox.getChildren().add(this.errorAddress);
                    this.addressField.clear();
                }
            });

            this.menu.setOnMouseClicked((retourAction) -> {
                this.sceneController.newScene("launch");
            });

    }

}
