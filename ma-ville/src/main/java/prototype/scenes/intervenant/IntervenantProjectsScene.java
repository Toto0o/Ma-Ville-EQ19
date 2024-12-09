package prototype.scenes.intervenant;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.application.Platform;
import prototype.controllers.ProjectController;

import prototype.controllers.SceneController;
import prototype.users.UserSession;
import prototype.projects.*;
import prototype.scenes.Scenes;

public class IntervenantProjectsScene extends Scenes{

    private VBox vbox;
    private Button backButton;
    private Button saveButton;
    private List<Project> projectsList;
    private ProjectController projectController;

    public IntervenantProjectsScene(SceneController sceneController, ProjectController projectController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.projectsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.saveButton = new Button("Enregistrer les changements");
        this.projectController = projectController;
    }

    @Override
    public void setScene() {
        // Set the back button at the top
        this.root.setTop(this.backButton);

        // Set up the BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createScrollableProjectBox());

        try {
            String userUid = UserSession.getInstance().getUserId();
            ArrayList<Project> projects = projectController.getProjects();
            

            for (Project project : projects) {
                if (userUid.equals(project.getUid())) {
                    VBox box = new VBox();
                    Button updateButton = new Button("Mettre à jours les informations");

                    updateButton.setOnMouseClicked(event -> {
                        box.getChildren().clear();
                        box.getChildren().add(updateProjectDisplay(project));
                    });
                    box.getChildren().addAll(project.afficher(), updateButton);

                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Set the main content in the center
        this.root.setCenter(borderPane);

        // Add the back button's functionality
        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });
    }

    private ScrollPane createScrollableProjectBox() {
        // Make the vbox scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Set max height for scrollable area
        this.vbox.setMaxHeight(400);
        this.vbox.setStyle("-fx-padding: 10;");

        return scrollPane;
    }


    private VBox updateProjectDisplay(Project project) {
        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear previous display
            
            // Create text fields for editing each project detail
            TextField titleField = new TextField(project.getTitle());
            titleField.setId("title");
            TextField descriptionField = new TextField(project.getDescription());
            descriptionField.setId("description");
            TextField typeField = new TextField(project.getType());
            typeField.setId("type");
            TextField quartiersField = new TextField(project.getQuartiersAffected());
            quartiersField.setId("quartiersAffected");
            TextField roadsField = new TextField(project.getStreetEntrave());
            roadsField.setId("roadsAffected");
            TextField startDateField = new TextField(project.getStartDate());
            startDateField.setId("startDate");
            TextField endDateField = new TextField(project.getEndDate());
            endDateField.setId("endDate");
            TextField horaireTravauxField = new TextField(project.getHoraireTravaux());
            horaireTravauxField.setId("horaireTravaux");

            // Create labels and add text fields for project editing
            VBox projectBox = new VBox(5);
            projectBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-padding: 10;");
            projectBox.setPrefWidth(300); // Set a smaller width for each box
            projectBox.setAlignment(Pos.TOP_LEFT);

            projectBox.getChildren().add(new Text("Title:"));
            projectBox.getChildren().add(titleField);
            projectBox.getChildren().add(new Text("Description:"));
            projectBox.getChildren().add(descriptionField);
            projectBox.getChildren().add(new Text("Type:"));
            projectBox.getChildren().add(typeField);
            projectBox.getChildren().add(new Text("Quartiers Affected:"));
            projectBox.getChildren().add(quartiersField);
            projectBox.getChildren().add(new Text("Roads Affected:"));
            projectBox.getChildren().add(roadsField);
            projectBox.getChildren().add(new Text("Start Date:"));
            projectBox.getChildren().add(startDateField);
            projectBox.getChildren().add(new Text("End Date:"));
            projectBox.getChildren().add(endDateField);
            projectBox.getChildren().add(new Text("Horaire Travaux:"));
            projectBox.getChildren().add(horaireTravauxField);

            // Add the edited project box to the vbox
            vbox.getChildren().add(projectBox);
            vbox.getChildren().add(saveButton);


            saveButton.setOnMouseClicked(event -> {
                HashMap<String,String> changes = new HashMap<>();

                for (javafx.scene.Node field : vbox.getChildren()) {
                    if (field instanceof TextField change) {
                        if (change.getText() != null) {
                            changes.put(change.getId(), change.getText().trim());
                        }
                    }
                }

                this.projectController.saveProjectChanges(project.getFirebaseKey(), changes);
            });
        });

        return vbox;
    }
}
