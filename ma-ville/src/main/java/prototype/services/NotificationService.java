package prototype.services;

import com.google.firebase.database.*;
import prototype.notifications.Notification;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Service de connexion Api pour les notifications
 * 
 * <p>Permet de charger les notifications avec {@link #getNotifications(FirebaseCallback, boolean)} </p>
 * <p>Permet d'ajouter une notification à la base de donnée avec {@link #addNotification(Notification)}</p>
 * <p>Permet d'updater une notification (changement de le status de lu ou non lu) avec {@link #saveNotificationChanges(Notification)}</p>
 */
public class NotificationService {

    /**
     * Charge les notifications dans la base de donnée avec {@link FirebaseDatabase}
     * @param callback le callback de la méthode sur fin de la lecture de la base de donnée
     * @param setNotificationsToRead notification lu ou non lu
     * @return {@link ArrayList}&lt;{@link Notification}&gt;
     * @throws Exception sur erreur de chargement
     */
    public ArrayList<Notification> getNotifications(FirebaseCallback<ArrayList<Notification>> callback, boolean setNotificationsToRead) throws Exception {
        ArrayList<Notification> notifications = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("notifications");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Notification notification = snapshot.getValue(Notification.class);
                        notification.setFirebaseKey(snapshot.getKey()); // Set Firebase key

                        if (setNotificationsToRead && !notification.isLu()) {
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

    /**
     * Méthode pour updater une notifications avec {@link FirebaseDatabase}
     * @param notification à enregister
     */
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


    /**
     * Méthode pour ajouter une nouvelle notification avec {@link FirebaseDatabase}
     * <p>Associe un id avec {@link UUID#randomUUID()}</p>
     * @param notification à enregister
     */
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