package prototype.Scenes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform; // Import Platform class
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Users.Resident;
import prototype.Users.UserSession;
import prototype.service.RequestServices;

public class ResidentRequestScene extends Scenes {

    private Button menu, sendRequest, newRequest;
    private VBox requestBox;
    private TextField titleField, descriptionField;
    private Text titleText, descriptionText, typeText, dateText;
    private ComboBox<String> typeComboBox;
    private String[] types;
    private DatePicker datePicker; // New DatePicker for date selection

    public ResidentRequestScene(SceneController sceneController) {
        super(sceneController);

        this.requestBox = new VBox();
        this.menu = new Button("Retour");
        this.sendRequest = new Button("Envoyer la requête");
        this.newRequest = new Button("Ajouter une nouvelle requête de travaux");

        this.titleField = new TextField();
        this.descriptionField = new TextField();

        this.titleText = new Text("Titre du projet");
        this.descriptionText = new Text("Description détaillée");
        this.typeText = new Text("Choisissez le type de projet");
        this.dateText = new Text("Date espérée de début du projet");

        this.types = new String[] { "Travaux routiers", "Travaux de gaz ou électricité", "Construction ou rénovation" };
        this.typeComboBox = new ComboBox<>(FXCollections.observableArrayList(this.types));

        this.datePicker = new DatePicker(); // Initialize DatePicker
    }

    /**
     * Fetch the user's address quartier from Firebase based on their UID from
     * UserSession.
     */

    private void saveRequest(String title, String description, String type, String date) {
        // Access the Resident object from UserSession (already available)
        Resident resident = (Resident) UserSession.getInstance().getUser();

        if (resident != null) {
            // Use the quartier stored in the Resident object
            String quartierName = resident.getQuartier();

            // Run the UI update code on the FX Application thread
            Platform.runLater(() -> {
                // Save the request using RequestServices
                RequestServices.saveRequest(title, description, type, date, quartierName);
                sceneController.newScene("residentMenu"); // Navigate to the dashboard after saving
            });
        } else {
            System.out.println("Resident not found in the UserSession.");
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
                this.dateText,
                this.datePicker, // Add the DatePicker to the layout
                this.sendRequest);

        // Set max widths for fields and combo box
        this.titleField.setMaxWidth(250);
        this.descriptionField.setMaxWidth(250);
        this.typeComboBox.setMaxWidth(250);
        this.datePicker.setMaxWidth(250); // Set max width for DatePicker

        this.requestBox.setSpacing(20);
        this.requestBox.setAlignment(Pos.CENTER);

        // Show the form when the "Ajouter une nouvelle requête" button is clicked
        this.newRequest.setOnMouseClicked(event -> this.root.setCenter(this.requestBox));

        // Go back to the main menu
        this.menu.setOnMouseClicked(event -> this.sceneController.newScene("residentMenu"));

        // Save the request to Firestore when the "Envoyer la requête" button is clicked
        this.sendRequest.setOnMouseClicked(event -> {
            String title = this.titleField.getText();
            String description = this.descriptionField.getText();
            String type = this.typeComboBox.getValue() != null ? this.typeComboBox.getValue() : "";
            LocalDate date = this.datePicker.getValue(); // Get the selected date

            // Validate input fields
            if (!title.isEmpty() && !description.isEmpty() && !type.isEmpty() && date != null) {
                // Format the date in YYYYMMDD format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = date.format(formatter);

                // Fetch the user's quartier and save the request
                saveRequest(title, description, type, formattedDate);
            } else {
                System.out.println("Please fill in all fields.");
            }
        });
    }
}
