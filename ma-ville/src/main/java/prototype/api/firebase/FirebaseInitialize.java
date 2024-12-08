package prototype.api.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseInitialize {

    private static Firestore firestore;
    private static FirebaseAuth firebaseAuth; // Declare FirebaseAuth

    public static void initialize() {
        if (firestore != null) {
            System.out.println("Firebase Firestore is already initialized.");
            return;
        }

        try {
            String serviceAccountPath = "\\resources\\prototype\\serviceAccount.json";

            try (FileInputStream serviceAccount = new FileInputStream(serviceAccountPath)) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                // Initialize FirebaseApp
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }

                // Initialize Firestore and Auth
                firestore = FirestoreClient.getFirestore();
                firebaseAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth
                System.out.println("Firebase Firestore and Auth initialized successfully.");
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Firestore getFirestore() {
        if (firestore == null) {
            throw new IllegalStateException("Firestore has not been initialized. Call initialize() first.");
        }
        return firestore;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            throw new IllegalStateException("FirebaseAuth has not been initialized. Call initialize() first.");
        }
        return firebaseAuth;
    }

    // Method to verify Firebase ID Token and extract UID
    public static String getUidFromToken(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        return decodedToken.getUid(); // Extract UID from the decoded token
    }
    
}
