package prototype.services;

import com.google.firebase.database.*;
import prototype.notifications.Notification;
import prototype.users.UserSession;

import java.util.ArrayList;

public class NotificationService {

    public ArrayList<Notification> getNotifications(FirebaseCallback<ArrayList<Notification>> callback) {
        ArrayList<Notification> notifications = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance(
                "https://maville-18aa2-default-rtdb.firebaseio.com/"
        ).getReference("notifications");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    System.out.println(dataSnapshot == null);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Notification notification = snapshot.getValue(Notification.class);
                        if (notification.getUsersId().contains(UserSession.getInstance().getUserId())) {
                            notifications.add(notification);
                        }
                    }
                    callback.onSucessReturn(notifications);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
        return notifications;
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
