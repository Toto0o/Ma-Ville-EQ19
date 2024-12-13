package prototype.service;

import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import prototype.Projects.Project;
import prototype.Users.UserSession;

public class IntervenantFunctions {

    public static void fetchProjectsForCurrentUser(List<Project> projectsList, VBox vbox,
            Runnable updateDisplayCallback) {
        String userUid = UserSession.getInstance().getUserId();

        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");

        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projectsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String projectUid = snapshot.child("uid").getValue(String.class);

                    if (userUid.equals(projectUid)) {
                        String title = snapshot.child("title").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String type = snapshot.child("type").getValue(String.class);
                        String quartiersAffected = snapshot.child("quartiersAffected").getValue(String.class);
                        String roadsAffected = snapshot.child("roadsAffected").getValue(String.class);
                        String startDate = snapshot.child("startDate").getValue(String.class);
                        String endDate = snapshot.child("endDate").getValue(String.class);
                        String horaireTravaux = snapshot.child("horaireTravaux").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);

                        Project project = new Project(
                                title, description, type, quartiersAffected, roadsAffected,
                                startDate, endDate, horaireTravaux, status, projectUid);
                        project.setFirebaseKey(snapshot.getKey());
                        projectsList.add(project);
                    }
                }

                Platform.runLater(updateDisplayCallback);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
    }

    public static void saveProjectChanges(List<Project> projectsList, VBox vbox) {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");

        for (int i = 0; i < projectsList.size(); i++) {
            Project project = projectsList.get(i);
            String projectKey = project.getFirebaseKey();

            VBox projectBox = (VBox) vbox.getChildren().get(i);
            TextField titleField = (TextField) projectBox.getChildren().get(1);
            TextField descriptionField = (TextField) projectBox.getChildren().get(3);
            TextField typeField = (TextField) projectBox.getChildren().get(5);
            TextField quartiersField = (TextField) projectBox.getChildren().get(7);
            TextField roadsField = (TextField) projectBox.getChildren().get(9);
            TextField startDateField = (TextField) projectBox.getChildren().get(11);
            TextField endDateField = (TextField) projectBox.getChildren().get(13);
            TextField horaireTravauxField = (TextField) projectBox.getChildren().get(15);

            project.setTitle(titleField.getText());
            project.setDescription(descriptionField.getText());
            project.setType(typeField.getText());
            project.setQuartiersAffected(quartiersField.getText());
            project.setRoadsAffected(roadsField.getText());
            project.setStartDate(startDateField.getText());
            project.setEndDate(endDateField.getText());
            project.setHoraireTravaux(horaireTravauxField.getText());

            DatabaseReference projectRef = projectsRef.child(projectKey);
            projectRef.child("title").setValueAsync(project.getTitle());
            projectRef.child("description").setValueAsync(project.getDescription());
            projectRef.child("type").setValueAsync(project.getType());
            projectRef.child("quartiersAffected").setValueAsync(project.getQuartiersAffected());
            projectRef.child("roadsAffected").setValueAsync(project.getRoadsAffected());
            projectRef.child("startDate").setValueAsync(project.getStartDate());
            projectRef.child("endDate").setValueAsync(project.getEndDate());
            projectRef.child("horaireTravaux").setValueAsync(project.getHoraireTravaux());
            projectRef.child("uid").setValueAsync(project.getUid());
        }

        System.out.println("Projects updated successfully!");
    }
}
