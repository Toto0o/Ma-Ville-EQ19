package prototype.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import prototype.users.*;

/**
 * Connexion Api avec firebase pour traiter les actions relatives à l'utilisateur
 *
 * <p>Utiliser {@link #authenticateWithFirebase(String, String, FirebaseCallback)} pour l'authentification</p>
 * <p>Utiliser {@link #register(Resident)} pour enregistrer un résident</p>
 * <p>Utiliser {@link #register(Intervenant)} pour enregistrer un intervenant</p>
 */
public class UserServices {
    private static final String API_KEY = "AIzaSyD95B1nhXm9xVTn_QJjXpQD-FDEqlG6cKM";
    private static final String DATABASE_URL = "https://maville-18aa2-default-rtdb.firebaseio.com/";
    private final List<Exception> exceptionsList;

    public UserServices() {
        this.exceptionsList = new ArrayList<>();
    }

    /**
     * Atuthentifier l'utilisateur avec Firebase en utilisant l'email et le mot de passe
     *
     * <p><a href = "https://cloud.google.com/identity-platform/docs/reference/rest">identitytoolkit.googleapis.com</a> </p>
     *
     * @param email le email de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     */
    public void authenticateWithFirebase(String email, String password, FirebaseCallback callback) throws IllegalArgumentException {
        Thread thread = new Thread(() -> {
            exceptionsList.clear();
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
                        getUser(userId, callback);


                    }
                } else {
                    exceptionsList.add(new IllegalArgumentException("Invalid email or password"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!exceptionsList.isEmpty()) {
             throw (IllegalArgumentException) exceptionsList.get(0);}
    }
    /**
     * Détermine le rôle de l'utilisateur selon son emplacement dans la base de donnée
     */
    private String checkUserRole(String userId) {
        final String[] response = new String[1];
        Thread thread = new Thread(() -> {
            try {
                // Check the 'residents' folder
                String residentUrl = DATABASE_URL + "residents/" + userId + ".json";
                if (isUserInFolder(residentUrl)) {
                    response[0] = "residents";
                }
                // Check the 'intervenants' folder
                String intervenantUrl = DATABASE_URL + "intervenants/" + userId + ".json";
                if (isUserInFolder(intervenantUrl)) {
                    response[0] = "intervenants";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
            return response[0];
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
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
            userFolderRef.setValueAsync(resident).get();
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
            userFolderRef.setValueAsync(intervenant).get();
            UserSession.getInstance().setUser(intervenant);
            UserSession.getInstance().setUserId(uid);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void getUser(String userId, FirebaseCallback<Utilisateur> callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference(checkUserRole(userId)).child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Utilisateur utilisateur;
                    if (checkUserRole(userId).equals("residents")) utilisateur = dataSnapshot.getValue(Resident.class);
                    else utilisateur = dataSnapshot.getValue(Intervenant.class);
                    UserSession.getInstance().setUser(utilisateur);
                    callback.onSucess();

                } catch (Exception e) {
                    e.printStackTrace();
                }
               }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public ArrayList<Horaire> getPreferencesHoraires() {
        ArrayList<Horaire> horaires = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("residents");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Utilisateur utilisateur = ds.getValue(Resident.class);
                    horaires.add(utilisateur.getHoraire());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return horaires;
    }

    public void updateUser() {
        try {
            Utilisateur utilisateur = UserSession.getInstance().getUser();
            DatabaseReference databaseReference;
            if (utilisateur instanceof Resident resident) {
                databaseReference = FirebaseDatabase.getInstance(
                        "https://maville-18aa2-default-rtdb.firebaseio.com/"
                ).getReference("residents");
            } else {
                databaseReference = FirebaseDatabase.getInstance(
                        "https://maville-18aa2-default-rtdb.firebaseio.com/"
                ).getReference("intervenants");
            }
            databaseReference.child(UserSession.getInstance().getUserId())
                    .setValueAsync(utilisateur).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getResidents(FirebaseCallback<ArrayList<Utilisateur>> callbackRes) {

        ArrayList<Utilisateur> users = new ArrayList<>();

        DatabaseReference databaseReferenceRes = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("residents");
        databaseReferenceRes.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    users.add(ds.getValue(Resident.class));
                }
                callbackRes.onSucessReturn(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void getIntervenants(FirebaseCallback<ArrayList<Utilisateur>> callbackInter) {
        ArrayList<Utilisateur> users = new ArrayList<>();
        DatabaseReference databaseReferenceRes = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("intervenants");
        databaseReferenceRes.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    users.add(ds.getValue(Intervenant.class));
                }
                callbackInter.onSucessReturn(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

}
