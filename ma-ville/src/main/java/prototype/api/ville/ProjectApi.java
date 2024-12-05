package prototype.api.ville;

import prototype.projects.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.cloud.storage.Acl;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProjectApi {

    private String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    private ArrayList<ProjectVille> projects;
    private Thread fetchThread;

    public ProjectApi() {
        this.projects = new ArrayList<>();
    }

     private void fetchProjects() {

        this.fetchThread=  new Thread(() -> {
            try {
                // Set up the API connection
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the response using org.json
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject result = jsonResponse.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");

                for (int i = 0; i < records.length(); i++) {
                    JSONObject jsonObject = records.getJSONObject(i);
                    String id = jsonObject.optString("id", "Non spécifié");
                    String boroughid = jsonObject.optString("boroughid", "Non spécifié");
                    String currentStatus = jsonObject.optString("currentstatus", "Non spécifié");
                    String reasonCategory = jsonObject.optString("reason_category", "Non spécifié");
                    String submitterCategory = jsonObject.optString("submittercategory", "Non spécifié");
                    String organizationName = jsonObject.optString("organizationname", "Non spécifié");

                    ProjectVille project = new ProjectVille(id, boroughid, currentStatus, reasonCategory, submitterCategory, organizationName, i);
                    this.projects.add(project);

                }
                

            } catch (Exception e) {
                e.printStackTrace();
                throw new Error("Erreur lors de la récupération des données");
            }
        });

        this.fetchThread.start();

    }

    public ArrayList<ProjectVille> getProject() {
        try {
            fetchProjects();
            this.fetchThread.join();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return this.projects;
    }

    
}
