package prototype.scenes.general.consult;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.api.ville.EntravesApi;
import prototype.controllers.SceneController;
import prototype.entraves.Entrave;
import prototype.scenes.Scenes;

public class ConsultEntraveScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> entraveListView;
    private Text entraveCountText;
    private TextField searchField; // Add a TextField for searching
    private EntravesApi entravesLoader;
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    public ConsultEntraveScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.backButton = new Button("Retour au menu");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les entraves");
        this.entraveListView = new ListView<>();
        this.entraveCountText = new Text();
        this.searchField = new TextField(); // Initialize the search field
        this.searchField.setPromptText("Rechercher par ID ou rue");
        this.entravesLoader = new EntravesApi();

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
            this.sceneController.newScene("menu");
        });

        // Set up the search field action
        searchField.setOnAction(e -> searchEntraves());
    }

    private void fetchEntraves() {
       
        // Clear existing list
        entraveListView.getItems().clear();

        // Loop through the entraves and add them to the ListView
        for (Entrave entrave : this.entravesLoader.getEntraves()) {

            // Add the entrave to the ListView
            entraveListView.getItems().add(entrave.afficher());
        }

        // Update the entrave count text
        entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());
    
    }

    private void searchEntraves() {
        String searchQuery = searchField.getText().toLowerCase().trim();

        if (searchQuery.isEmpty()) {
            fetchEntraves(); // If search is empty, fetch all entraves


        } else {
            
            // Clear existing list
            entraveListView.getItems().clear();

            // Loop through the entraves and add them to the ListView
            for (Entrave entrave : this.entravesLoader.getEntraves()) {

                // Check if any of the values match the search query
                if (entrave.getid_request().toLowerCase().contains(searchQuery) ||
                        entrave.getstreetid().toLowerCase().contains(searchQuery) ||
                        entrave.getshortname().toLowerCase().contains(searchQuery) ||
                        entrave.getstreetimpacttype().toLowerCase().contains(searchQuery)) {

                    
                    // Add the entrave to the ListView
                    entraveListView.getItems().add(entrave.afficher());
                }
            }

            // Update the entrave count text
            entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());
                  
        }
    }
}