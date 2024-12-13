package prototype.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Platform;
import javafx.scene.control.Label;
import prototype.Controllers.SceneController;
import prototype.Users.Intervenant;
import prototype.Users.Resident;
import prototype.Users.UserSession;
import prototype.Users.Utilisateur;

public class LoginServices {

    private static final String API_KEY = "AIzaSyD95B1nhXm9xVTn_QJjXpQD-FDEqlG6cKM";
    private static final String DATABASE_URL = "https://maville-18aa2-default-rtdb.firebaseio.com/";

    /**
     * Authenticate the user with Firebase using email and password.
     */
    public void authenticateWithFirebase(String email, String password, SceneController sceneController,
            Label statusLabel) {
        new Thread(() -> {
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
                        checkUserRole(userId, sceneController, statusLabel);
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
        }).start();
    }

    /**
     * Check the role of the user in Firebase database (residents or intervenants).
     */
    private void checkUserRole(String userId, SceneController sceneController, Label statusLabel) {
        new Thread(() -> {
            try {
                // Check the 'residents' folder
                String residentUrl = DATABASE_URL + "residents/" + userId + ".json";
                if (isUserInFolder(residentUrl)) {
                    // Fetch and save Resident data
                    Resident resident = fetchUserData(residentUrl, true);
                    UserSession.getInstance()
                            .setUser(new Resident(resident.getName(), resident.getLastName(), resident.getBirthday(),
                                    resident.getAddress(), resident.getEmail(), resident.getPhone(),
                                    resident.getPassword(), resident.getQuartier()));

                    Platform.runLater(() -> {
                        statusLabel.setText("Welcome, Resident!");
                        sceneController.newScene("residentMenu");
                    });
                    return;
                }

                // Check the 'intervenants' folder
                String intervenantUrl = DATABASE_URL + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    // Fetch and save Intervenant data
                    Intervenant intervenant = fetchUserData(intervenantUrl, false);
                    UserSession.getInstance().setUser(new Intervenant(intervenant.getName(), intervenant.getLastName(),
                            intervenant.getPassword(), intervenant.getBirthday(), intervenant.getPhone(),
                            intervenant.getAddress(), intervenant.getEmail(), intervenant.getCityID()));

                    Platform.runLater(() -> {
                        statusLabel.setText("Welcome, Intervenant!");
                        sceneController.newScene("intervenantMenu");
                    });
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
        }).start();
    }

    /**
     * Fetch and populate user data from Firebase.
     */
    private <T extends Utilisateur> T fetchUserData(String urlString, boolean isResident) {
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
                        return (T) new Resident(name, lastName, birthday, address, email, phone, password,
                                quartier);
                    } else {
                        String cityID = extractFieldFromJson(responseBody, "cityID");
                        return (T) new Intervenant(name, lastName, password, birthday, phone, address, email, cityID);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching user data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Check if the user exists in the specified folder.
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
     * Extract a specific field from a JSON response.
     */
    private String extractFieldFromJson(String json, String field) {
        try {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            return null;
        }
    }
}
