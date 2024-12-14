package prototype.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.firebase.database.*;
import org.json.JSONArray;
import org.json.JSONObject;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.users.UserSession;
import prototype.projects.Project;

/**
 * Connexion Api avec firebase pour charger les projets et sauvgarder un nouveau projet
 *
 * <p>Utiliser {@link #saveProjectToFirebase(Project)} pour enregistrer un nouveau projet</p>
 * <p>Utiliser {@link #getProjects()} pour charger les projets</p>
 */
public class ProjectService {

    public void saveProjectToFirebase(Project project) {
        try {
            // Generate a random request ID
            String requestId = java.util.UUID.randomUUID().toString();
            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("projects").child(requestId);
            String userUid = UserSession.getInstance().getUserId(); // Use the UID from UserSession
            // Create a Project object with collected data
            requestFolderRef.setValueAsync(project); // Save request data under "projects/RequestID" node
            System.out.println("Request saved to Firebase under ID: " + requestId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    /**
     * Charge les projets contenus dans la base données de la ville de Montréal
     * <p><a href = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b">donnees.montreal.ca</a></p>
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     * @throws Exception
     */
    public static ArrayList<Project> fetchProjects() throws Exception {
        // Set up the API connection
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            // Parse the response using org.json
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject result = jsonResponse.getJSONObject("result");
            JSONArray records = result.getJSONArray("records");
            ArrayList<Project> projects = new ArrayList<>();
            for (int i = 0; i < records.length(); i++) {

                JSONObject jsonObject = records.getJSONObject(i);
                String id = jsonObject.optString("id", "Non spécifié");
                String boroughid = jsonObject.optString("boroughid", "Non spécifié");
                String currentStatus = jsonObject.optString("currentstatus", "Non spécifié");
                String reasonCategory = jsonObject.optString("reason_category", "Non spécifié");
                String submitterCategory = jsonObject.optString("submittercategory", "Non spécifié");
                String organizationName = jsonObject.optString("organizationname", "Non spécifié");
                String startDate = jsonObject.optString("duration_start_date").replaceAll("[TZ]", " ");
                String endDate = jsonObject.optString("duration_end_date").replaceAll("[TZ]", " ");
                String streetEntrave = jsonObject.optString("occupancy_name");

                Project project = new Project(
                        id,
                        boroughid,
                        reasonCategory,
                        submitterCategory,
                        organizationName,
                        startDate,
                        endDate,
                        currentStatus,
                        streetEntrave);
                projects.add(project);

            }
            return projects;
        }
    }

    /**
     * Méthode pour charger les projets dans la base de données Firebase
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     * @throws Exception
     */
    private static ArrayList<Project> fetchProjectsFromFirebase() throws Exception {
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
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
        return projects;
    }

    /**
     * Retourne les projets chargés par {@link #fetchProjects()} et {@link #fetchProjectsFromFirebase()}
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     * @throws Exception
     */
    public ArrayList<Project> getProjects() throws Exception {
        ArrayList<Project> projects = new ArrayList<>(fetchProjects());
        projects.addAll(fetchProjectsFromFirebase());
        return projects;
    }
}
