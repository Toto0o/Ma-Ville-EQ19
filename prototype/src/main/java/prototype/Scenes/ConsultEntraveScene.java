package prototype.Scenes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class ConsultEntraveScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> entraveListView;
    private Text entraveCountText;
    private TextField searchField;
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    public ConsultEntraveScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.backButton = new Button("Retour");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les entraves");
        this.entraveListView = new ListView<>();
        this.entraveCountText = new Text();
        this.searchField = new TextField(); // Initialize the search field
        this.searchField.setPromptText("Rechercher par ID ou rue");

        // Fetch data from the API
        fetchEntraves();
    }

    public void setScene() {
        this.root.setCenter(vBox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold;");
        this.entraveCountText.setFont(new Font("Arial", 14));

        // Set the layout for the search field
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(new Text("Rechercher: "), searchField);

        // Set the layout for the entrave list and count
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(entraveCountText);

        // Add components to the VBox
        this.vBox.getChildren().addAll(this.title, searchBox, this.entraveListView, bottomBox, this.backButton);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(20);

        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("residentMenu");
        });

        // Set up the search field action
        searchField.setOnAction(e -> searchEntraves());
    }

    private void fetchEntraves() {
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
                    // Clear existing list
                    entraveListView.getItems().clear();

                    // Loop through the entraves and add them to the ListView
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject entrave = records.getJSONObject(i);
                        String idRequest = entrave.optString("id_request", "Non spécifié");
                        String streetId = entrave.optString("streetid", "Non spécifié");
                        String shortname = entrave.optString("shortname", "Non spécifié");
                        String streetImpactType = entrave.optString("streetimpacttype", "Non spécifié");

                        VBox entraveBox = new VBox();
                        entraveBox.setSpacing(10);

                        // Create and add the entrave label (e.g., Entrave 1)
                        Text entraveLabel = new Text("Entrave " + (i + 1));
                        entraveLabel.setFont(new Font("Arial", 18));
                        entraveLabel.setStyle("-fx-font-weight: bold;");

                        // Add the selected details into the VBox
                        Text entraveIdRequest = new Text("Identifiant du travail: " + idRequest);
                        Text entraveStreetId = new Text("Identifiant de la rue: " + streetId);
                        Text entraveShortname = new Text("Nom de la rue: " + shortname);
                        Text entraveImpactType = new Text("Impact sur la rue: " + streetImpactType);

                        // Add to the VBox
                        entraveBox.getChildren().addAll(entraveLabel, entraveIdRequest, entraveStreetId,
                                entraveShortname, entraveImpactType);

                        // Add a border to the entrave VBox
                        entraveBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                        // Add the entrave to the ListView
                        entraveListView.getItems().add(entraveBox);
                    }

                    // Update the entrave count text
                    entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    entraveListView.getItems().add(new VBox(new Text("Erreur lors de la récupération des données.")));
                });
            }
        }).start();
    }

    private void searchEntraves() {
        String searchQuery = searchField.getText().toLowerCase().trim();

        if (searchQuery.isEmpty()) {
            fetchEntraves(); // If search is empty, fetch all entraves
        } else {
            new Thread(() -> {
                try {
                    // Set up the API connection
                    URL url = new URL(API_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "UTF-8"));
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
                        // Clear existing list
                        entraveListView.getItems().clear();

                        // Loop through the entraves and add them to the ListView
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject entrave = records.getJSONObject(i);
                            String idRequest = entrave.optString("id_request", "Non spécifié");
                            String streetId = entrave.optString("streetid", "Non spécifié");
                            String shortname = entrave.optString("shortname", "Non spécifié");
                            String streetImpactType = entrave.optString("streetimpacttype", "Non spécifié");

                            // Check if any of the values match the search query
                            if (idRequest.toLowerCase().contains(searchQuery) ||
                                    streetId.toLowerCase().contains(searchQuery) ||
                                    shortname.toLowerCase().contains(searchQuery) ||
                                    streetImpactType.toLowerCase().contains(searchQuery)) {

                                VBox entraveBox = new VBox();
                                entraveBox.setSpacing(10);

                                // Create and add the entrave label (e.g., Entrave 1)
                                Text entraveLabel = new Text("Entrave " + (i + 1));
                                entraveLabel.setFont(new Font("Arial", 18));
                                entraveLabel.setStyle("-fx-font-weight: bold;");

                                // Add all the details into the VBox
                                Text entraveIdRequest = new Text("Identifiant du travail: " + idRequest);
                                Text entraveStreetId = new Text("Identifiant de la rue: " + streetId);
                                Text entraveShortname = new Text("Nom de la rue: " + shortname);
                                Text entraveImpactType = new Text("Impact sur la rue: " + streetImpactType);

                                // Add to the VBox
                                entraveBox.getChildren().addAll(entraveLabel, entraveIdRequest, entraveStreetId,
                                        entraveShortname, entraveImpactType);

                                // Add a border to the entrave VBox
                                entraveBox
                                        .setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                                // Add the entrave to the ListView
                                entraveListView.getItems().add(entraveBox);
                            }
                        }

                        // Update the entrave count text
                        entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
