package prototype.scenes.general.consult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class NotificationScene extends Scenes {

    private VBox vbox;
    private Button menu;
    private Text title, subtitle;

    public NotificationScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.menu = new Button("Menu");

        // Setting title and subtitle
        this.title = new Text("Notifications");
        this.subtitle = new Text("Les Projets dans votre quartier (Le Sud-Ouest)");

        vbox.getChildren().addAll(title, subtitle);

        // Fetch projects for Le Sud-Ouest
        fetchProjects();
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.vbox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold;");
        this.subtitle.setFont(new Font("Arial", 20));
        this.subtitle.setStyle("-fx-font-weight: bold;");
        this.root.setTop(this.menu);
        this.vbox.setAlignment(Pos.CENTER);

        // Action for the menu button
        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("residentMenu");
        });
    }

    // Method to fetch and display projects for the borough 'Le Sud-Ouest'
    private void fetchProjects() {
        try {
            // API URL for projects in Le Sud-Ouest
            String urlString = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Ensure UTF-8 encoding for the input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            String responseData = response.toString();
            JSONObject jsonResponse = new JSONObject(responseData);
            JSONObject result = jsonResponse.getJSONObject("result");
            JSONArray records = result.getJSONArray("records");

            // Create a VBox for displaying project details
            VBox projectBox = new VBox(10);
            projectBox.setStyle(
                    "-fx-border-color: #000; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #f9f9f9;");

            int projectCount = 0;

            // Loop through each record and filter based on 'boroughid' equal to 'Le
            // Sud-Ouest'
            for (int i = 0; i < records.length(); i++) {
                JSONObject project = records.getJSONObject(i);
                String boroughId = project.optString("boroughid", "");

                // Check if the project is in Le Sud-Ouest
                if ("Le Sud-Ouest".equalsIgnoreCase(boroughId)) {
                    projectCount++; // Increment the project count

                    // Extracting project details
                    String id = project.optString("id", "No ID");
                    String currentStatus = project.optString("currentstatus", "No Status");
                    String reasonCategory = project.optString("reason_category", "No Category");
                    String submitterCategory = project.optString("submittercategory", "No Submitter");
                    String organizationName = project.optString("organizationname", "No Organization");

                    // Creating the project label with numbering
                    Text projectLabel = new Text("Project " + projectCount);
                    projectLabel.setFont(new Font("Arial", 18));
                    projectLabel.setStyle("-fx-font-weight: bold;");

                    // Displaying the project details in the VBox
                    Text projectDetails = new Text(
                            "Identifiant: " + id + "\n" +
                                    "Arrondissement: " + boroughId + "\n" +
                                    "Statut actuel: " + currentStatus + "\n" +
                                    "Motif: " + reasonCategory + "\n" +
                                    "Catégorie de soumissionnaire: " + submitterCategory + "\n" +
                                    "Organisation: " + organizationName);
                    projectDetails.setFont(new Font("Arial", 16));

                    // Create a border for each project
                    VBox projectItem = new VBox(5, projectLabel, projectDetails);
                    projectItem.setStyle(
                            "-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #fff;");
                    projectBox.getChildren().add(projectItem);
                }
            }

            // Add the total number of projects at the bottom
            Text totalProjects = new Text("Total Projects: " + projectCount);
            totalProjects.setFont(new Font("Arial", 18));
            totalProjects.setStyle("-fx-font-weight: bold;");
            projectBox.getChildren().add(totalProjects);

            // Make the VBox scrollable if the content exceeds the screen size
            ScrollPane scrollPane = new ScrollPane(projectBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: transparent;");

            // Add the ScrollPane to the main VBox
            vbox.getChildren().add(scrollPane);

        } catch (Exception e) {
            System.out.println("Error fetching projects: " + e.getMessage());
            Text errorText = new Text("Erreur lors de la récupération des projets");
            vbox.getChildren().add(errorText);
        }
    }
}
