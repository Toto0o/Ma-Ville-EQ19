package prototype.scenes.intervenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.scenes.Scenes;
import prototype.projects.Request;
import prototype.users.UserSession;

public class ConsultRequestsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button applyFiltersButton; // Add button for applying filters
    private ArrayList<Request> requestsList;
    private ComboBox<String> typeFilterComboBox;
    private DatePicker dateFilterPicker;
    private TextField quartierSearchField;
    private ApiController apiController;

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
        this.apiController = sceneController.getApiController();
    }

    @Override
    public void setScene() {
        // Set the back button at the top
        this.root.setTop(this.backButton);

        // Set the main content in the center
        this.root.setCenter(this.vbox);

        // Add the back button's functionality
        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

        // Create filters
        createFilters();

        quartierSearchField.setMaxWidth(250);

        // Set the layout for the search field
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(labelText("Rechercher: "), quartierSearchField);

        // Add filter and request list to the VBox
        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(
                labelText("Filter by Type:"), typeFilterComboBox,
                labelText("Filter by Date:"), dateFilterPicker,
                labelText("Search by Quartier:"), quartierSearchField,
                applyFiltersButton); // Add the apply button

        this.vbox.getChildren().addAll(searchBox, filterBox);

        // Fetch requests from Firebase
        this.requestsList = this.apiController.getRequests();
        updateRequestDisplay();

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

    private void updateRequestDisplay() {
        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear previous display
            vbox.getChildren().clear();

            // Add the filter options again
            VBox filterBox = new VBox(10);
            filterBox.setAlignment(Pos.CENTER);
            filterBox.getChildren().addAll(
                    labelText("Filter by Type:"), typeFilterComboBox,
                    labelText("Filter by Date:"), dateFilterPicker,
                    labelText("Search by Quartier:"), quartierSearchField,
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
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                Button candidature = new Button("Soumettre sa candidature pour ce projet");
                box.getChildren().addAll(request.afficher(), candidature);

                candidature.setOnMouseClicked(event -> {
                    box.getChildren().remove(candidature);
                    VBox choose = new VBox(3);
                    DatePicker startDate = new DatePicker();
                    DatePicker endDate = new DatePicker();
                    Button soumettre = new Button("Soumettre");
                    Button annuler = new Button("Annuler");
                    Text status = labelText("");
                    
                    soumettre.setOnMouseClicked(e -> {
                        if (startDate.getValue() == null || endDate.getValue() == null) {
                            status.setText("Date ne peut pas être vide");
                        }
                        else {
                            Project project = new Project(
                                    request.getTitle(),
                                    request.getDescription(),
                                    request.getType(),
                                    request.getQuartier(),
                                    startDate.getValue().format(this.formatter),
                                    endDate.getValue().format(this.formatter),
                                    "8:00 AM 19:59 PM",
                                    Status.PREVU,
                                    UserSession.getInstance().getUserId(),
                                    request.getStreet()
                            );
                            this.apiController.addProject(project);
                        }
                    });

                    annuler.setOnMouseClicked(e -> {
                        box.getChildren().remove(choose);
                        box.getChildren().add(candidature);
                    });
                    choose.getChildren().addAll(
                            labelText("Date de début"), startDate,
                            labelText("Date de fin"), endDate,
                            soumettre, annuler, status);
                    choose.setAlignment(Pos.CENTER);
                    box.getChildren().addAll(choose);


                });
                
                vbox.getChildren().add(box);
                vbox.setSpacing(20);
                vbox.setMaxWidth(400);
            }

        });
    }

}
