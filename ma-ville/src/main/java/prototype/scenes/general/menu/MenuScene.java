package prototype.scenes.general.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;

import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.controllers.ApiController;
import prototype.services.FirebaseCallback;
import prototype.users.Resident;
import prototype.users.UserSession;
import prototype.users.Utilisateur;
import prototype.notifications.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene de menu. Permet d'acceder aux différentes options selon le type d'utilisateur ({@link prototype.users.Resident résident} ou {@link prototype.users.Intervenant intervenant})
 *
 * <p> Certaines fonctionnalités et bouton ne sont pas visible, dépendement de l'utilisateur. La visibilité est déterminée par {@link Utilisateur#isIntervenant()} </p>
 *
 *
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class MenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultButton, entraveButton, settingsButton, requestButton, logoutButton, notificationButton, intervenantProjectButton, intervenantSubmitProjectButton;
    private Text title;
    private Button consultRequestButton;
    private Utilisateur user;
    private Text notificationCountText;
    private String userBorough;
    private int notificationCount = 0;
    private ApiController apiController;
    private HBox notificationBox; // Declare notificationBox here

    /**
     * Constructeur
     * @param sceneController
     */
    public MenuScene(SceneController sceneController) {
        super(sceneController);
        this.apiController = new ApiController(); // Initialize ApiController
        this.userBorough = UserSession.getInstance().getUser().getAddress().getBorough();


        // Initialize Buttons
        this.consultButton = new Button("Consulter des travaux en cours");
        this.consultButton.setId("consultButton");
        this.entraveButton = new Button("Consulter les entraves routières");
        this.entraveButton.setId("entraveButton");
        this.settingsButton = new Button("Personnaliser le profil");
        this.settingsButton.setId("settingsButton");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.requestButton.setId("requestButton");
        this.notificationButton = new Button();
        this.notificationButton.setId("notificationButton");
        this.logoutButton = new Button("Déconnexion");
        this.logoutButton.setId("logoutButton");
        this.intervenantSubmitProjectButton = new Button("Soumettre un projet");
        this.intervenantSubmitProjectButton.setId("intervenantSubmitProjectButton");
        this.intervenantProjectButton = new Button("Consulter mes projets");
        this.intervenantProjectButton.setId("intervenantProjectButton");

        this.vBox = new VBox();
        this.titleBox = new HBox();
        this.title = new Text("Bienvenu dans le menu");

        this.consultRequestButton = new Button("Consulter la liste des requêtes de travail");
        this.consultRequestButton.setId("consultRequestButton");

        this.user = UserSession.getInstance().getUser();
        this.notificationCountText = new Text("0");
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.vBox);
        this.title.setFont(new Font("Arial", 25));
        this.titleBox.getChildren().add(this.title);
        this.titleBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(
                this.consultButton,
                this.entraveButton,
                this.settingsButton,
                this.requestButton,
                this.consultRequestButton,
                this.intervenantSubmitProjectButton,
                this.intervenantProjectButton,
                this.logoutButton
        );
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);

        BorderPane topPane = new BorderPane();
        this.root.setTop(topPane);

        topPane.setCenter(this.titleBox);
        this.titleBox.setAlignment(Pos.TOP_CENTER);
        ImageView notificationIcon = new ImageView(new Image(
                getClass().getResource("/prototype/assets/bell.png").toExternalForm()
        ));
        notificationIcon.setFitHeight(15);
        notificationIcon.setPreserveRatio(true);
        this.notificationButton.setGraphic(notificationIcon);
        this.notificationButton.setAlignment(Pos.CENTER);
        notificationBox = new HBox(5); // Initialize notificationBox here
        notificationBox.getChildren().addAll(this.notificationCountText,this.notificationButton);
        notificationBox.setMargin(this.notificationButton, new Insets(10));
        notificationBox.setAlignment(Pos.CENTER);
        this.notificationCountText.setFont(new Font("Arial", 16));
        this.notificationCountText.setFill(Color.WHITE);


        topPane.setRight(notificationBox);

        // Fetch notifications and update the notification count
        if (!user.isIntervenant())
            fetchNotifications();

        // Intervenant option access
        this.consultRequestButton.setVisible(user.isIntervenant());
        this.consultRequestButton.setManaged(user.isIntervenant());

        this.intervenantProjectButton.setVisible(user.isIntervenant());
        this.intervenantProjectButton.setManaged(user.isIntervenant());

        this.intervenantSubmitProjectButton.setVisible(user.isIntervenant());
        this.intervenantSubmitProjectButton.setManaged(user.isIntervenant());

        this.notificationButton.setVisible(!user.isIntervenant());
        this.notificationButton.setManaged(!user.isIntervenant());

        // Resident option access
        this.requestButton.setVisible(!user.isIntervenant());
        this.requestButton.setManaged(!user.isIntervenant());

        this.consultButton.setOnMouseClicked(event -> newSceneAction(event, "consultProjects"));
        this.settingsButton.setOnMouseClicked(event -> newSceneAction(event, "settings"));
        this.requestButton.setOnMouseClicked(event -> newSceneAction(event, "request"));
        this.notificationButton.setOnMouseClicked(event -> newSceneAction(event, "notifications"));
        this.intervenantProjectButton.setOnMouseClicked(event -> newSceneAction(event, "intervenantProject"));
        this.intervenantSubmitProjectButton.setOnMouseClicked(event -> newSceneAction(event, "intervenantSubmitProjectButton"));
        this.logoutButton.setOnMouseClicked((event) -> {
            UserSession.getInstance().disconnect();
            this.sceneController.newScene("launch");
        });
        this.entraveButton.setOnMouseClicked(event -> newSceneAction(event, "consultEntraves"));
        this.consultRequestButton.setOnMouseClicked(event -> newSceneAction(event, "consultRequest"));
    }

    /**
     * Fetch notifications and update NotificationState
     */
    private void fetchNotifications() {
        try {
            // Calling getNotifications with false for setNotificationToRead
            apiController.getNotifications(new FirebaseCallback<ArrayList<Notification>>() {
                @Override
                public void onSuccessReturn(ArrayList<Notification> notifications) {
                    // Ensure the UI is updated on the JavaFX application thread
                    Platform.runLater(() -> {

                        Resident currentResident = UserSession.getInstance().getResident();
                        List<String> selectedQuartiers = currentResident.getSelectedQuartiers();

                        notificationCount = 0;

                        // Filter notifications based on the user's borough or selected quartiers and unread status
                        for (Notification notification : notifications) {
                            String notificationQuartier = notification.getQuartier();

                            // Check if notification is in the user's borough or selected quartiers, and if it's unread
                            if ((userBorough.equalsIgnoreCase(notificationQuartier) ||
                                    (selectedQuartiers != null && selectedQuartiers.stream()
                                            .anyMatch(q -> q.equalsIgnoreCase(notificationQuartier))))) {
                                System.out.println("PRINTING NOTIFICATION READ BOOLEAN : "+notification.isLu());

                                if (!notification.isLu()) {
                                    System.out.println("notification unread : "+ !notification.isLu());
                                    notificationCount++;
                                }
                            }
                        }

                        // Set the notification count to the global state

                        // Update the notification count text
                        notificationCountText.setText(String.valueOf(notificationCount));
                    });
                }

                @Override
                public void onFailureReturn(String errorMessage) {
                    Platform.runLater(() -> {
                        System.err.println("Failed to fetch notifications: " + errorMessage);
                        Text errorText = new Text("Erreur lors de la récupération des notifications");
                        vBox.getChildren().add(errorText); // Add error message to the scene
                    });
                }

                @Override
                public void onSuccess() {}
            }, false); // Passing 'false' to setNotificationsToRead
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                Text errorText = new Text("Erreur lors de la récupération des notifications");
                errorText.setFill(javafx.scene.paint.Color.WHITE);
                vBox.getChildren().add(errorText); // Add error message to the scene
            });
        }
    }



}