package prototype.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchEntraves {

    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    public static List<JSONObject> fetchEntraves() throws Exception {
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

        // Convert JSONArray to List of JSONObjects
        List<JSONObject> entraveList = new ArrayList<>();
        for (int i = 0; i < records.length(); i++) {
            entraveList.add(records.getJSONObject(i));
        }

        return entraveList;
    }
}