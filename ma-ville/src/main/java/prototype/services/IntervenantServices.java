package prototype.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.users.UserSession;


public class IntervenantServices {

    public ArrayList<Project> getProjects() {
        return fetchProjectsForCurrentUser();
    }

    public static ArrayList<Project> fetchProjectsForCurrentUser() {
        ArrayList<Project> projects = new ArrayList<>();
        String userUid = UserSession.getInstance().getUserId();
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");
        projectsRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                                title,
                                description,
                                Type.valueOf(type),
                                quartiersAffected,
                                startDate,
                                endDate,
                                horaireTravaux,
                                Status.valueOf(status),
                                projectUid,
                                roadsAffected);
                        project.setFirebaseKey(snapshot.getKey());
                        projects.add(project);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
        return projects;
    }
    public static void saveProjectChanges(HashMap<String, String> changes, String projectKey) {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects").child(projectKey);

        for (String key : changes.keySet()) {
            String value = changes.get(key);
            projectsRef.child(key).setValueAsync(value);
        }
    }

    public void updateProject(HashMap<String,String> changes, String projectKey) {
        saveProjectChanges(changes, projectKey);
    }

}
