package prototype.Scenes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Projects.Project;

public class IntervenantSubmitProjectScene extends Scenes {

    private Button menu, sendRequest, newRequest;
    private VBox requestBox;
    private TextField titleField, descriptionField, horaireTravauxField, quartiersAffectedField, roadsAffectedField;
    private Text titleText, descriptionText, typeText, quartiersAffected, roadsAffected, startDateText, endDateText,
            horaireTravauxText;
    private ComboBox<String> typeComboBox;
    private String[] types;
    private DatePicker startDatePicker, endDatePicker; // DatePickers for start and end date

    public IntervenantSubmitProjectScene(SceneController sceneController) {
        super(sceneController);
        this.requestBox = new VBox();
        this.menu = new Button("Retour");
        this.sendRequest = new Button("Soumettre projet");
        this.newRequest = new Button("Ajouter une nouvelle requête de travaux");

        this.titleField = new TextField();
        this.descriptionField = new TextField();
        this.horaireTravauxField = new TextField(); // TextField for horaireTravaux
        this.quartiersAffectedField = new TextField(); // TextField for quartiers affected
        this.roadsAffectedField = new TextField(); // TextField for roads affected

        this.titleText = new Text("Titre du projet");
        this.descriptionText = new Text("Description détaillée");
        this.typeText = new Text("Choisissez le type de projet");
        this.quartiersAffected = new Text("Choisissez les quartiers affectés");
        this.roadsAffected = new Text("Choisissez les rues affectées");
        this.startDateText = new Text("Date de début");
        this.endDateText = new Text("Date de fin");
        this.horaireTravauxText = new Text("Horaire des travaux");

        this.types = new String[] { "Travaux routiers", "Travaux de gaz ou électricité", "Construction ou rénovation" };
        this.typeComboBox = new ComboBox<>(FXCollections.observableArrayList(this.types));

        this.startDatePicker = new DatePicker(); // Initialize start DatePicker
        this.endDatePicker = new DatePicker(); // Initialize end DatePicker
    }

    /**
     * Saves a new request to Firestore with the formatted date.
     */
    private void saveRequestToFirebase(String title, String description, String type, String quartiersAffected,
            String roadsAffected, String startDate,
            String endDate, String horaireTravaux, String status) {
        try {
            // Generate a random request ID
            String requestId = java.util.UUID.randomUUID().toString();

            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("projects").child(requestId);

            // Create a Project object with collected data
            Project project = new Project(title, description, type, quartiersAffected, roadsAffected, startDate,
                    endDate, horaireTravaux,
                    status);
            requestFolderRef.setValueAsync(project); // Save request data under "projects/RequestID" node

            System.out.println("Request saved to Firebase under ID: " + requestId);

            // Navigate to a confirmation or dashboard scene after successful submission
            this.sceneController.newScene("dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the scene layout and button actions.
     */
    @Override
    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.newRequest);

        this.requestBox.getChildren().addAll(
                this.titleText,
                this.titleField,
                this.descriptionText,
                this.descriptionField,
                this.typeText,
                this.typeComboBox,
                this.startDateText,
                this.startDatePicker, // Add start DatePicker
                this.endDateText,
                this.endDatePicker, // Add end DatePicker
                this.horaireTravauxText,
                this.horaireTravauxField, // Add TextField for horaireTravaux
                this.quartiersAffected,
                this.quartiersAffectedField, // Add TextField for quartiers affected
                this.roadsAffected,
                this.roadsAffectedField, // Add TextField for roads affected
                this.sendRequest);

        // Set max widths for fields and combo box
        this.titleField.setMaxWidth(250);
        this.descriptionField.setMaxWidth(250);
        this.typeComboBox.setMaxWidth(250);
        this.startDatePicker.setMaxWidth(250); // Set max width for DatePicker
        this.endDatePicker.setMaxWidth(250); // Set max width for DatePicker
        this.horaireTravauxField.setMaxWidth(250);
        this.quartiersAffectedField.setMaxWidth(250);
        this.roadsAffectedField.setMaxWidth(250);

        this.requestBox.setSpacing(20);
        this.requestBox.setAlignment(Pos.CENTER);

        // Show the form when the "Ajouter une nouvelle requête" button is clicked
        this.newRequest.setOnMouseClicked(event -> this.root.setCenter(this.requestBox));

        // Go back to the main menu
        this.menu.setOnMouseClicked(event -> this.sceneController.newScene("intervenantMenu"));

        // Save the request to Firestore
        this.sendRequest.setOnMouseClicked(event -> {
            String title = this.titleField.getText();
            String description = this.descriptionField.getText();
            String type = this.typeComboBox.getValue() != null ? this.typeComboBox.getValue() : "";
            LocalDate startDate = this.startDatePicker.getValue();
            LocalDate endDate = this.endDatePicker.getValue();
            String horaireTravaux = this.horaireTravauxField.getText();
            String quartiersAffected = this.quartiersAffectedField.getText();
            String roadsAffected = this.roadsAffectedField.getText();
            String status = "Prévu";

            // Validate input fields
            if (!title.isEmpty() && !description.isEmpty() && !type.isEmpty() && startDate != null && endDate != null
                    && !horaireTravaux.isEmpty() && !quartiersAffected.isEmpty() && !roadsAffected.isEmpty()
                    && !status.isEmpty()) {

                // Format the dates in YYYYMMDD format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedStartDate = startDate.format(formatter);
                String formattedEndDate = endDate.format(formatter);

                saveRequestToFirebase(title, description, type, quartiersAffected, roadsAffected, formattedStartDate,
                        formattedEndDate, horaireTravaux,
                        status);
            } else {
                System.out.println("Please fill in all fields.");
            }
        });
    }
}
