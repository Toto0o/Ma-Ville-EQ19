package prototype.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QuartiersServices {

    List<String> montrealPostalCodes = List.of(
            "H2T 2M7", // Plateau Mont-Royal
            "H2V 4A7", // Mile-End
            "H2Y 1B6", // Vieux-Montréal
            "H4G 1P9", // Verdun
            "H1X 1J4", // Rosemont–La Petite-Patrie
            "H1W 1A1", // Hochelaga-Maisonneuve
            "H2V 4Y9", // Outremont
            "H3S 1X4", // Côte-des-Neiges
            "H3B 1A8", // Ville-Marie
            "H4C 2A3", // Saint-Henri
            "H4L 4P2", // Saint-Laurent
            "H3L 2N4", // Ahuntsic-Cartierville
            "H1J 2N7", // Anjou
            "H8S 3E6"  // Lachine
    );
    private static final String API_KEY = "AIzaSyALSJ8oH8Ko2r8gkik4dTKkzDlIEw1Zzf8";  // Replace this with your API key

    // Fetch quartiers (neighborhoods) in Montreal for multiple postal codes
    public List<String> fetchQuartiersInMontreal(List<String> postalCodes) {
        List<String> allQuartiers = new ArrayList<>();
        for (String postalCode : postalCodes) {
            try {
                String address = postalCode + "+Canada"; // Postal code with country
                String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
                String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress + "&key=" + API_KEY;

                HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() != 200) {
                    System.err.println("HTTP error code: " + conn.getResponseCode());
                    continue;  // Skip this postal code and move to the next one
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Log the raw response for debugging
                System.out.println("Geocoding API Response for " + postalCode + ": " + response.toString());

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray results = jsonResponse.optJSONArray("results");

                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        JSONArray addressComponents = result.getJSONArray("address_components");
                        for (int j = 0; j < addressComponents.length(); j++) {
                            JSONObject component = addressComponents.getJSONObject(j);
                            JSONArray types = component.getJSONArray("types");
                            if (types.toString().contains("sublocality_level_1")) {
                                allQuartiers.add(component.getString("short_name"));
                            }
                        }
                    }
                } else {
                    System.err.println("No results found in the Geocoding API response for " + postalCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fetched quartiers: " + allQuartiers);
        return allQuartiers;
    }

    public List<String> quartiersInMontreal(){
        List<String> allQuartiers = new ArrayList<>();
        allQuartiers=fetchQuartiersInMontreal(montrealPostalCodes);
        return allQuartiers;
    }

    // Fetch streets (rues) in a specific quartier
    public static List<String> fetchStreetsInQuartier(String quartier) {
        List<String> streetsList = new ArrayList<>();
        try {
            String query = "streets in " + quartier + " Montreal";
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String urlString = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + encodedQuery + "&key=" + API_KEY;

            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                System.err.println("HTTP error code: " + connection.getResponseCode());
                return streetsList;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Log the raw response for debugging
            System.out.println("Places API Response for streets in " + quartier + ": " + response.toString());

            // Parse the response to get streets
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.optJSONArray("results");

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    String street = result.getString("name");
                    streetsList.add(street);
                }
            } else {
                System.err.println("No results found in the Places API response.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fetched streets: " + streetsList);
        return streetsList;
    }

    public void updateSelectedQuartiersInFirebase(Resident currentResident) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("residents").child(UserSession.getInstance().getUserId());

        databaseReference.child("selectedQuartiers").setValue(currentResident.getSelectedQuartiers(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Error updating quartiers: " + databaseError.getMessage());
                } else {
                    System.out.println("Quartiers updated successfully");
                }
            }
        });
    }
}
