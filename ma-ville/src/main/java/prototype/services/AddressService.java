package prototype.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressService {

    private static final String API_KEY = "AIzaSyALSJ8oH8Ko2r8gkik4dTKkzDlIEw1Zzf8";

    public AddressService() {}

    /**
     * Méthode qui utilise l'api de google pour trouver le quartier d'une adresse donnée
     *
     * @param codePostal le code postal de l'adresse
     * @return {@link String} - le quartier
     * @throws Exception
     */
     private String getQuartierFromAddress(String codePostal) throws Exception {
        try {
            // Prepare the API URL
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    codePostal + "+Canada" + "&key=" + API_KEY;
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

    /**
     * Méthode pour trouver la ville d'une adresse donnée. Principalement utilisé par {@link prototype.users.UserCredentialsVerifier UserCredentialsVerifier}
     * @param codePostal le code postal de l'adresse
     * @return {@link String} - la ville
     * @throws Exception
     */
    private String getCityFromAddress(String codePostal) throws Exception {
        try {
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    codePostal + "+Canada" + "&key=" + API_KEY;
            URL url = new URL(urlString);
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

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            if (jsonResponse.get("status").getAsString().equals("OK")) {
                for (var result : jsonResponse.getAsJsonArray("results")) {
                    var addressComponents = result.getAsJsonObject().getAsJsonArray("address_components");
                    for (var component : addressComponents) {
                        var types = component.getAsJsonObject().getAsJsonArray("types");
                        if (types.toString().contains("\"locality\"")) {
                            return component.getAsJsonObject().get("long_name").getAsString();
                        }
                    }
                }
                throw new  Exception("City not found.");
            } else {
                throw new Exception("Error fetching city: " + jsonResponse.get("status").getAsString());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Getter pour le quartier d'une adresse, renvoyé par {@link #getQuartierFromAddress(String)}
     *
     * @param codePostal le code postal de l'adresse
     * @return {@link String} le quartier
     * @throws Exception
     */
    public String getQuartier(String codePostal) throws Exception{
        return getQuartierFromAddress(codePostal);
    }

    /**
     * Getter pour la ville d'une addresse, renvoyé par {@link #getCityFromAddress(String)}
     * @param codePostal le code postal
     * @return {@link String} la ville
     * @throws Exception
     */
    public String getCity(String codePostal) throws Exception{
        return this.getCityFromAddress(codePostal);
    }

}
