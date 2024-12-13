package prototype.Scenes;

import java.util.List;
import java.util.stream.Collectors;

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
import prototype.service.FetchEntraves;

public class ConsultEntraveScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> entraveListView;
    private Text entraveCountText;
    private TextField searchField;

    public ConsultEntraveScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.backButton = new Button("Retour");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les entraves");
        this.entraveListView = new ListView<>();
        this.entraveCountText = new Text();
        this.searchField = new TextField();
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
                List<JSONObject> entraves = FetchEntraves.fetchEntraves();

                Platform.runLater(() -> {
                    entraveListView.getItems().clear();

                    for (int i = 0; i < entraves.size(); i++) {
                        JSONObject entrave = entraves.get(i);
                        String idRequest = entrave.optString("id_request", "Non spécifié");
                        String streetId = entrave.optString("streetid", "Non spécifié");
                        String shortname = entrave.optString("shortname", "Non spécifié");
                        String streetImpactType = entrave.optString("streetimpacttype", "Non spécifié");

                        VBox entraveBox = new VBox();
                        entraveBox.setSpacing(10);

                        Text entraveLabel = new Text("Entrave " + (i + 1));
                        entraveLabel.setFont(new Font("Arial", 18));
                        entraveLabel.setStyle("-fx-font-weight: bold;");

                        Text entraveIdRequest = new Text("Identifiant du travail: " + idRequest);
                        Text entraveStreetId = new Text("Identifiant de la rue: " + streetId);
                        Text entraveShortname = new Text("Nom de la rue: " + shortname);
                        Text entraveImpactType = new Text("Impact sur la rue: " + streetImpactType);

                        entraveBox.getChildren().addAll(entraveLabel, entraveIdRequest, entraveStreetId,
                                entraveShortname,
                                entraveImpactType);
                        entraveBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                        entraveListView.getItems().add(entraveBox);
                    }

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
        String searchTerm = searchField.getText().toLowerCase(); // Get search term and convert to lowercase for
                                                                 // case-insensitive comparison

        if (searchTerm.isEmpty()) {
            // If the search field is empty, show all entraves again
            fetchEntraves();
        } else {
            // If there's a search term, filter the entraves
            new Thread(() -> {
                try {
                    // Fetch all entraves from the API
                    List<JSONObject> entraves = FetchEntraves.fetchEntraves();

                    // Filter the entraves based on the search term
                    List<JSONObject> filteredEntraves = entraves.stream()
                            .filter(entrave -> entrave.optString("shortname", "").toLowerCase().contains(searchTerm))
                            .collect(Collectors.toList());

                    Platform.runLater(() -> {
                        entraveListView.getItems().clear(); // Clear the ListView

                        // Add the filtered entraves to the ListView
                        for (int i = 0; i < filteredEntraves.size(); i++) {
                            JSONObject entrave = filteredEntraves.get(i);
                            String idRequest = entrave.optString("id_request", "Non spécifié");
                            String streetId = entrave.optString("streetid", "Non spécifié");
                            String shortname = entrave.optString("shortname", "Non spécifié");
                            String streetImpactType = entrave.optString("streetimpacttype", "Non spécifié");

                            VBox entraveBox = new VBox();
                            entraveBox.setSpacing(10);

                            Text entraveLabel = new Text("Entrave " + (i + 1));
                            entraveLabel.setFont(new Font("Arial", 18));
                            entraveLabel.setStyle("-fx-font-weight: bold;");

                            Text entraveIdRequest = new Text("Identifiant du travail: " + idRequest);
                            Text entraveStreetId = new Text("Identifiant de la rue: " + streetId);
                            Text entraveShortname = new Text("Nom de la rue: " + shortname);
                            Text entraveImpactType = new Text("Impact sur la rue: " + streetImpactType);

                            entraveBox.getChildren().addAll(entraveLabel, entraveIdRequest, entraveStreetId,
                                    entraveShortname, entraveImpactType);
                            entraveBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

                            entraveListView.getItems().add(entraveBox);
                        }

                        entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        entraveListView.getItems()
                                .add(new VBox(new Text("Erreur lors de la récupération des données.")));
                    });
                }
            }).start();
        }
    }

}
