package prototype.scenes.general.consult;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.entraves.Entrave;
import prototype.scenes.Scenes;
import prototype.services.ServiceSession;

/**
 * Scene de consultation des entraves
 * 
 * <p> Utilise {@link ApiController#getEntraves()} pour charger les entraves </p>
 *
 * <p>Accessible par {@link prototype.scenes.general.menu.MenuScene MenuScene}</p>
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class ConsultEntraveScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> entraveListView;
    private Text entraveCountText;
    private TextField searchField; // Add a TextField for searching
    private ApiController apiController;

    /**
     * Constructeur
     *
     * <p>Charge les entraves à l'instanciation avec {@link #fetchEntraves()}</p>
     * @param sceneController
     */
    public ConsultEntraveScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.backButton = new Button("Retour au menu");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les entraves");
        this.entraveListView = new ListView<>();
        this.entraveListView.setId("entraveListView");
        this.entraveCountText = new Text();
        this.searchField = new TextField(); // Initialize the search field
        this.searchField.setId("searchField");
        this.searchField.setPromptText("Rechercher par ID ou rue");
        this.apiController = ServiceSession.getInstance().getController();

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

        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double listViewHeight = screenHeight * 0.6;
        this.entraveListView.setPrefHeight(listViewHeight);


        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("menu");
        });

        // Set up the search field action
        searchField.setOnAction(e -> searchEntraves());
    }

    /**
     * Méthode pour charger les entraves
     * <p>Utilise {@link ApiController#getEntraves()} pour charger les entraves</p>
     * <p>Utilise {@link Entrave#afficher()} pour les afficher</p>
     */
    private void fetchEntraves() {
       
        // Clear existing list
        entraveListView.getItems().clear();
        try {
        // Loop through the entraves and add them to the ListView
        for (Entrave entrave : this.apiController.getEntraves()) {

            // Add the entrave to the ListView
            entraveListView.getItems().add(entrave.afficher());
        }

        // Update the entrave count text
        entraveCountText.setText("Total des entraves: " + entraveListView.getItems().size());}
        catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    /**
     * Méthode pour filtrer les entraves selon la recherche de l'utilisateur
     */
    private void searchEntraves() {
        String searchQuery = searchField.getText().toLowerCase().trim();

        if (searchQuery.isEmpty()) {
            fetchEntraves(); // If search is empty, fetch all entraves


        } else {
            
            // Clear existing list
            entraveListView.getItems().clear();
            try {
                // Loop through the entraves and add them to the ListView
                for (Entrave entrave : this.apiController.getEntraves()) {

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
