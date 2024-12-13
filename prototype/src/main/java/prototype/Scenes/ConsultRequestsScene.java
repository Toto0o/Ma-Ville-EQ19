package prototype.Scenes;

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
import prototype.Controllers.SceneController;
import prototype.Requests.Request;
import prototype.service.RequestServices;

public class ConsultRequestsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button applyFiltersButton;
    private List<Request> requestsList;
    private ComboBox<String> typeFilterComboBox;
    private DatePicker dateFilterPicker;
    private TextField quartierSearchField;
    private RequestServices requestFetcher;

    public ConsultRequestsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.backButton = new Button("Back");
        this.applyFiltersButton = new Button("Apply Filters");
        this.typeFilterComboBox = new ComboBox<>();
        this.dateFilterPicker = new DatePicker();
        this.quartierSearchField = new TextField();
        this.quartierSearchField.setPromptText("Rechercher par quartier");
        this.requestFetcher = new RequestServices();
    }

    @Override
    public void setScene() {
        this.root.setTop(this.backButton);
        this.root.setCenter(this.vbox);

        this.backButton.setOnMouseClicked(e -> this.sceneController.newScene("intervenantMenu"));

        createFilters();

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(new Text("Rechercher: "), quartierSearchField);

        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(
                new Text("Filter by Type:"), typeFilterComboBox,
                new Text("Filter by Date:"), dateFilterPicker,
                new Text("Search by Quartier:"), quartierSearchField,
                applyFiltersButton);

        this.vbox.getChildren().addAll(searchBox, filterBox);

        applyFiltersButton.setOnMouseClicked(e -> updateRequestDisplay());

        // Use the RequestService to fetch requests
        fetchRequests();
    }

    private void createFilters() {
        typeFilterComboBox.getItems().addAll("All", "Travaux routiers", "Travaux de gaz ou électricité",
                "Construction ou rénovation");
        typeFilterComboBox.setValue("All");

        typeFilterComboBox.setOnAction(e -> updateRequestDisplay());
        dateFilterPicker.setOnAction(e -> updateRequestDisplay());
    }

    private void fetchRequests() {
        requestFetcher.fetchRequests(fetchedRequests -> {
            requestsList = fetchedRequests;
            updateRequestDisplay();
        });
    }

    private void updateRequestDisplay() {
        Platform.runLater(() -> {
            vbox.getChildren().clear();

            VBox filterBox = new VBox(10);
            filterBox.setAlignment(Pos.CENTER);
            filterBox.getChildren().addAll(
                    new Text("Filter by Type:"), typeFilterComboBox,
                    new Text("Filter by Date:"), dateFilterPicker,
                    new Text("Search by Quartier:"), quartierSearchField,
                    applyFiltersButton);
            vbox.getChildren().add(filterBox);

            String quartierSearchInput = quartierSearchField.getText().trim().toLowerCase();

            // Convert the selected date from the DatePicker to YYYY/MM/DD format if
            // available
            String selectedDate = dateFilterPicker.getValue() != null
                    ? dateFilterPicker.getValue().toString().replace("-", "/")
                    : null;

            List<Request> filteredRequests = requestsList.stream()
                    .filter(r -> (typeFilterComboBox.getValue().equals("All")
                            || r.getType().equals(typeFilterComboBox.getValue())))
                    .filter(r -> (selectedDate == null
                            || formatDate(r.getDate()).equals(selectedDate)))
                    .filter(r -> {
                        String quartier = r.getQuartier() != null ? r.getQuartier().toLowerCase() : "";
                        return quartierSearchInput.isEmpty() || quartier.startsWith(quartierSearchInput);
                    })
                    .collect(Collectors.toList());

            for (Request request : filteredRequests) {
                String formattedDate = formatDate(request.getDate());
                String displayText = "Title: " + request.getTitle() + "\n"
                        + "Description: " + request.getDescription() + "\n"
                        + "Type: " + request.getType() + "\n"
                        + "Date de début: " + formattedDate + "\n"
                        + "Status: " + request.getStatus() + "\n"
                        + "Quartier: " + request.getQuartier();

                Text requestText = new Text(displayText);
                requestText.setStyle("-fx-font-family: 'Arial'; -fx-padding: 10;");

                HBox requestBox = new HBox(10);
                requestBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                requestBox.getChildren().add(requestText);

                vbox.getChildren().add(requestBox);
            }
        });
    }

    private String formatDate(String date) {
        if (date != null && date.length() == 8) {
            return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        }
        return date;
    }

}
