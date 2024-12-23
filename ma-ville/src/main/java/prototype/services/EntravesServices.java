package prototype.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import prototype.entraves.Entrave;

/**
 * Connexion api avec la base de donnée de la ville de Montréal pour charger les entraves routières
 *
 * <p><a href = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd">donnes.montreal.ca</a></p>
 */
public class EntravesServices {

    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    /**
     *
     * Charges les entraves de la base de données
     * @return {@link ArrayList}&lt;{@link Entrave}&gt;
     * @throws Exception
     */
    public static ArrayList<Entrave> fetchEntraves() throws Exception {
        ArrayList<Entrave> entraves = new ArrayList<>();

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

        for (int i = 0; i < records.length(); i++) {
            JSONObject jsonObject = records.getJSONObject(i);
            String idRequest = jsonObject.optString("id_request", "Non spécifié");
            String streetId = jsonObject.optString("streetid", "Non spécifié");
            String shortname = jsonObject.optString("shortname", "Non spécifié");
            String streetimpacttype = jsonObject.optString("streetimpacttype", "Non spécifié");

            Entrave entrave = new Entrave(idRequest, streetId, shortname, streetimpacttype);
            entraves.add(entrave);
        }
        return entraves;
    }

    /**
     * Retourne les entraves cahrgés par {@link #fetchEntraves()}
     * @return {@link ArrayList}&lt;{@link Entrave}&gt;
     * @throws Exception
     */
    public ArrayList<Entrave> getEntraves() throws Exception {
        return fetchEntraves();
    }
}
