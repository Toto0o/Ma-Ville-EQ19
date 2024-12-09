package prototype.api.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import prototype.projects.Project;

/**
 * Api Firebase permettant de :
 *   charger les projets contenus dans la base de donnée;
 *   sauvagder les projets dans la base de donnée;
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 * 
 */

public class ProjectApiFirebase {

    private ArrayList<Project> projects;

    public ProjectApiFirebase() {
        this.projects = new ArrayList<>();
    }

    public ArrayList<Project> getProjects() {
        fetchProjects();
        return this.projects;
    }

    private void fetchProjects() {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");

        // Fetch the projects data
        projectsRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear previous project data (if any)
                if (projects != null) projects.clear();
                

                // Loop through all the projects and filter by user UID
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String projectUid = snapshot.child("uid").getValue(String.class);

                    // Only add the project if the UID matches the logged-in user
                    
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
                            startDate,
                            endDate,
                            horaireTravaux,
                            status,
                            projectUid,
                            roadsAffected);
                    project.setFirebaseKey(snapshot.getKey());
                    projects.add(project);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle any error that might occur while fetching data
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
    }

    public void saveProjectChanges(String projectKey, HashMap<String,String> changes) {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects").child(projectKey);

        for (String key : changes.keySet()) {
            if (changes.get(key) != null)
            projectsRef.child(key).setValueAsync(changes.get(key));
        }
    }
}
