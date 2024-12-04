package prototype.api.firebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;

import com.google.api.core.ApiFuture;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Platform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import prototype.users.Address;
import prototype.users.Intervenant;
import prototype.users.Resident;
import prototype.users.Utilisateur;

public class UserFireBase {

    private boolean intervenant;
    private volatile Utilisateur user;

    public UserFireBase() {};
    
    public boolean getIntervenant() {
        return this.intervenant;
    }

    public Utilisateur authenticate(String email, String password) throws Exception {
        try {
            Thread authThread = authenticateWithFirebase(email, password);
            authThread.start();
            authThread.join();
            return this.user;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private Thread authenticateWithFirebase(String email, String password) throws IllegalAccessException {
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

                catch (Exception e) {
                    throw new IllegalAccessException("Invalid credentials");
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
                        System.out.println("userId:" + userId);

                        // Check user role in Firebase Realtime Database
                        Thread userRoleThread = checkUserRole(userId);
                        userRoleThread.start();
                        userRoleThread.join();
                        return;
                    }          
                   
                } else {
                    
                    try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(authConnection.getErrorStream(), "UTF-8"))) {
                        StringBuilder errorResponse = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            errorResponse.append(line);
                        }
                        
                } 
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
                    this.intervenant = false;
                    return;
                }

                // Check the 'intervenants' folder
                String intervenantUrl = databaseUrl + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    this.intervenant = true;
                    return;
                }

                // If user not found in either folder
                throw new NoSuchElementException("Error: User not found in any folder.");
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
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            return null;
        }
    }


    public void saveUserToFirebase(Utilisateur utilisateur) throws Error {
        try {
            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(utilisateur.getEmail())
                    .setPassword(utilisateur.getPassword())
                    .setDisplayName(utilisateur.getName() + " " + utilisateur.getLastname()));

            // Block and get the result of the async operation
            UserRecord userRecord = future.get(); // This will block until the operation completes

            String uid = userRecord.getUid(); // Get the unique UID of the user

            // Save user to Firebase Database under a folder named after the UID
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("residents").child(uid);

            userFolderRef.setValueAsync(utilisateur); // Save resident data under "residents/UID" node

            System.out.println("Resident data saved to Firebase under UID: " + uid);

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

}
