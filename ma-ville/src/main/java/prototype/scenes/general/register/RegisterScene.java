package prototype.scenes.general.register;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.api.firebase.UserFireBase;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.users.Address;
import prototype.users.Intervenant;

public class RegisterScene extends Scenes {

    private TextField nameField, lastNameField, streetNumberField, streetNameField, postalCodeField, emailField, phoneField, passwordField, cityIDField;
    private DatePicker birthdayPicker;
    private Button submitButton, intervenantSubmitButton, backButton;

    private UserFireBase fireBaseLoder;
    private boolean intervenant;

    public RegisterScene(SceneController sceneController, boolean intervenant) {
        
        super(sceneController);
        this.fireBaseLoder = new UserFireBase();
        this.intervenant = intervenant;

    }

    @Override
    public void setScene() {
        // Initialize form fields
        this.nameField = new TextField();
        this.lastNameField = new TextField();
        this.birthdayPicker = new DatePicker();
        this.streetNameField = new TextField();
        this.streetNumberField = new TextField();
        this.postalCodeField = new TextField();
        this.emailField = new TextField();
        this.phoneField = new TextField();
        this.passwordField = new TextField();
        this.cityIDField = new TextField();

        this.nameField.setMaxWidth(250);
        this.lastNameField.setMaxWidth(250);
        this.streetNumberField.setMaxWidth(250);
        this.streetNameField.setMaxWidth(250);
        this.postalCodeField.setMaxWidth(250);
        this.emailField.setMaxWidth(250);
        this.phoneField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.cityIDField.setMaxWidth(250);

        this.cityIDField.setVisible(this.intervenant);
        this.cityIDField.setManaged(this.intervenant);
        

        this.submitButton = new Button("Submit");
        this.intervenantSubmitButton = new Button("Submit");
        this.backButton = new Button("Back");

        this.intervenantSubmitButton.setVisible(this.intervenant);
        this.intervenantSubmitButton.setVisible(this.intervenant);

        Text cityIDText = new Text("City ID");
        cityIDText.setVisible(this.intervenant);
        cityIDText.setManaged(this.intervenant); 

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                new Text("Name"), this.nameField,
                new Text("Last Name"), this.lastNameField,
                new Text("Enter your birhtday"), this.birthdayPicker,
                new Text("Street number"), this.streetNumberField,
                new Text("Street name"), this.streetNameField,
                new Text("Code postal"), this.postalCodeField,
                new Text("Email"), this.emailField,
                new Text("Phone"), this.phoneField,
                new Text("Password"), this.passwordField,
                cityIDText, this.cityIDField, // Adding cityID input field
                this.submitButton,
                this.intervenantSubmitButton,
                this.backButton);

        this.root.setCenter(vBox);

        // Set actions for buttons
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("roleSelection"));
        submitButton.setOnMouseClicked(event -> this.fireBaseLoder.saveUserToFirebase(
            this.nameField.getText(), 
            this.lastNameField.getText(), 
            this.passwordField.getText(),
            this.birthdayPicker.getValue().format(this.formatter),
            this.phoneField.getText(), 
            this.emailField.getText(), 
            new Address(
                this.streetNumberField.getText(),
                this.streetNameField.getText(), 
                this.postalCodeField.getText(),
                null)));
    }

}
