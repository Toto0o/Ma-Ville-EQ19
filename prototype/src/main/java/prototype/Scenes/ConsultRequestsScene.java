package prototype.Scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Requests.Request;

public class ConsultRequestsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button applyFiltersButton; // Add button for applying filters
    private List<Request> requestsList;
    private ComboBox<String> typeFilterComboBox;
    private DatePicker dateFilterPicker;
    private TextField quartierSearchField;

    public ConsultRequestsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.requestsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.applyFiltersButton = new Button("Apply Filters"); // Initialize the button
        this.typeFilterComboBox = new ComboBox<>();
        this.dateFilterPicker = new DatePicker();
        this.quartierSearchField = new TextField();
        this.quartierSearchField.setPromptText("Rechercher par quartier");
    }

    @Override
    public void setScene() {
        // Set the back button at the top
        this.root.setTop(this.backButton);

        // Set the main content in the center
        this.root.setCenter(this.vbox);

        // Add the back button's functionality
        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("intervenantMenu");
        });

        // Create filters
        createFilters();

        quartierSearchField.setMaxWidth(250);

        // Set the layout for the search field
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(new Text("Rechercher: "), quartierSearchField);

        // Add filter and request list to the VBox
        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(
                new Text("Filter by Type:"), typeFilterComboBox,
                new Text("Filter by Date:"), dateFilterPicker,
                new Text("Search by Quartier:"), quartierSearchField,
                applyFiltersButton); // Add the apply button

        this.vbox.getChildren().addAll(searchBox, filterBox);

        // Fetch requests from Firebase
        fetchRequestsFromFirebase();

        // Add the event handler for the apply button
        applyFiltersButton.setOnMouseClicked(e -> updateRequestDisplay());
    }

    private void createFilters() {
        // Populate type filter with possible types
        typeFilterComboBox.getItems().addAll("All", "Travaux routiers", "Travaux de gaz ou électricité",
                "Construction ou rénovation");

        // Set the default value for the filters
        typeFilterComboBox.setValue("All");

        // Add a filter action for ComboBox and DatePicker
        typeFilterComboBox.setOnAction(e -> updateRequestDisplay());
        dateFilterPicker.setOnAction(e -> updateRequestDisplay());
    }

    private void fetchRequestsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference requestFolderRef = database.getReference("requests");

        // Fetch the requests data
        requestFolderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear previous requests data (if any)
                requestsList.clear();

                // Loop through all the children of "requests" node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve each request's details from the snapshot
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String quartier = snapshot.child("quartier").getValue(String.class);

                    // Create a Request object and add it to the list
                    Request request = new Request(title, description, type, date, status, quartier);
                    requestsList.add(request);
                }

                // After fetching requests, update the display
                updateRequestDisplay();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle any error that might occur while fetching data
                System.err.println("Failed to fetch data: " + error.getMessage());
            }
        });
    }

    private void updateRequestDisplay() {
        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear previous display
            vbox.getChildren().clear();

            // Add the filter options again
            VBox filterBox = new VBox(10);
            filterBox.setAlignment(Pos.CENTER);
            filterBox.getChildren().addAll(
                    new Text("Filter by Type:"), typeFilterComboBox,
                    new Text("Filter by Date:"), dateFilterPicker,
                    new Text("Search by Quartier:"), quartierSearchField,
                    applyFiltersButton); // Ensure the button is re-added
            vbox.getChildren().add(filterBox);

            // Get the quartier search input, ensuring it's trimmed for extra spaces
            String quartierSearchInput = quartierSearchField.getText().trim().toLowerCase();

            // Filter the requests based on the selected filters
            List<Request> filteredRequests = requestsList.stream()
                    .filter(r -> (typeFilterComboBox.getValue().equals("All")
                            || r.getType().equals(typeFilterComboBox.getValue())))
                    .filter(r -> (dateFilterPicker.getValue() == null
                            || r.getDate().equals(dateFilterPicker.getValue().toString().replace("-", "")))) // Compare
                                                                                                             // the
                                                                                                             // formatted
                                                                                                             // date
                    .filter(r -> {
                        String quartier = r.getQuartier() != null ? r.getQuartier().toLowerCase() : "";
                        // If the quartierSearchInput is empty, we don't filter for quartier
                        if (quartierSearchInput.isEmpty()) {
                            return true; // No filtering if search is empty
                        }
                        // Exact match check: quartier should exactly match the search input
                        return quartier.equals(quartierSearchInput);
                    })
                    .collect(Collectors.toList());

            // Loop through the filtered requests and add each to the VBox
            for (Request request : filteredRequests) {
                // Format the date to YYYY/MM/DD
                String formattedDate = formatDate(request.getDate());

                String displayText = "Title: " + request.getTitle() + "\n"
                        + "Description: " + request.getDescription() + "\n"
                        + "Type: " + request.getType() + "\n"
                        + "Date: " + formattedDate + "\n"
                        + "Status: " + request.getStatus() + "\n"
                        + "Quartier: " + request.getQuartier();

                Text requestText = new Text(displayText);
                requestText.setStyle("-fx-font-family: 'Arial'; -fx-padding: 10;");

                // Create a bordered HBox for each request
                HBox requestBox = new HBox(10);
                requestBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                requestBox.getChildren().add(requestText);

                // Add the requestBox to the VBox
                vbox.getChildren().add(requestBox);
            }
        });
    }

    // Helper method to format the date string
    private String formatDate(String date) {
        if (date != null && date.length() == 8) {
            // Convert from YYYYMMDD to YYYY/MM/DD
            return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        }
        return date; // Return as is if the format is incorrect
    }
}
