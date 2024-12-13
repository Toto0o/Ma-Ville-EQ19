package prototype.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.service.UserService;

public class IntervenantRegistrationScene extends Scenes {

    private TextField nameField, lastNameField, birthdayField, addressField, emailField, phoneField, passwordField,
            cityIDField;
    private Button submitButton, backButton;

    public IntervenantRegistrationScene(SceneController sceneController) {
        super(sceneController);
        setScene();
    }

    @Override
    public void setScene() {
        // Initialize form fields
        this.nameField = new TextField();
        this.lastNameField = new TextField();
        this.birthdayField = new TextField();
        this.addressField = new TextField();
        this.emailField = new TextField();
        this.phoneField = new TextField();
        this.passwordField = new TextField();
        this.cityIDField = new TextField();

        this.nameField.setMaxWidth(250);
        this.lastNameField.setMaxWidth(250);
        this.birthdayField.setMaxWidth(250);
        this.addressField.setMaxWidth(250);
        this.emailField.setMaxWidth(250);
        this.phoneField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);
        this.cityIDField.setMaxWidth(250);

        this.submitButton = new Button("Submit");
        this.backButton = new Button("Back");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                new Text("Name"), nameField,
                new Text("Last Name"), lastNameField,
                new Text("Birthday (YYYY-MM-DD)"), birthdayField,
                new Text("Address"), addressField,
                new Text("Email"), emailField,
                new Text("Phone"), phoneField,
                new Text("Password"), passwordField,
                new Text("City ID"), cityIDField, // Adding cityID input field
                submitButton,
                backButton);

        this.root.setCenter(vBox);

        // Set actions for buttons
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("roleSelection"));
        submitButton.setOnMouseClicked(event -> saveUserToFirebase());
    }

    private void saveUserToFirebase() {
        try {
            String name = this.nameField.getText().trim();
            String lastName = this.lastNameField.getText().trim();
            String birthday = this.birthdayField.getText().trim();
            String address = this.addressField.getText().trim();
            String email = this.emailField.getText().trim();
            String phone = this.phoneField.getText().trim();
            String password = this.passwordField.getText().trim();
            String cityID = this.cityIDField.getText().trim(); // Get cityID from the input field

            // Call the static method of UserService to save to Firebase and get the UID
            String uid = UserService.saveIntervenantToFirebase(name, lastName, birthday, address, email, phone,
                    password, cityID);

            if (uid != null) {
                System.out.println("Intervenant data saved to Firebase under UID: " + uid);
                this.sceneController.newScene("login"); // Navigate to the login scene after success
            } else {
                // Handle the case where the UID is null (error occurred)
                System.out.println("Error saving Intervenant data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
