package prototype.scenes.general.register;

import com.google.auth.oauth2.UserCredentials;
import com.google.firebase.auth.internal.GetAccountInfoResponse;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.users.*;

/**
 * Scene pour authentifier un utilisateur
 *
 * <p>Utilise
 * <ul>
 *     <li>{@link ApiController#register(Resident)} pour les résidents</li>
 *     <li>{@link ApiController#register(Intervenant)} pour les intervenants</li>
 * </ul>
 * </p>
 *
 * <p>Accessible par {@link RoleSelectionScene}</p>
 */
public class RegisterScene extends Scenes {

    private TextField nameField, lastNameField, streetNumberField, streetNameField, postalCodeField, emailField, phoneField, password1Field, password2Field, cityIDField;
    private ComboBox<IntervenantType> intervenantType;
    private DatePicker birthdayPicker;
    private Label status;
    private Button submitButton, intervenantSubmitButton, backButton;

    private boolean intervenant;
    private ApiController apiController;
    private UserCredentialsVerifier userCredentials;

    /**
     * Constructeur
     * @param sceneController
     * @param intervenant true si l'utilisateur désire s'enregistrer comme intervenant (à partir de {@link RoleSelectionScene})
     */
    public RegisterScene(SceneController sceneController, boolean intervenant) {
        super(sceneController);
        this.intervenant = intervenant;
        this.apiController = sceneController.getApiController();
        this.userCredentials = new UserCredentialsVerifier(this.apiController);
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
        this.birthdayPicker.setMinWidth(250);
        this.streetNumberField.setMaxWidth(250);
        this.streetNameField.setMaxWidth(250);
        this.postalCodeField.setMaxWidth(250);
        this.emailField.setMaxWidth(250);
        this.phoneField.setMaxWidth(250);
        this.password1Field.setMaxWidth(250);
        this.password2Field.setMaxWidth(250);
        this.cityIDField.setMaxWidth(250);

        this.cityIDField.setVisible(this.intervenant);
        this.cityIDField.setManaged(this.intervenant);
        

        this.submitButton = new Button("Submit");
        this.intervenantSubmitButton = new Button("Submit");
        this.backButton = new Button("Back");

        this.intervenantSubmitButton.setVisible(this.intervenant);
        this.intervenantSubmitButton.setVisible(this.intervenant);

        this.submitButton.setVisible(!this.intervenant);
        this.submitButton.setManaged(!this.intervenant);

        this.intervenantType.getItems().addAll(IntervenantType.values());
        this.intervenantType.setVisible(this.intervenant);
        this.intervenantType.setManaged(this.intervenant);

        Text cityIDText = labelText("City ID");
        cityIDText.setVisible(this.intervenant);
        cityIDText.setManaged(this.intervenant);
        
        Text intervenantTypeText = labelText("Type d'Intervenant");
        intervenantTypeText.setVisible(this.intervenant);
        intervenantTypeText.setManaged(this.intervenant);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-text-fill: white;");
        vBox.getChildren().addAll(
                labelText("Name"), this.nameField,
                labelText("Last Name"), this.lastNameField,
                labelText("Enter your birhtday"), this.birthdayPicker,
                labelText("Street number"), this.streetNumberField,
                labelText("Street name"), this.streetNameField,
                labelText("Code postal"), this.postalCodeField,
                labelText("Email"), this.emailField,
                labelText("Phone"), this.phoneField,
                labelText("Password"), this.password1Field,
                labelText("Enter password again"), this.password2Field,
                cityIDText, this.cityIDField, // Adding cityID input field
                intervenantTypeText, this.intervenantType,
                this.status,
                this.submitButton,
                this.intervenantSubmitButton,
                this.backButton);

        this.root.setCenter(vBox);

        // Set actions for buttons
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("roleSelection"));
        submitButton.setOnMouseClicked((event) -> { try {
            this.userCredentials.verifyResidentRegister(
                    password1Field.getText().trim(),
                    password2Field.getText().trim(),
                    birthdayPicker.getValue(),
                    emailField.getText().trim(),
                    new Address(
                            streetNumberField.getText().trim(),
                            streetNameField.getText().trim(),
                            postalCodeField.getText().trim()
                    )
            );
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }
        Address address = new Address(
                streetNumberField.getText().trim(),
                streetNameField.getText().trim(),
                postalCodeField.getText().trim()
        );
        try {
            this.apiController.getQuartier(address.getPostalCode());
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }
        Resident resident = new Resident(
                nameField.getText().trim(),
                lastNameField.getText().trim(),
                password1Field.getText().trim(),
                birthdayPicker.getValue().format(this.formatter),
                address,
                phoneField.getText().trim(),
                emailField.getText().trim()
        );
        try {
            this.apiController.register(resident);
        } catch (Exception e) {
            status.setText(e.getMessage());
        }
        });
        
        intervenantSubmitButton.setOnMouseClicked((event) -> {
        try {
            this.userCredentials.verifyIntervenantRegister(
                    password1Field.getText().trim(),
                    password2Field.getText().trim(),
                    cityIDField.getText().trim()
            );
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }
        Address address = new Address(
                streetNumberField.getText().trim(),
                streetNameField.getText().trim(),
                postalCodeField.getText().trim()
        );

        try {
            address.setBorough(this.apiController.getQuartier(address.getPostalCode()));
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }

        try {
            Intervenant intervenant = new Intervenant(
                    nameField.getText().trim(),
                    lastNameField.getText().trim(),
                    password1Field.getText().trim(),
                    birthdayPicker.getValue().format(this.formatter),
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    address,
                    cityIDField.getText().trim()
            );
        } catch (Exception e) {
            this.status.setText(e.getMessage());
        }
        });
    }
}
