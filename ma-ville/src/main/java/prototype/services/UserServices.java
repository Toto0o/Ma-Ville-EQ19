package prototype.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javafx.application.Platform;
import javafx.scene.control.Label;
import prototype.users.*;

/**
 * Connexion Api avec firebase pour traiter les actions relatives à l'utilisateur
 *
 * <p>Utiliser {@link #authenticateWithFirebase(String, String, Label)} pour l'authentification</p>
 * <p>Utiliser {@link #register(Resident)} pour enregistrer un résident</p>
 * <p>Utiliser {@link #register(Intervenant)} pour enregistrer un intervenant</p>
 */
public class UserServices {
    private static final String API_KEY = "AIzaSyD95B1nhXm9xVTn_QJjXpQD-FDEqlG6cKM";
    private static final String DATABASE_URL = "https://maville-18aa2-default-rtdb.firebaseio.com/";
    /**
     * Atuthentifier l'utilisateur avec Firebase en utilisant l'email et le mot de passe
     *
     * <p><a href = "https://cloud.google.com/identity-platform/docs/reference/rest">identitytoolkit.googleapis.com</a> </p>
     *
     * @param email le email de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @param statusLabel {@link Label} pour afficher les messages d'erreur
     */
    public void authenticateWithFirebase(String email, String password, Label statusLabel) {
        Thread thread = new Thread(() -> {
            try {
                URL authUrl = new URL(
                        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY);
                HttpURLConnection authConnection = (HttpURLConnection) authUrl.openConnection();
                authConnection.setRequestMethod("POST");
                authConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                authConnection.setDoOutput(true);
                String jsonInputString = String
                        .format("{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);
                try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
                        authConnection.getOutputStream(), "UTF-8")) {
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
                        checkUserRole(userId, statusLabel);
                    }
                } else {
                    Platform.runLater(() -> {
                        statusLabel.setText("Login failed.");
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                });
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Détermine le rôle de l'utilisateur selon son emplacement dans la base de donnée
     */
    private void checkUserRole(String userId, Label statusLabel) {
        Thread thread = new Thread(() -> {
            try {
                // Check the 'residents' folder
                String residentUrl = DATABASE_URL + "residents/" + userId + ".json";
                if (isUserInFolder(residentUrl)) {
                    // Fetch and save Resident data
                    Resident resident = (Resident) fetchUserData(residentUrl, true);
                    UserSession.getInstance()
                            .setUser(resident);
                    return;
                }
                // Check the 'intervenants' folder
                String intervenantUrl = DATABASE_URL + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    // Fetch and save Intervenant data
                    Intervenant intervenant = (Intervenant) fetchUserData(intervenantUrl, false);
                    UserSession.getInstance().setUser(intervenant);
                    return;
                }
                // If user not found in either folder
                Platform.runLater(() -> {
                    statusLabel.setText("Error: User not found in any folder.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                });
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Méthode pour obtenir l'utilisateur en cours
     * @return {@link Utilisateur}
     */
    private Utilisateur fetchUserData(String urlString, boolean isResident) {
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
                    // Parse response and create the appropriate object
                    String responseBody = response.toString();
                    String name = extractFieldFromJson(responseBody, "name");
                    String lastName = extractFieldFromJson(responseBody, "lastName");
                    String birthday = extractFieldFromJson(responseBody, "birthday");
                    String address = extractFieldFromJson(responseBody, "address");
                    String email = extractFieldFromJson(responseBody, "email");
                    String phone = extractFieldFromJson(responseBody, "phone");
                    String password = extractFieldFromJson(responseBody, "password");
                    String quartier = extractFieldFromJson(responseBody, "quartier");
                    if (isResident) {
                        // Create a Resident object with the quartier information
                        return new Resident(
                                name,
                                lastName,
                                password,
                                birthday,
                                new Address(address, address, address),
                                email,
                                phone);
                    } else {
                        String cityID = extractFieldFromJson(responseBody, "cityID");
                        return new Intervenant(
                                name,
                                lastName,
                                password,
                                birthday,
                                phone,
                                email,
                                new Address(address, address, address),
                                cityID);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching user data: " + e.getMessage());
        }
        return null;
    }
    /**
     * Méthode pour vérifier si l'utilisateur se trouve dans le dossier spécifié
     *
     * @return true si l'utilisateur est trouvé
     */
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
    /**
     * Méthode utilitaire pour extraire un field d'une String json
     *
     * @param json la chaîne de caractère contenant les champs
     * @param field le champ à extraire
     *
     * @return le champ extrait
     */
    private String extractFieldFromJson(String json, String field) {
        try {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Méthode pour enregistrer un nouveau résident
     * @param resident le résident à enregistrer
     */
    public void register(Resident resident) {
        try {
            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(resident.getEmail())
                    .setPassword(resident.getPassword())
                    .setDisplayName(resident.getName() + " " + resident.getLastname()));
            UserRecord userRecord = future.get(); // Block until operation completes
            String uid = userRecord.getUid();
            // Save user to Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("residents").child(uid);
            userFolderRef.setValueAsync(resident);
            UserSession.getInstance().setUser(resident);
            UserSession.getInstance().setUserId(uid);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Méthode pour enregistrer un nouvel intervenant
     * @param intervenant l'intervenant à enregistrer
     */
    public void register(Intervenant intervenant) {
        try {
            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(intervenant.getEmail())
                    .setPassword(intervenant.getPassword())
                    .setDisplayName(intervenant.getName() + " " + intervenant.getLastname()));
            UserRecord userRecord = future.get(); // Block until operation completes
            String uid = userRecord.getUid(); // Get the unique UID of the user
            // Save Intervenant to Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("intervenants").child(uid);
            // Save the Intervenant data under the UID folder
            userFolderRef.setValueAsync(intervenant);
            UserSession.getInstance().setUser(intervenant);
            UserSession.getInstance().setUserId(uid);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Méthode pour mettre à jours les informations du profil
     * @param userId l'id de l'utilisateur
     * @param changes {@link HashMap} (id, changes) des champs modifiés
     */
    public void updateInfo(String userId, HashMap<String, String> changes) {}
}
