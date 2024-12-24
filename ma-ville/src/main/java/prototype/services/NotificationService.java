package prototype.services;

import com.google.firebase.database.*;
import prototype.notifications.Notification;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    public ArrayList<Notification> getNotifications(FirebaseCallback<ArrayList<Notification>> callback, boolean setNotificationsToRead) throws Exception {
        ArrayList<Notification> notifications = new ArrayList<>();
        Resident currentResident = UserSession.getInstance().getResident();
        List<String> selectedQuartiers = currentResident.getSelectedQuartiers();
        String userBorough = UserSession.getInstance().getUser().getAddress().getBorough();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("notifications");

        // Use 'addListenerForSingleValueEvent' to fetch the data once
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Datasnapshot : " + dataSnapshot.getChildren());
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Notification notification = snapshot.getValue(Notification.class);
                        notification.setFirebaseKey(snapshot.getKey()); // Set Firebase key
                        String notificationQuartier = notification.getQuartier();


                        if (setNotificationsToRead  && (userBorough.equalsIgnoreCase(notificationQuartier) ||  !notification.isLu() && selectedQuartiers != null && selectedQuartiers.stream()
                                .anyMatch(q -> q.equalsIgnoreCase(notificationQuartier)))) {
                            // Update the 'lu' field to true
                            notification.setLu(true);

                            // Save the updated notification back to Firebase
                            saveNotificationChanges(notification);
                        }

                        notifications.add(notification);
                    }
                    callback.onSuccessReturn(notifications);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailureReturn("Error parsing data.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                callback.onFailureReturn(databaseError.getMessage());
            }
        });

        return notifications;
    }


    public void saveNotificationChanges(Notification notification) {
        try {
            // Get the notification's unique Firebase key
            String notificationKey = notification.getFirebaseKey(); // Assuming Notification has a method to get the Firebase key

            // Reference to the notifications node in Firebase
            DatabaseReference notificationRef = FirebaseDatabase.getInstance(
                    "https://maville-18aa2-default-rtdb.firebaseio.com/"
            ).getReference("notifications").child(notificationKey);

            // Update the notification in Firebase (set the values to the latest notification object)
            notificationRef.setValueAsync(notification);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating notification: " + e.getMessage());
        }
    }




    public void addNotification(Notification notification) {
        try {
            String notificationId = java.util.UUID.randomUUID().toString();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                    "https://maville-18aa2-default-rtdb.firebaseio.com/"
            ).getReference("notifications").child(notificationId);

            databaseReference.setValueAsync(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}