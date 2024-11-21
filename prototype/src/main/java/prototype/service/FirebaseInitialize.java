package prototype.service;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseInitialize {

    private static Firestore firestore;

    /**
     * Initializes Firebase and Firestore with the service account credentials.
     */
    public static void initialize() {
        if (firestore != null) {
            System.out.println("Firebase Firestore is already initialized.");
            return; // Prevent re-initialization
        }

        try {
            // Provide the absolute path to the service account file
            String serviceAccountPath = "D:\\UNI\\CURRENT COURSES\\IFT 2255\\ProjetMaven\\serviceAccount.json";

            try (FileInputStream serviceAccount = new FileInputStream(serviceAccountPath)) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                // Initialize FirebaseApp only if not already initialized
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }

                // Initialize Firestore
                firestore = FirestoreClient.getFirestore();
                System.out.println("Firebase Firestore initialized successfully.");
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Provides access to the Firestore instance.
     *
     * @return Firestore instance
     */
    public static Firestore getFirestore() {
        if (firestore == null) {
            throw new IllegalStateException("Firestore has not been initialized. Call initialize() first.");
        }
        return firestore;
    }
}
