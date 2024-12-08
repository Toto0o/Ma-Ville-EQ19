package prototype.scenes.general.consult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import prototype.controllers.SceneController;
import prototype.controllers.ProjectController;
import prototype.scenes.Scenes;
import prototype.projects.Project;


public class ConsultProjectScene extends Scenes {

    private VBox vBox;
    private Button backButton;
    private Text title;
    private ListView<VBox> projectListView;
    private Text projectCountText;
    private ComboBox<String> boroughFilterCombo;
    private ComboBox<String> typeOfWorkFilterCombo;

    private Set<String> borough, typesOfWork;

    private ProjectController projectController;

    private boolean intervenant;

    private ArrayList<Project> projects;

    public ConsultProjectScene(SceneController sceneController, boolean intervenant, ProjectController projectController) {
        super(sceneController);

        this.backButton = new Button("Retour");

        // Initialize layout elements
        this.vBox = new VBox();
        this.title = new Text("Consulter les travaux en cours");
        this.projectListView = new ListView<>();
        this.boroughFilterCombo = new ComboBox<>();
        this.typeOfWorkFilterCombo = new ComboBox<>();

        this.borough = new HashSet<>();
        this.typesOfWork = new HashSet<>();

        this.intervenant = intervenant;
        
        this.projectController = projectController;
        

    }
    
    @Override
    public void setScene() {

        // Set up the root layout and title font
        this.root.setCenter(vBox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold;");
        

        // Set the layout for the filters
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(new Text("Filtrer par quartier: "), this.boroughFilterCombo,
                new Text("Filtrer par type de travaux: "), this.typeOfWorkFilterCombo);

        // Set default values for ComboBoxes
        boroughFilterCombo.setValue("Aucun filtre");
        typeOfWorkFilterCombo.setValue("Aucun filtre");

        // Set the layout for the project list and count
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        

        this.projectListView.getItems().clear();

        try {
            if (this.projects != null) this.projects.clear(); 
            
            this.projects = projectController.getProjects();

            this.projectCountText = new Text("Total des projets : " + Integer.toString(this.projects.size()));
            this.projectCountText.setFont(new Font("Arial", 14));
            bottomBox.getChildren().add(this.projectCountText);

            for (Project project : this.projects) {
                System.out.println("adding projects");
                this.projectListView.getItems().add(project.afficher());
                this.borough.add(project.getQuartiersAffected());
                this.typesOfWork.add(project.getDescription());
            }

        } catch (Exception e) {
            VBox errorBox = new VBox();
            errorBox.getChildren().add(new Text(e.getMessage()));
            this.projectListView.getItems().add(errorBox);
        }

        

        // Add components to the VBox
        this.vBox.getChildren().addAll(this.title, filterBox, this.projectListView, bottomBox, this.backButton);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(20);

        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double listViewHeight = screenHeight * 0.6;
        this.projectListView.setPrefHeight(listViewHeight);

        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("menu");
        });

        boroughFilterCombo.getItems().addAll(this.borough);
        boroughFilterCombo.getItems().add("Aucun filtre");

        typeOfWorkFilterCombo.getItems().addAll(this.typesOfWork);
        typeOfWorkFilterCombo.getItems().add("Aucun filtre");

        // Set up the ComboBoxes for filtering
        boroughFilterCombo.setOnAction(e -> filterProjects());
        typeOfWorkFilterCombo.setOnAction(e -> filterProjects());
    }

    private void filterProjects() {
        String selectedBorough = boroughFilterCombo.getValue();
        String selectedType = typeOfWorkFilterCombo.getValue();

        // Fetch the original list of projects from the API again and filter based on
        // selection
       
        projectListView.getItems().clear();

        if (this.projects != null) this.projects.clear();

        try {
            this.projects = projectController.getProjects();

            // Loop through the projects and apply the filters
            for (Project project : this.projects) {

                String boroughid = project.getQuartiersAffected();
                String reasonCategory = project.getDescription();

    
                // Check if selectedBorough or selectedType is null before comparison
                boolean matchesBorough = (selectedBorough == null || selectedBorough.equals("Aucun filtre"))
                        || boroughid.equals(selectedBorough);
                boolean matchesTypeOfWork = (selectedType == null || selectedType.equals("Aucun filtre"))
                        || reasonCategory.equals(selectedType);
                

                if (matchesBorough && matchesTypeOfWork) {
                    projectListView.getItems().add(project.afficher());
                }
                
            }
        } catch (Exception e) {
            VBox errorBox = new VBox();
            errorBox.getChildren().add(new Text(e.getMessage()));
            projectListView.getItems().add(errorBox);
        }

        projectCountText.setText("Total des projets: " + projectListView.getItems().size());
    }
}