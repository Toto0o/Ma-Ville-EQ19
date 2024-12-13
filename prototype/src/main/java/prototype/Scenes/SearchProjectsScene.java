package prototype.Scenes;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.service.ProjectServices;

public class SearchProjectsScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> projectListView;
    private Text projectCountText;
    private TextField searchTitleField;
    private TextField searchBoroughField;
    private ComboBox<String> typeFilter;

    public SearchProjectsScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.backButton = new Button("Retour");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Rechercher des projets");
        this.projectListView = new ListView<>();
        this.projectCountText = new Text();
        this.searchTitleField = new TextField();
        this.searchBoroughField = new TextField();
        this.typeFilter = new ComboBox<>();

        // Add filter items (options)
        typeFilter.getItems().addAll(
                "Travaux routiers",
                "Travaux de gaz ou électricité",
                "Construction ou rénovation",
                "Entretien paysager",
                "Travaux liés aux transports en commun",
                "Travaux de signalisation et éclairage",
                "Travaux souterrains",
                "Travaux résidentiel",
                "Entretien urbain",
                "Entretien des réseaux de télécommunication");
        typeFilter.setValue("Tous les types");

        // Mapping of API categories to filter options
        Map<String, String> categoryMapping = new HashMap<>();
        categoryMapping.put("Construction/rénovation avec excavation", "Construction ou rénovation");
        categoryMapping.put("Construction/rénovation sans excavation", "Construction ou rénovation");
        categoryMapping.put("Égouts et aqueducs - Excavation", "Travaux routiers");
        categoryMapping.put("Égouts et aqueducs - Inspection et nettoyage", "Travaux de gaz ou électricité");
        categoryMapping.put("Égouts et aqueducs - Réhabilitation", "Travaux de gaz ou électricité");
        categoryMapping.put("Réseaux routier - Réfection et travaux corrélatifs", "Travaux routiers");
        categoryMapping.put("Feux de signalisation - Ajout/réparation", "Travaux de signalisation et éclairage");
        categoryMapping.put("Toiture - Rénovation", "Construction ou rénovation");
        categoryMapping.put("S-3 Infrastructure souterraine majeure - Puits d'accès", "Travaux souterrains");
        categoryMapping.put("Autre", "Autre");
        categoryMapping.put("Entretien", "Entretien urbain");

        // Fetch data from the API
        fetchProjects();
    }

    public void setScene() {
        this.root.setCenter(vBox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold;");
        this.projectCountText.setFont(new Font("Arial", 14));

        // Set the layout for the search and filter elements
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(new Text("Titre: "), searchTitleField, new Text("Quartier: "),
                searchBoroughField);

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(new Text("Type de travail: "), typeFilter);

        // Set the layout for the project list and count
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(projectCountText);

        // Add components to the VBox
        this.vBox.getChildren().addAll(this.title, searchBox, filterBox, this.projectListView, bottomBox,
                this.backButton);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(20);

        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("residentMenu");
        });

        // Set up the search fields and filter actions
        searchTitleField.setOnAction(e -> searchProjects());
        searchBoroughField.setOnAction(e -> searchProjects());
        typeFilter.setOnAction(e -> searchProjects());
    }

    private void fetchProjects() {
        new Thread(() -> {
            try {
                JSONArray records = ProjectServices.fetchProjects();

                // Update the UI with the fetched data
                Platform.runLater(() -> {
                    // Clear existing list
                    projectListView.getItems().clear();

                    // Loop through the records and add them to the ListView
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject project = records.getJSONObject(i);
                        String title = project.optString("occupancy_name", "Non spécifié");
                        String borough = project.optString("boroughid", "Non spécifié");
                        String type = project.optString("reason_category", "Non spécifié");

                        VBox projectBox = new VBox();
                        projectBox.setSpacing(10);

                        // Create and add the project label (e.g., Project 1)
                        Text projectLabel = new Text("Projet " + (i + 1));
                        projectLabel.setFont(new Font("Arial", 18));
                        projectLabel.setStyle("-fx-font-weight: bold;");

                        // Add all the details into the VBox
                        Text projectTitle = new Text("Titre: " + title);
                        Text projectBorough = new Text("Quartier: " + borough);
                        Text projectType = new Text("Type de travail: " + type);

                        // Add to the VBox
                        projectBox.getChildren().addAll(projectLabel, projectTitle, projectBorough, projectType);

                        // Add a border to the project VBox
                        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                        // Add the project to the ListView
                        projectListView.getItems().add(projectBox);
                    }

                    // Update the project count text
                    projectCountText.setText("Total des projets: " + projectListView.getItems().size());
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    projectListView.getItems().add(new VBox(new Text("Erreur lors de la récupération des données.")));
                });
            }
        }).start();
    }

    private void searchProjects() {
        String titleQuery = searchTitleField.getText().toLowerCase().trim();
        String boroughQuery = searchBoroughField.getText().toLowerCase().trim();
        String typeQuery = mapFilterToApiCategory(typeFilter.getValue());

        if (titleQuery.isEmpty() && boroughQuery.isEmpty() && typeQuery.equals("tous les types")) {
            fetchProjects(); // If no search is provided, fetch all projects
        } else {
            new Thread(() -> {
                try {
                    JSONArray records = ProjectServices.fetchProjects();

                    // Update the UI with the fetched data
                    Platform.runLater(() -> {
                        // Clear existing list
                        projectListView.getItems().clear();

                        // Loop through the records and add them to the ListView
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject project = records.getJSONObject(i);
                            String title = project.optString("occupancy_name", "Non spécifié");
                            String borough = project.optString("boroughid", "Non spécifié");
                            String type = project.optString("reason_category", "Non spécifié");

                            // Check if the project matches all the search or filter criteria
                            boolean matchesTitle = title.toLowerCase().contains(titleQuery) || titleQuery.isEmpty();
                            boolean matchesBorough = borough.toLowerCase().contains(boroughQuery)
                                    || boroughQuery.isEmpty();
                            boolean matchesType = typeQuery.equals("tous les types") || type.equals(typeQuery);

                            // Only add the project if it matches title, borough, and type (when applicable)
                            if (matchesTitle && matchesBorough && matchesType) {
                                VBox projectBox = new VBox();
                                projectBox.setSpacing(10);

                                // Create and add the project label (e.g., Project 1)
                                Text projectLabel = new Text("Projet " + (i + 1));
                                projectLabel.setFont(new Font("Arial", 18));
                                projectLabel.setStyle("-fx-font-weight: bold;");

                                // Add all the details into the VBox
                                Text projectTitleText = new Text("Titre: " + title);
                                Text projectBorough = new Text("Quartier: " + borough);
                                Text projectType = new Text("Type de travail: " + type);

                                // Add to the VBox
                                projectBox.getChildren().addAll(projectLabel, projectTitleText, projectBorough,
                                        projectType);

                                // Add a border to the project VBox
                                projectBox
                                        .setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                                // Add the project to the ListView
                                projectListView.getItems().add(projectBox);
                            }
                        }

                        // Update the project count text
                        projectCountText.setText("Total des projets: " + projectListView.getItems().size());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        projectListView.getItems().add(new VBox(new Text("Erreur lors de la recherche des projets.")));
                    });
                }
            }).start();
        }
    }

    // Map selected filter to actual API category
    private String mapFilterToApiCategory(String filter) {
        switch (filter) {
            case "Travaux routiers":
                return "Réseaux routier - Réfection et travaux corrélatifs";
            case "Travaux de gaz ou électricité":
                return "Égouts et aqueducs - Inspection et nettoyage";
            case "Construction ou rénovation":
                return "Construction/rénovation avec excavation"; // Can be expanded based on different cases
            case "Entretien paysager":
                return "Autre"; // Example, update as necessary
            case "Travaux liés aux transports en commun":
                return "Autre"; // Update as needed
            case "Travaux de signalisation et éclairage":
                return "Feux de signalisation - Ajout/réparation";
            case "Travaux souterrains":
                return "S-3 Infrastructure souterraine majeure - Puits d'accès";
            case "Travaux résidentiel":
                return "Toiture - Rénovation";
            case "Entretien urbain":
                return "Entretien";
            default:
                return "tous les types"; // Default
        }
    }
}
