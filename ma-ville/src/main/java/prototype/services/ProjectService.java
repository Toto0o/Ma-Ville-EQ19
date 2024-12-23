package prototype.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
 * <p>Utiliser {@link #getProjects(FirebaseCallback)} pour charger les projets</p>
 */
public class ProjectService {

    public void saveProjectToFirebase(Project project) {
        try {
            // Generate a random request ID
            String projectId = java.util.UUID.randomUUID().toString();
            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("projects").child(projectId);
            String userUid = UserSession.getInstance().getUserId(); // Use the UID from UserSession
            // Create a Project object with collected data
            requestFolderRef.setValueAsync(project); // Save request data under "projects/RequestID" node
            System.out.println("Project saved to Firebase under ID: " + projectId);
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
    private static ArrayList<Project> fetchProjects() throws Exception {
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
                String horaireTravaux = startDate.split(" ")[1] + " " + endDate.split(" ")[1];
                Project project = new Project(
                        id,
                        submitterCategory,
                        Type.getType(reasonCategory),
                        boroughid,
                        startDate,
                        endDate,
                        horaireTravaux,
                        Status.getStatus(currentStatus),
                        organizationName,
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
    private static void fetchProjectsFromFirebase(FirebaseCallback<ArrayList<Project>> callback) throws Exception {
        ArrayList<Project> projects = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");
        projectsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Project project = snapshot.getValue(Project.class);
                        projects.add(project);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                callback.onSuccessReturn(projects);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch projects: " + error.getMessage());
            }
        });
    }

    /**
     * Retourne les projets chargés par {@link #fetchProjects()} et {@link #fetchProjectsFromFirebase()}
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     * @throws Exception
     */
    public void getProjectsFromFirebase(FirebaseCallback<ArrayList<Project>> callback) throws Exception {
        fetchProjectsFromFirebase(callback);
    }

    public ArrayList<Project> getProjects() throws Exception {
        return fetchProjects();
    }
}
