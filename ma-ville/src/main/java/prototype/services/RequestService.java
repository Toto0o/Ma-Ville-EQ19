package prototype.services;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import prototype.projects.Request;
import prototype.projects.Type;
import prototype.users.UserSession;

public class RequestService {
    // Firebase database reference URL
    private static final String DATABASE_URL = "https://maville-18aa2-default-rtdb.firebaseio.com/";
    private static final String REQUESTS_NODE = "requests";
    /**
     * Fetches requests from the Firebase database and passes the results to the
     * given callback.
     *
     * @param callback A Consumer that accepts a List of Request objects.
     */
    public ArrayList<Request> fetchRequests() {
        ArrayList<Request> requestsList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance(DATABASE_URL);
        DatabaseReference requestFolderRef = database.getReference(REQUESTS_NODE);
        requestFolderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String quartier = snapshot.child("quartier").getValue(String.class);
                    Request request = new Request(
                            title,
                            description,
                            Type.valueOf(type),
                            date,
                            status,
                            quartier,
                            UserSession.getInstance().getUser().getAddress().getStreet());
                    requestsList.add(request);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Failed to fetch data: " + error.getMessage());
            }
        });
        return requestsList;
    }

    public ArrayList<Request> getRequests() {
        return fetchRequests();
    }

    public static void saveRequest(Request request) {
        try {
            // Default status for a new request
            String status = "Pending";
            // Generate a random request ID
            String requestId = java.util.UUID.randomUUID().toString();
            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference requestFolderRef = database.getReference("requests").child(requestId);
            // Save request data under "requests/RequestID" node
            requestFolderRef.setValueAsync(request);
            System.out.println("Request saved to Firebase under ID: " + requestId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newRequest(Request request) {
        saveRequest(request);
    }
}
