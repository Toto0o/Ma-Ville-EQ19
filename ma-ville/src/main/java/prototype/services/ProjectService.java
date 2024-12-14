package prototype.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import prototype.users.UserSession;
import prototype.projects.Project;


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

    public ArrayList<Project> getProjects() throws Exception {
        return fetchProjects();
    }
}
