package prototype.api.ville;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class QuartierApi {

    private String name;
    private static final String API_KEY = "AIzaSyALSJ8oH8Ko2r8gkik4dTKkzDlIEw1Zzf8";

    public QuartierApi() {}

    // Method to get the neighborhood from the address using Google Maps API
    private String getQuartierFromAddress(String address) throws Exception {
        try {
            // Prepare the API URL
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    address.replace(" ", "+") + "&key=" + API_KEY;
            URL url = new URL(urlString);

            // Establish HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Set UTF-8 charset for proper decoding

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // Explicit
                                                                                                               // UTF-8
                                                                                                               // encoding
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON with Gson
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            if (jsonResponse.get("status").getAsString().equals("OK")) {
                for (var result : jsonResponse.getAsJsonArray("results")) {
                    var addressComponents = result.getAsJsonObject().getAsJsonArray("address_components");
                    for (var component : addressComponents) {
                        var types = component.getAsJsonObject().getAsJsonArray("types");
                        if (types.toString().contains("sublocality_level_1")) {
                            return component.getAsJsonObject().get("short_name").getAsString();
                        }
                    }
                }
                throw new  Exception("Neighborhood not found.");
            } else {
                throw new Exception("Error fetching neighborhood: " + jsonResponse.get("status").getAsString());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Getter for the neighborhood name
    public String getName(String address) throws Exception{
        try {
            this.name = getQuartierFromAddress(address);
        } catch (Exception e) {
            throw e;
        }
        return name;
    }

    // Setter for the neighborhood name (optional)
    public void setName(String name) {
        this.name = name;
    }

}
