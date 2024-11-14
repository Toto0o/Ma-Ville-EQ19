package prototype.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseInitialize {

    public static Firestore firestore;

    public static void initialize() throws FileNotFoundException, IOException {
        // Get the path from the environment variable
        String serviceAccountPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        // Check if the environment variable is set
        if (serviceAccountPath == null) {
            throw new IllegalStateException("Environment variable GOOGLE_APPLICATION_CREDENTIALS is not set.");
        }

        try (FileInputStream serviceAccount = new FileInputStream(serviceAccountPath)) {

            // Initialize Firebase with the service account credentials
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Initialize Firebase
            FirebaseApp.initializeApp(options);

            // Initialize Firestore
            firestore = FirestoreClient.getFirestore();

            System.out.println("Firebase Firestore initialized successfully.");
        } catch (Exception e) {
            System.err.println("Firebase initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to access Firestore in other classes
    public static Firestore getFirestore() {
        return firestore;
    }
}
