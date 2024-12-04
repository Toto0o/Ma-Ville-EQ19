package prototype.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import prototype.Users.UserSession;
import prototype.Users.Utilisateur;

public class FirebaseLoader {

    volatile Utilisateur user;

    public FirebaseLoader() {}

    public FirebaseLoader(Utilisateur user) {
        this.user = user;
    }

    public Utilisateur authenticate(String email, String password) {
        Thread authenticate = authenticateWithFirebase(email, password);
        try {
            authenticate.start();
            authenticate.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return this.user;
    }

    private Thread authenticateWithFirebase(String email, String password) {
        Thread thread = new Thread(() -> {
            try {
                String apiKey = "AIzaSyD95B1nhXm9xVTn_QJjXpQD-FDEqlG6cKM";
                URL authUrl = new URL(
                        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
                HttpURLConnection authConnection = (HttpURLConnection) authUrl.openConnection();
                authConnection.setRequestMethod("POST");
                authConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                authConnection.setDoOutput(true);

                String jsonInputString = String.format(
                        "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);

                try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
                        authConnection.getOutputStream(),
                        "UTF-8")) {
                    writer.write(jsonInputString);
                    writer.flush();
                }

                int authResponseCode = authConnection.getResponseCode();
                if (authResponseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(authConnection.getInputStream(), "UTF-8"))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        // Extract user ID (localId) from the response
                        String responseBody = response.toString();
                        String userId = extractFieldFromJson(responseBody, "localId");

                        // Save UID in UserSession
                        UserSession.getInstance().setUserId(userId);

                        // Check user role in Firebase Realtime Database
                        Thread userRole = checkUserRole(userId);
                        userRole.start();
                        userRole.join();

                    }
                } else {
                    throw new Error("Error while authenticating");
                }
            } catch (Exception e) {
                    e.printStackTrace();
                }
        });

        return thread;
    }

    private Thread checkUserRole(String userId) {
        
        Thread thread = new Thread(() -> {
            try {
                String databaseUrl = "https://maville-18aa2-default-rtdb.firebaseio.com/";

                // Check the 'residents' folder
                String residentUrl = databaseUrl + "residents/" + userId + ".json";
                System.out.println(residentUrl);
                if (isUserInFolder(residentUrl)) {
                    this.user = getUser();
                    return;
                }

                // Check the 'intervenants' folder
                String intervenantUrl = databaseUrl + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    this.user = getUser();
                    return;
                }

                // If user not found in either folder
                throw new Error("User not found");
            } catch (Exception e) {
                throw e;
            }
        });

        return thread;
    }

    private boolean isUserInFolder(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Check if the response is not null (user exists)
                    return !response.toString().equals("null");
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking folder: " + e.getMessage());
        }
        return false;
    }

    private String extractFieldFromJson(String json, String field) {
        try {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            return null;
        }
    }
}
