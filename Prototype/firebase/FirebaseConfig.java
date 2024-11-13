package Prototype.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {

    public static void initializeFirebase() throws IOException {
        String path = "D:\\UNI\\CURRENT COURSES\\IFT 2255\\Projet\\Ma-Ville-EQ19\\Prototype\\resources\\maville-18aa2-firebase-adminsdk-suusz-8ebb3b3abe.json";

        try {
            // Debug: Print the absolute path of the service account file
            System.out.println("Initializing Firebase...");
            System.out.println("Service account file path: " + new java.io.File(path).getAbsolutePath());

            // Load service account JSON file
            FileInputStream serviceAccount = new FileInputStream(path);
            System.out.println("Service account file loaded successfully.");

            // Configure Firebase options
            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://maville-18aa2-default-rtdb.firebaseio.com/")
                    .build();
            System.out.println("Firebase options configured.");

            // Initialize Firebase
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully.");

        } catch (IOException e) {
            // Log the error with detailed information
            System.err.println("Failed to initialize Firebase:");
            e.printStackTrace();
            throw e; // Rethrow exception to ensure the caller is aware
        }
    }
}
