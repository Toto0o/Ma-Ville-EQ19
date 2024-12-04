package prototype.api.ville;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/* import com.google.api.client.json.Json; */

import prototype.entraves.Entrave;


public class EntravesApiLoader {

    private final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";
    
    private ArrayList<Entrave> entravesList;

    private Thread fetchEntravesThread;

    public EntravesApiLoader() {
        this.entravesList = new ArrayList<>();
    }

    private void fetchEntraves() {
        this.fetchEntravesThread = new Thread(() -> {
            try {
                
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

                    // Loop through the entraves and add them to the ListView
                for (int i = 0; i < records.length(); i++) {
                    JSONObject jsonObject = records.getJSONObject(i);

                    String id_request = jsonObject.optString("id_request");
                    String street_id = jsonObject.optString("streetid");
                    String shortname = jsonObject.optString("shortname");
                    String streetimpacttype = jsonObject.optString("streetimpacttype");

                    Entrave entrave = new Entrave(id_request, street_id, shortname, streetimpacttype, i);

                    this.entravesList.add(entrave); 
                }

            }

             catch (Exception e) {
                e.printStackTrace();
            } 
        });

        this.fetchEntravesThread.start();
    }

    public ArrayList<Entrave> getEntraves() {
        try {
            fetchEntraves();
            this.fetchEntravesThread.join();
        }
        catch (Exception e) {
            
            e.printStackTrace();
        }
        return this.entravesList;
    }

}
