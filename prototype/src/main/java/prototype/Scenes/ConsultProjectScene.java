package prototype.Scenes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import prototype.Controllers.SceneController;

public class ConsultProjectScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> projectListView;
    private Text projectCountText;
    private ComboBox<String> boroughFilterCombo;
    private ComboBox<String> typeOfWorkFilterCombo;
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    public ConsultProjectScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons and ComboBoxes
        this.backButton = new Button("Retour");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les travaux en cours");
        this.projectListView = new ListView<>();
        this.projectCountText = new Text();
        this.boroughFilterCombo = new ComboBox<>();
        this.typeOfWorkFilterCombo = new ComboBox<>();

        // Fetch data from the API
        fetchProjects();
    }

    public void setScene() {
        // Set up the root layout and title font
        this.root.setCenter(vBox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold;");
        this.projectCountText.setFont(new Font("Arial", 14));

        // Set the layout for the filters
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(new Text("Filtrer par quartier: "), boroughFilterCombo,
                new Text("Filtrer par type de travaux: "), typeOfWorkFilterCombo);

        // Set default values for ComboBoxes
        boroughFilterCombo.setValue("Aucun filtre");
        typeOfWorkFilterCombo.setValue("Aucun filtre");

        // Set the layout for the project list and count
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(projectCountText);

        // Add components to the VBox
        this.vBox.getChildren().addAll(this.title, filterBox, this.projectListView, bottomBox, this.backButton);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(20);

        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double listViewHeight = screenHeight * 0.6;
        this.projectListView.setPrefHeight(listViewHeight);

        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("residentMenu");
        });

        // Set up the ComboBoxes for filtering
        boroughFilterCombo.setOnAction(e -> filterProjects());
        typeOfWorkFilterCombo.setOnAction(e -> filterProjects());
    }

    private void fetchProjects() {
        new Thread(() -> {
            try {
                // Set up the API connection
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the response using org.json
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject result = jsonResponse.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");

                // Update the UI with the fetched data
                Platform.runLater(() -> {
                    // Clear the existing items (in case this is called again)
                    projectListView.getItems().clear();

                    // Create lists for borough and type of work filters
                    boroughFilterCombo.getItems().clear();
                    typeOfWorkFilterCombo.getItems().clear();

                    // Initialize temporary lists for borough and type of work
                    Set<String> boroughs = new HashSet<>();
                    Set<String> typesOfWork = new HashSet<>();

                    // Add "No Filter" option
                    boroughFilterCombo.getItems().add("Aucun filtre");
                    typeOfWorkFilterCombo.getItems().add("Aucun filtre");

                    // Loop through the projects and add them to the ListView
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject project = records.getJSONObject(i);
                        String id = project.optString("id", "Non spécifié");
                        String boroughid = project.optString("boroughid", "Non spécifié");
                        String currentStatus = project.optString("currentstatus", "Non spécifié");
                        String reasonCategory = project.optString("reason_category", "Non spécifié");
                        String submitterCategory = project.optString("submittercategory", "Non spécifié");
                        String organizationName = project.optString("organizationname", "Non spécifié");

                        // Add borough and type of work to the filter lists
                        boroughs.add(boroughid);
                        typesOfWork.add(reasonCategory);

                        // Create a VBox for each project to organize its details
                        VBox projectBox = new VBox();
                        projectBox.setSpacing(10);

                        // Create and add the project label (e.g., Project 1)
                        Text projectLabel = new Text("Project " + (i + 1));
                        projectLabel.setFont(new Font("Arial", 18));
                        projectLabel.setStyle("-fx-font-weight: bold;");

                        // Add each piece of information into the VBox
                        Text projectId = new Text("ID: " + id);
                        Text projectBorough = new Text("Arrondissement: " + boroughid);
                        Text projectStatus = new Text("Statut actuel: " + currentStatus);
                        Text projectReason = new Text("Motif: " + reasonCategory);
                        Text projectCategory = new Text("Catégorie: " + submitterCategory);
                        Text projectOrganization = new Text("Intervenant: " + organizationName);

                        // Set font size and style
                        Font font = new Font("Arial", 16);
                        projectId.setFont(font);
                        projectBorough.setFont(font);
                        projectStatus.setFont(font);
                        projectReason.setFont(font);
                        projectCategory.setFont(font);
                        projectOrganization.setFont(font);

                        // Add the project label and details to the VBox
                        projectBox.getChildren().addAll(projectLabel, projectId, projectBorough, projectStatus,
                                projectReason, projectCategory, projectOrganization);

                        // Add a border to the project VBox
                        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                        // Add the VBox for this project to the ListView
                        projectListView.getItems().add(projectBox);
                    }

                    // Add the available boroughs and types of work to the ComboBoxes
                    boroughFilterCombo.getItems().addAll(boroughs);
                    typeOfWorkFilterCombo.getItems().addAll(typesOfWork);

                    // Update the project count text
                    projectCountText.setText("Total des projets: " + records.length());
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    projectListView.getItems().add(new VBox(new Text("Erreur lors de la récupération des données.")));
                });
            }
        }).start();
    }

    private void filterProjects() {
        String selectedBorough = boroughFilterCombo.getValue();
        String selectedType = typeOfWorkFilterCombo.getValue();

        // Fetch the original list of projects from the API again and filter based on
        // selection
        new Thread(() -> {
            try {
                // Set up the API connection
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the response using org.json
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject result = jsonResponse.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");

                // Update the UI with the filtered data
                Platform.runLater(() -> {
                    projectListView.getItems().clear();

                    int filteredCount = 0; // Counter for filtered projects
                    // Loop through the projects and apply the filters
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject project = records.getJSONObject(i);
                        String boroughid = project.optString("boroughid", "Non spécifié");
                        String reasonCategory = project.optString("reason_category", "Non spécifié");

                        // Check if selectedBorough or selectedType is null before comparison
                        boolean matchesBorough = (selectedBorough == null || selectedBorough.equals("Aucun filtre"))
                                || boroughid.equals(selectedBorough);
                        boolean matchesTypeOfWork = (selectedType == null || selectedType.equals("Aucun filtre"))
                                || reasonCategory.equals(selectedType);

                        if (matchesBorough && matchesTypeOfWork) {
                            VBox projectBox = createProjectBox(project, filteredCount);
                            projectListView.getItems().add(projectBox);
                            filteredCount++; // Increment the counter for each matched project
                        }
                    }

                    // Update the project count text based on the filtered projects
                    projectCountText.setText("Total des projets: " + filteredCount);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    projectListView.getItems().add(new VBox(new Text("Erreur lors de la récupération des données.")));
                });
            }
        }).start();
    }

    private VBox createProjectBox(JSONObject project, int filteredIndex) {
        String id = project.optString("id", "Non spécifié");
        String boroughid = project.optString("boroughid", "Non spécifié");
        String currentStatus = project.optString("currentstatus", "Non spécifié");
        String reasonCategory = project.optString("reason_category", "Non spécifié");
        String submitterCategory = project.optString("submittercategory", "Non spécifié");
        String organizationName = project.optString("organizationname", "Non spécifié");

        VBox projectBox = new VBox();
        projectBox.setSpacing(10);

        // Use filteredIndex for labeling
        Text projectLabel = new Text("Project " + (filteredIndex + 1));
        projectLabel.setFont(new Font("Arial", 18));
        projectLabel.setStyle("-fx-font-weight: bold;");

        Text projectId = new Text("ID: " + id);
        Text projectBorough = new Text("Arrondissement: " + boroughid);
        Text projectStatus = new Text("Statut actuel: " + currentStatus);
        Text projectReason = new Text("Motif: " + reasonCategory);
        Text projectCategory = new Text("Catégorie: " + submitterCategory);
        Text projectOrganization = new Text("Intervenant: " + organizationName);

        Font font = new Font("Arial", 16);
        projectId.setFont(font);
        projectBorough.setFont(font);
        projectStatus.setFont(font);
        projectReason.setFont(font);
        projectCategory.setFont(font);
        projectOrganization.setFont(font);

        projectBox.getChildren().addAll(projectLabel, projectId, projectBorough, projectStatus, projectReason,
                projectCategory, projectOrganization);
        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return projectBox;
    }

}
