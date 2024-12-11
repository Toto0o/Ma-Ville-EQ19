package prototype.api.firebase;

import java.util.ArrayList;
import prototype.projects.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Api Firebase permettant de 
 * <ul>
 *  <li> Charger les requêtes faites par les résidents avec {@link #getRequests()} </li>
 *  <li> Sauvegarder les requêtes faites par les résidents avec {@link #saveRequest(request)} </li>
 * </ul>
 * 
 * <p> Les méthode sont accédés par {@link ApiController} </p>
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */


public class RequestApi {

    private ArrayList<Request> requests;

    public RequestApi() {
        this.requests = new ArrayList<>();
    }

    public ArrayList<Request> getRequests() {
        fetchRequest();
        return this.requests;
    }


    /**
     * Charge les requêtes avec {@link FirebaseDatabase} et {@link DatabaseReference}
     * 
     * <p> Les données chargées pour chaques requêtes sont instancié dans une {@link Request} avant d'être ajouté a une 
     * {@link ArrayList} accesible avec {@link #getRequests()}
     */
    private void fetchRequest() {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference requestFolderRef = database.getReference("requests");

        // Fetch the requests data
        requestFolderRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear previous requests data (if any)
                
                if (requests != null) {
                    requests.clear();
                }

                // Loop through all the children of "requests" node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve each request's details from the snapshot
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String quartier = snapshot.child("quartier").getValue(String.class);

                    // Create a Request object and add it to the list
                    Request request = new Request(title, description, type, date, status, quartier);
                    requests.add(request);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle any error that might occur while fetching data
                System.err.println("Failed to fetch data: " + error.getMessage());
            }
        });
    }
}
