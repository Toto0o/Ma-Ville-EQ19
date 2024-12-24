package prototype.scenes.intervenant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.application.Platform;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.notifications.Notification;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.scenes.Scenes;
import prototype.projects.Request;
import prototype.services.FirebaseCallback;
import prototype.services.ServiceSession;
import prototype.users.UserSession;
import prototype.users.Utilisateur;

/**
 * Scene de consultation des requêtes pour les intervenants
 *
 * <p>Charge les requêtes avec {@link ApiController#getRequests()}</p>
 * <p>Permet la soumission de candidature pour chacun des projets</p>
 * <p>Accessible par {@link prototype.scenes.general.menu.MenuScene MenuScene}</p>
 */
public class ConsultRequestsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button applyFiltersButton; // Add button for applying filters
    private ArrayList<Request> requestsList;
    private ComboBox<String> typeFilterComboBox;
    private DatePicker dateFilterPicker;
    private TextField quartierSearchField;
    private ApiController apiController;

    /**
     * Constructeur
     * @param sceneController
     */
    public ConsultRequestsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.vbox.setId("RequestContainer");
        this.requestsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.backButton.setId("menu");
        this.applyFiltersButton = new Button("Apply Filters"); // Initialize the button
        this.typeFilterComboBox = new ComboBox<>();
        this.typeFilterComboBox.setId("typeFilterComboBox");
        this.dateFilterPicker = new DatePicker();
        this.dateFilterPicker.setId("dateFilterPicker");
        this.quartierSearchField = new TextField();
        this.quartierSearchField.setId("quartierSearchField");
        this.quartierSearchField.setPromptText("Rechercher par quartier");
        this.apiController = ServiceSession.getInstance().getController();
    }

    @Override
    public void setScene() {
        // Apply the gradient background to the entire root
        this.root.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");

        // Set the back button at the top
        this.root.setTop(this.backButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createScrollableProjectBox());
        // Set the main content in the center
        this.root.setCenter(borderPane);

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
        this.requestsList = new ArrayList<>();
        this.apiController.getRequests(requestsList, this::updateRequestDisplay);

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

    /**
     * Crée un {@link ScrollPane} pour afficher les projets
     * @return {@link ScrollPane}
     */
    private ScrollPane createScrollableProjectBox() {
        // Make the VBox scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Set max height for scrollable area to 0.6 * screen height
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        this.vbox.setMaxHeight(0.85 * screenHeight);

        // Style and alignment
        this.vbox.setStyle("-fx-padding: 10;");
        this.vbox.setAlignment(Pos.CENTER);
        this.vbox.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");

        return scrollPane;
    }



    /**
     * Affiche les requêtes selon les filtres choisis (ou selon la recherche)
     */
    private void updateRequestDisplay() {
        // Get the screen height and calculate 80% of it
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double vboxHeight = screenHeight * 0.85;

        // Set the height of the VBox to 80% of the screen height

        vbox.setPrefHeight(vboxHeight);

        // Clear previous display
        vbox.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");
        vbox.getChildren().clear();

        // Add the filter options again (search box and filters)
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
                        || r.getType().toString().equals(typeFilterComboBox.getValue()))) // Compare with Type enum
                .filter(r -> {
                    // Check if the date filter is null or matches the request date
                    if (dateFilterPicker.getValue() != null) {
                        String formattedDate = r.getDate().replace("-", "");
                        String selectedDate = dateFilterPicker.getValue().toString().replace("-", "");
                        return formattedDate.equals(selectedDate);
                    }
                    return true; // If no date filter, include all requests
                })
                .filter(r -> {
                    String quartier = r.getQuartier() != null ? r.getQuartier().toLowerCase() : "";
                    // If the quartierSearchInput is empty, we don't filter for quartier
                    if (quartierSearchInput.isEmpty()) {
                        return true; // No filtering if search is empty
                    }
                    // Check if the quartier starts with the search input
                    return quartier.startsWith(quartierSearchInput);
                })
                .collect(Collectors.toList());

        if (filteredRequests.isEmpty()) {
            // Add a placeholder message when no requests match the filters
            VBox placeholderBox = new VBox();
            placeholderBox.setAlignment(Pos.CENTER);
            placeholderBox.getChildren().add(labelText("Aucune requête ne correspond aux filtres."));

            // Set the height of placeholderBox to 90% of the screen height
//            placeholderBox.setMinHeight(vboxHeight);
//            placeholderBox.setMaxHeight(vboxHeight);
//            placeholderBox.setPrefHeight(vboxHeight);
//            placeholderBox.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");


            vbox.getChildren().add(placeholderBox);
        } else {
            // Loop through the filtered requests and add each to the VBox
            for (Request request : filteredRequests) {
                VBox box = new VBox();
                vbox.setAlignment(Pos.CENTER);

                // Create a button for the candidature submission
                Button candidature = new Button("Soumettre sa candidature pour ce projet");
                candidature.setId("candidature");

                // Create the request details including title, description, type, status, quartier, street, and date
                VBox requestDetailsBox = new VBox(5);
                requestDetailsBox.setAlignment(Pos.CENTER);
                requestDetailsBox.setStyle("-fx-text-fill: black;");
                requestDetailsBox.getChildren().addAll(
                        labelText("Titre: " + request.getTitle()),
                        labelText("Date: " + request.getDate()),
                        labelText("Description: " + request.getDescription()),
                        labelText("Type: " + request.getType()),
                        labelText("Status: " + request.getStatus()),
                        labelText("Quartier: " + request.getQuartier()),
                        labelText("Street: " + request.getStreet())
                );

                // Create a container for the candidature button and request details
                box.getChildren().addAll(requestDetailsBox, candidature);

                // Handle the candidature button click event
                candidature.setOnMouseClicked(event -> {
                    box.getChildren().remove(candidature);
                    VBox choose = new VBox(3);
                    DatePicker startDate = new DatePicker();
                    startDate.setId("startDate");
                    DatePicker endDate = new DatePicker();
                    endDate.setId("endDate");
                    Button soumettre = new Button("Soumettre");
                    soumettre.setId("soumettre");
                    Button annuler = new Button("Annuler");
                    annuler.setId("annuler");
                    Text status = labelText("");

                    soumettre.setOnMouseClicked(e -> {
                        if (startDate.getValue() == null || endDate.getValue() == null) {
                            status.setText("Date ne peut pas être vide");
                        } else {
                            Project project = new Project(
                                    request.getTitle(),
                                    request.getDescription(),
                                    request.getType(), // Use the toString of Type enum
                                    request.getQuartier(),
                                    startDate.getValue().format(this.formatter),
                                    endDate.getValue().format(this.formatter),
                                    "8:00 AM 19:59 PM",
                                    Status.PREVU,
                                    UserSession.getInstance().getUserId(),
                                    request.getStreet()
                            );
                            this.apiController.addProject(project);
                            // Send notification on new project added
                            Notification notification = new Notification(
                                    project.getTitle(),
                                    "Un nouveau projet à été approuvé dans votre quartier : " +
                                            project.getQuartiersAffected(), project.getQuartiersAffected()
                            );
                            FirebaseCallback<ArrayList<Utilisateur>> callback = new FirebaseCallback<>() {
                                @Override
                                public void onSuccessReturn(ArrayList<Utilisateur> users) {
                                    Platform.runLater(() -> {
                                        apiController.addNotification(notification);
                                    });
                                }

                                @Override
                                public void onFailureReturn(String message) {
                                }

                                @Override
                                public void onSuccess() {}
                            };
                            this.apiController.getResidents(callback);
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
            }
        }
    }




}