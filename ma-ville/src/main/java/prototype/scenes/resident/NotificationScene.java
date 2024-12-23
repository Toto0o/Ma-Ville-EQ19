package prototype.scenes.resident;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.notifications.Notification;
import prototype.scenes.Scenes;
import prototype.services.FirebaseCallback;
import prototype.services.NotificationService;
import prototype.services.ServiceSession;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationScene extends Scenes {

    private VBox vbox;
    private Button menu;
    private Text title, subtitle;
    private String userBorough;
    private ApiController apiController;
    private int notificationCount = 0;


    public NotificationScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.menu = new Button("Menu");
        this.apiController = ServiceSession.getInstance().getController();
        // Fetch notifications when the scene is created

        fetchNotifications();

        // Setting title and subtitle
        this.title = new Text("Notifications");
        this.userBorough = UserSession.getInstance().getUser().getAddress().getBorough();
        this.subtitle = new Text("Les Notifications dans votre quartier (" + userBorough + ")");


        Resident currentResident = UserSession.getInstance().getResident();
        List<String> selectedQuartiers = currentResident.getSelectedQuartiers();

        vbox.getChildren().addAll(title, subtitle);
    }

    @Override
    public void setScene() {
        this.root.setStyle("-fx-background-color: #2f2f2f;"); // Dark background
        this.root.setCenter(this.vbox);
        this.title.setFont(new Font("Arial", 30));
        this.title.setStyle("-fx-font-weight: bold; -fx-fill: white;");
        this.subtitle.setFont(new Font("Arial", 20));
        this.subtitle.setStyle("-fx-font-weight: bold; -fx-fill: white;");
        this.root.setTop(this.menu);
        this.vbox.setAlignment(Pos.CENTER);

        // Action for the menu button
        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });
    }

    private void fetchNotifications() {
        try {
            apiController.getNotifications(new FirebaseCallback<ArrayList<Notification>>() {
                @Override
                public void onSuccessReturn(ArrayList<Notification> notifications) {
                    // Ensure the UI is updated on the JavaFX application thread
                    Platform.runLater(() -> {
                        VBox notificationBox = new VBox(10);
                        notificationBox.setStyle("-fx-border-color: #000; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #333;");

                        Resident currentResident = UserSession.getInstance().getResident();
                        List<String> selectedQuartiers = currentResident.getSelectedQuartiers();

                        // Create a Set to store unique firebaseKeys
                        Set<String> uniqueFirebaseKeys = new HashSet<>();

                        // Filter notifications based on the user's borough or selected quartiers
                        for (Notification notification : notifications) {
                            String notificationQuartier = notification.getQuartier();
                            String firebaseKey = notification.getFirebaseKey();

                            // Skip the notification if its firebaseKey has already been seen
                            if (uniqueFirebaseKeys.contains(firebaseKey)) {
                                continue;
                            }

                            // Add the firebaseKey to the set to track it
                            uniqueFirebaseKeys.add(firebaseKey);

                            // Check if the notification's borough matches the user's or one of the selected quartiers
                            if (userBorough.equalsIgnoreCase(notificationQuartier) ||
                                    (selectedQuartiers != null && selectedQuartiers.stream()
                                            .anyMatch(q -> q.equalsIgnoreCase(notificationQuartier)))) {

                                // Add the notification to the box
                                VBox notificationItem = notification.afficher();

                                // Add "Read" or "Unread" based on the `lu` status
                                Text readStatus = new Text(notification.isLu() ? "Read" : "Unread");
                                readStatus.setFont(new Font("Arial", 14));
                                readStatus.setStyle("-fx-fill: white;");

                                // Add the status to the notification item
                                notificationItem.getChildren().add(readStatus);

                                // Add the notification item to the notification box
                                notificationBox.getChildren().add(notificationItem);
                            }
                        }

                        // Update the notification count in the UI
                        Text notificationCountText = new Text("Notifications: " + notifications.size());
                        notificationCountText.setFont(new Font("Arial", 18));
                        notificationCountText.setStyle("-fx-font-weight: bold; -fx-fill: white;");
                        notificationBox.getChildren().add(notificationCountText);

                        // Set the scroll pane
                        ScrollPane scrollPane = new ScrollPane(notificationBox);
                        scrollPane.setFitToWidth(true);
                        scrollPane.setStyle("-fx-background-color: #fff;");
                        vbox.getChildren().add(scrollPane);
                    });
                }

                @Override
                public void onFailureReturn(String errorMessage) {
                    Platform.runLater(() -> {
                        System.err.println("Failed to fetch notifications: " + errorMessage);
                        Text errorText = new Text("Erreur lors de la récupération des notifications");
                        errorText.setFill(javafx.scene.paint.Color.WHITE);
                        vbox.getChildren().add(errorText);
                    });
                }

                @Override
                public void onSuccess() {}
            }, true); // Passing 'true' to setNotificationsToRead
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                Text errorText = new Text("Erreur lors de la récupération des notifications");
                errorText.setFill(javafx.scene.paint.Color.WHITE);
                vbox.getChildren().add(errorText);
            });
        }
    }








}
