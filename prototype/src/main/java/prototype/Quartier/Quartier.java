package prototype.Quartier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Quartier {
    private static final String API_KEY = "AIzaSyALSJ8oH8Ko2r8gkik4dTKkzDlIEw1Zzf8";

    /**
     * Fetches the quartier (neighborhood) for a given address using the Google Maps
     * API.
     * 
     * @param address The address to look up.
     * @return The name of the neighborhood (quartier) or an error message.
     */
    public String getQuartierFromAddress(String address) {
        try {
            // Prepare the API URL
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    address.replace(" ", "+") + "&key=" + API_KEY;
            URL url = new URL(urlString);

            // Establish HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON to extract the short_name for sublocality_level_1
            String json = response.toString();
            String key = "\"sublocality_level_1\"";
            if (json.contains(key)) {
                // Locate the sublocality_level_1 component
                int keyIndex = json.indexOf(key);
                int shortNameIndex = json.lastIndexOf("\"short_name\"", keyIndex);
                int valueStart = json.indexOf("\"", shortNameIndex + 14) + 1; // Skip "short_name":
                int valueEnd = json.indexOf("\"", valueStart);
                return json.substring(valueStart, valueEnd);
            } else {
                return "Neighborhood not found in the response.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching neighborhood.";
        }
    }
}
