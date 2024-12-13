package prototype.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import prototype.Projects.Project;
import prototype.Users.UserSession;

public class ProjectServices {

    /**
     * Saves a new project request to Firebase.
     */
    public void saveProjectToFirebase(String title, String description, String type, String quartiersAffected,
            String roadsAffected, String startDate, String endDate, String horaireTravaux,
            String status) {
        try {
            // Generate a random request ID
            String requestId = java.util.UUID.randomUUID().toString();

            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("projects").child(requestId);

            String userUid = UserSession.getInstance().getUserId(); // Use the UID from UserSession

            // Create a Project object with collected data
            Project project = new Project(title, description, type, quartiersAffected, roadsAffected, startDate,
                    endDate, horaireTravaux, status, userUid);
            requestFolderRef.setValueAsync(project); // Save request data under "projects/RequestID" node

            System.out.println("Request saved to Firebase under ID: " + requestId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    public static JSONArray fetchProjects() throws Exception {
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
            return result.getJSONArray("records");
        }
    }
}
