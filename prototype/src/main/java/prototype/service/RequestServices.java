package prototype.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import prototype.Requests.Request;

public class RequestServices {

    // Firebase database reference URL
    private static final String DATABASE_URL = "https://maville-18aa2-default-rtdb.firebaseio.com/";
    private static final String REQUESTS_NODE = "requests";

    /**
     * Fetches requests from the Firebase database and passes the results to the
     * given callback.
     *
     * @param callback A Consumer that accepts a List of Request objects.
     */
    public void fetchRequests(Consumer<List<Request>> callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(DATABASE_URL);
        DatabaseReference requestFolderRef = database.getReference(REQUESTS_NODE);

        requestFolderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Request> requestsList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String quartier = snapshot.child("quartier").getValue(String.class);

                    Request request = new Request(title, description, type, date, status, quartier);
                    requestsList.add(request);
                }

                // Pass the fetched requests to the callback
                callback.accept(requestsList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch data: " + error.getMessage());
            }
        });
    }

    public static void saveRequest(String title, String description, String type, String date, String quartier) {
        try {
            // Default status for a new request
            String status = "Pending";

            // Generate a random request ID
            String requestId = java.util.UUID.randomUUID().toString();

            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("requests").child(requestId);

            // Create a Request object with collected data
            Request request = new Request(title, description, type, date, status, quartier);

            // Save request data under "requests/RequestID" node
            requestFolderRef.setValueAsync(request);

            System.out.println("Request saved to Firebase under ID: " + requestId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
