package prototype.Scenes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Users.Resident;

public class ResidentRegistrationScene extends Scenes {

    private TextField nameField, lastNameField, birthdayField, addressField, emailField, phoneField, passwordField;
    private Button submitButton, backButton;
    private Text errorMessage; // Text node to display error messages

    public ResidentRegistrationScene(SceneController sceneController) {
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

        this.submitButton = new Button("Submit");
        this.backButton = new Button("Back");

        // Initialize error message Text (initially empty)
        this.errorMessage = new Text();
        this.errorMessage.setStyle("-fx-fill: red;"); // Red color for error message

        this.nameField.setMaxWidth(250);
        this.lastNameField.setMaxWidth(250);
        this.birthdayField.setMaxWidth(250);
        this.addressField.setMaxWidth(250);
        this.emailField.setMaxWidth(250);
        this.phoneField.setMaxWidth(250);
        this.passwordField.setMaxWidth(250);

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
                submitButton,
                backButton,
                errorMessage // Add the error message text to the layout
        );

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

            // Validate birthday: must be in the past and the user must be at least 16 years
            // old
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            LocalDate currentDate = LocalDate.now();

            // Check if the birthday is in the future
            if (birthDate.isAfter(currentDate)) {
                errorMessage.setText("Birthday cannot be in the future.");
                return; // Exit if the date is in the future
            }

            // Check if the user is at least 16 years old
            long age = ChronoUnit.YEARS.between(birthDate, currentDate);
            if (age < 16) {
                errorMessage.setText("You must be at least 16 years old.");
                return; // Exit if the user is under 16
            }

            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisplayName(name + " " + lastName));

            // Block and get the result of the async operation
            UserRecord userRecord = future.get(); // This will block until the operation completes

            String uid = userRecord.getUid(); // Get the unique UID of the user

            // Save user to Firebase Database under a folder named after the UID
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("residents").child(uid);

            Resident resident = new Resident(name, lastName, birthday, address, email, phone, password, "Resident");
            userFolderRef.setValueAsync(resident); // Save resident data under "residents/UID" node

            System.out.println("Resident data saved to Firebase under UID: " + uid);

            // Navigate to login scene after successful registration
            this.sceneController.newScene("login");

        } catch (Exception e) {
            // Catch any other exceptions and display a generic error message
            errorMessage.setText("An error occurred. Please try again.");
            e.printStackTrace();
        }
    }
}
