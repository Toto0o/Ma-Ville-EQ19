package prototype.scenes.general.register;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.users.Utilisateur;
import prototype.controllers.SceneController;
import prototype.controllers.UserController;
import prototype.scenes.Scenes;
import prototype.users.Address;
import prototype.users.Intervenant;
import prototype.users.IntervenantType;

public class RegisterScene extends Scenes {

    private TextField nameField, lastNameField, streetNumberField, streetNameField, postalCodeField, emailField, phoneField, password1Field, password2Field, cityIDField;
    private ComboBox<IntervenantType> intervenantType;
    private DatePicker birthdayPicker;
    private Label status;
    private Button submitButton, intervenantSubmitButton, backButton;

    private Utilisateur user;
    private UserController userController;

    public RegisterScene(SceneController sceneController, UserController userController) {
        
        super(sceneController);
        this.user = userController.getUser();
        this.userController = userController;

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
        this.password1Field = new TextField();
        this.password2Field = new TextField();
        this.cityIDField = new TextField();
        this.intervenantType = new ComboBox<>();
        this.status = new Label();

        this.nameField.setMaxWidth(250);
        this.lastNameField.setMaxWidth(250);
        this.streetNumberField.setMaxWidth(250);
        this.streetNameField.setMaxWidth(250);
        this.postalCodeField.setMaxWidth(250);
        this.emailField.setMaxWidth(250);
        this.phoneField.setMaxWidth(250);
        this.password1Field.setMaxWidth(250);
        this.password2Field.setMaxWidth(250);
        this.cityIDField.setMaxWidth(250);

        this.cityIDField.setVisible(user.isIntervenant());
        this.cityIDField.setManaged(user.isIntervenant());
        

        this.submitButton = new Button("Submit");
        this.intervenantSubmitButton = new Button("Submit");
        this.backButton = new Button("Back");

        this.intervenantSubmitButton.setVisible(user.isIntervenant());
        this.intervenantSubmitButton.setVisible(user.isIntervenant());

        this.intervenantType.getItems().addAll(IntervenantType.values());

        Text cityIDText = new Text("City ID");
        cityIDText.setVisible(user.isIntervenant());
        cityIDText.setManaged(user.isIntervenant()); 

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
                new Text("Password"), this.password1Field,
                new Text("Enter password again"), this.password2Field,
                new Text("Entrez votre identifiant d'intervenant (8 caractères)"), this.cityIDField, // Adding cityID input field
                new Text("Type d'Intervenant"), this.intervenantType,
                this.status,
                this.submitButton,
                this.intervenantSubmitButton,
                this.backButton);

        this.root.setCenter(vBox);

        // Set actions for buttons
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("roleSelection"));
        submitButton.setOnMouseClicked((event) -> { try {
            this.userController.register(
                this.nameField.getText().trim(), 
                this.lastNameField.getText().trim(), 
                this.password1Field.getText().trim(),
                this.password2Field.getText().trim(),
                this.birthdayPicker.getValue().format(this.formatter),
                new Address(
                    this.streetNumberField.getText().trim(),
                    this.streetNameField.getText().trim(), 
                    this.postalCodeField.getText().trim(),
                    null),
                this.phoneField.getText().trim(), 
                this.emailField.getText().trim());
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }});
        
        intervenantSubmitButton.setOnMouseClicked((event) -> { try {
            this.userController.register(
                this.nameField.getText().trim(),
                this.lastNameField.getText().trim(), 
                this.password1Field.getText().trim(),
                this.password2Field.getText().trim(),
                this.birthdayPicker.getValue().format(this.formatter),
                new Address(
                    this.streetNumberField.getText().trim(),
                    this.streetNameField.getText().trim(), 
                    this.postalCodeField.getText().trim(),
                    null),
                this.phoneField.getText().trim(), 
                this.emailField.getText().trim(),
                this.cityIDField.getText().trim(),
                this.intervenantType.getValue());
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }});
    }
}
