package prototype.Scenes;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Projects.Project;
import prototype.Users.UserSession;

public class IntervenantProjectsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button saveButton;
    private List<Project> projectsList;

    public IntervenantProjectsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.projectsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.saveButton = new Button("Save Settings");
    }

    @Override
    public void setScene() {
        // Set the back button at the top
        this.root.setTop(this.backButton);

        // Set up the BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createScrollableProjectBox());
        borderPane.setBottom(saveButton);

        // Set the main content in the center
        this.root.setCenter(borderPane);

        // Add the back button's functionality
        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("intervenantMenu");
        });

        // Add the save button's functionality
        this.saveButton.setOnMouseClicked(event -> saveProjectChanges());

        // Fetch projects from Firebase for the current user
        fetchProjectsForCurrentUser();
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

    private void fetchProjectsForCurrentUser() {
        // Get the current logged-in user's UID
        String userUid = UserSession.getInstance().getUserId();

        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");

        // Fetch the projects data
        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear previous project data (if any)
                projectsList.clear();

                // Loop through all the projects and filter by user UID
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String projectUid = snapshot.child("uid").getValue(String.class);

                    // Only add the project if the UID matches the logged-in user
                    if (userUid.equals(projectUid)) {
                        // Fetching project details
                        String title = snapshot.child("title").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String type = snapshot.child("type").getValue(String.class);
                        String quartiersAffected = snapshot.child("quartiersAffected").getValue(String.class);
                        String roadsAffected = snapshot.child("roadsAffected").getValue(String.class);
                        String startDate = snapshot.child("startDate").getValue(String.class);
                        String endDate = snapshot.child("endDate").getValue(String.class);
                        String horaireTravaux = snapshot.child("horaireTravaux").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);

                        // Create a Project object and add it to the list
                        Project project = new Project(
                                title,
                                description,
                                type,
                                quartiersAffected,
                                roadsAffected,
                                startDate,
                                endDate,
                                horaireTravaux,
                                status,
                                projectUid);
                        project.setFirebaseKey(snapshot.getKey());
                        projectsList.add(project);
                    }
                }

                // After fetching and filtering projects, update the display
                updateProjectDisplay();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle any error that might occur while fetching data
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
    }

    private void updateProjectDisplay() {
        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear previous display
            vbox.getChildren().clear();

            // Add each project to the VBox
            for (Project project : projectsList) {
                // Create text fields for editing each project detail
                TextField titleField = new TextField(project.getTitle());
                TextField descriptionField = new TextField(project.getDescription());
                TextField typeField = new TextField(project.getType());
                TextField quartiersField = new TextField(project.getQuartiersAffected());
                TextField roadsField = new TextField(project.getRoadsAffected());
                TextField startDateField = new TextField(project.getStartDate());
                TextField endDateField = new TextField(project.getEndDate());
                TextField horaireTravauxField = new TextField(project.getHoraireTravaux());

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
            }
        });
    }

    private void saveProjectChanges() {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");

        // Iterate through the list of projects and update their values
        for (int i = 0; i < projectsList.size(); i++) {
            Project project = projectsList.get(i);

            // Retrieve the Firebase key of the current project
            String projectKey = project.getFirebaseKey(); // Assuming getFirebaseKey() gives the correct Firebase node
                                                          // key

            // Retrieve the updated values from the text fields in the UI
            VBox projectBox = (VBox) vbox.getChildren().get(i);
            TextField titleField = (TextField) projectBox.getChildren().get(1);
            TextField descriptionField = (TextField) projectBox.getChildren().get(3);
            TextField typeField = (TextField) projectBox.getChildren().get(5);
            TextField quartiersField = (TextField) projectBox.getChildren().get(7);
            TextField roadsField = (TextField) projectBox.getChildren().get(9);
            TextField startDateField = (TextField) projectBox.getChildren().get(11);
            TextField endDateField = (TextField) projectBox.getChildren().get(13);
            TextField horaireTravauxField = (TextField) projectBox.getChildren().get(15);

            // Update the project object with the new values from the fields
            project.setTitle(titleField.getText());
            project.setDescription(descriptionField.getText());
            project.setType(typeField.getText());
            project.setQuartiersAffected(quartiersField.getText());
            project.setRoadsAffected(roadsField.getText());
            project.setStartDate(startDateField.getText());
            project.setEndDate(endDateField.getText());
            project.setHoraireTravaux(horaireTravauxField.getText());

            // Now use the stored Firebase key to reference the project node and update its
            // fields
            DatabaseReference projectRef = projectsRef.child(projectKey); // Use the projectKey here

            // Update the project fields in Firebase
            projectRef.child("title").setValueAsync(project.getTitle());
            projectRef.child("description").setValueAsync(project.getDescription());
            projectRef.child("type").setValueAsync(project.getType());
            projectRef.child("quartiersAffected").setValueAsync(project.getQuartiersAffected());
            projectRef.child("roadsAffected").setValueAsync(project.getRoadsAffected());
            projectRef.child("startDate").setValueAsync(project.getStartDate());
            projectRef.child("endDate").setValueAsync(project.getEndDate());
            projectRef.child("horaireTravaux").setValueAsync(project.getHoraireTravaux());
            projectRef.child("uid").setValueAsync(project.getUid()); // Optionally update UID if needed
        }

        // Provide feedback after saving changes
        System.out.println("Projects updated successfully!");

        // Optionally refresh the display to reflect the saved data
        fetchProjectsForCurrentUser();
    }

}
