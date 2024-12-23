package prototype.scenes.general.settings;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.services.QuartiersServices;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.List;
import java.util.Objects;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationSettingsScene extends Scenes {
    private Text aVenir;
    private HBox hBox;
    private Button menu;
    private ComboBox<String> quartiersComboBox;
    private ListView<HBox> selectedQuartiersListView;  // Change the ListView to hold HBoxes
    private List<String> allQuartiers;

    private QuartiersServices quartiersServices;

    public NotificationSettingsScene(SceneController sceneController) {
        super(sceneController);
        this.aVenir = new Text("Option à venir");
        this.hBox = new HBox();
        this.menu = new Button("Retour");

        // Initialize the ComboBox for quartiers
        this.quartiersComboBox = new ComboBox<>();
        this.selectedQuartiersListView = new ListView<>();
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.hBox);
        this.root.setTop(this.menu);
        this.hBox.getChildren().add(this.aVenir);
        this.hBox.setAlignment(Pos.CENTER);
        this.aVenir.setFont(new Font("arial", 20));

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("settings");
        });

        // Get the list of quartiers from the function
        this.quartiersServices = new QuartiersServices();  // Initialize QuartiersServices
        allQuartiers = quartiersServices.quartiersInMontreal();
        quartiersComboBox.getItems().addAll(allQuartiers);

        // Retrieve the user's selected quartiers from Firebase and display them
        Resident currentResident = UserSession.getInstance().getResident();
        if (currentResident != null) {
            List<String> selectedQuartiers = currentResident.getSelectedQuartiers();
            // Remove any "null" or invalid quartiers
            selectedQuartiers.removeIf(quartier -> Objects.equals(quartier, "null") || quartier.isEmpty());

            // Add the quartiers with a delete button next to them
            for (String quartier : selectedQuartiers) {
                HBox quartierHBox = createQuartierHBox(quartier);
                selectedQuartiersListView.getItems().add(quartierHBox);
            }
        }

        // When the user selects a quartier, add it to their selected list and update Firebase
        quartiersComboBox.setOnAction(event -> {
            String selectedQuartier = quartiersComboBox.getValue();
            if (selectedQuartier != null && !selectedQuartier.isEmpty()) {
                // Add the selected quartier to the list
                if (currentResident != null) {
                    List<String> selectedQuartiers = currentResident.getSelectedQuartiers();
                    if (!selectedQuartiers.contains(selectedQuartier)) {
                        selectedQuartiers.add(selectedQuartier);
                        // Update the ListView
                        selectedQuartiersListView.getItems().add(createQuartierHBox(selectedQuartier));
                        // Update Firebase with the new selected quartiers
                        updateSelectedQuartiersInFirebase(currentResident);
                    }
                }
            }
        });

        // Add UI components to the layout
        VBox layout = new VBox(10, quartiersComboBox, selectedQuartiersListView, menu);
        this.root.setCenter(layout);
    }

    // Method to create HBox for each quartier with a delete button
    private HBox createQuartierHBox(String quartier) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text quartierText = new Text(quartier);
        Button deleteButton = new Button("X");

        deleteButton.setOnAction(event -> {
            Resident currentResident = UserSession.getInstance().getResident();
            if (currentResident != null) {
                List<String> selectedQuartiers = currentResident.getSelectedQuartiers();
                selectedQuartiers.remove(quartier);
                // Update the ListView
                selectedQuartiersListView.getItems().remove(hBox);
                // Update Firebase with the new selected quartiers
                updateSelectedQuartiersInFirebase(currentResident);
            }
        });

        hBox.getChildren().addAll(quartierText, deleteButton);
        return hBox;
    }

    // Method to update the selected quartiers in Firebase
    private void updateSelectedQuartiersInFirebase(Resident currentResident) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("residents").child(UserSession.getInstance().getUserId());

        databaseReference.child("selectedQuartiers").setValue(currentResident.getSelectedQuartiers(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Error updating quartiers: " + databaseError.getMessage());
                } else {
                    System.out.println("Quartiers updated successfully");
                }
            }
        });
    }
}
