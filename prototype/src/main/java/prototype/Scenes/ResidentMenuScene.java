package prototype.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class ResidentMenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultProjectsButton, consultEntravesButton, searchProjectsButton, settingsButton, requestButton,
            problemButton, logoutButton, notificationButton;
    private Text title;

    public ResidentMenuScene(SceneController sceneController) {
        super(sceneController);

        // Initialize buttons
        this.consultProjectsButton = new Button("Consulter les travaux en cours");
        this.consultEntravesButton = new Button("Consulter les entraves routières causées par les travaux en cours");
        this.searchProjectsButton = new Button("Rechercher des travaux");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.notificationButton = new Button("Consulter les notifications");
        this.settingsButton = new Button("Personnaliser le profil");
        this.problemButton = new Button("Signaler un problème");
        this.logoutButton = new Button("Déconnexion");

        // Initialize layout elements
        this.vBox = new VBox();
        this.titleBox = new HBox();
        this.title = new Text("Bienvenu dans le menu des résidents!");
    }

    public void setScene() {
        this.root.setCenter(vBox);
        this.root.setTop(this.titleBox);
        this.title.setFont(new Font("Arial", 25));
        this.titleBox.getChildren().add(this.title);
        this.titleBox.setAlignment(Pos.CENTER);

        // Add UI elements to the vBox
        this.vBox.getChildren().addAll(
                this.consultProjectsButton,
                this.searchProjectsButton,
                this.consultEntravesButton,
                this.settingsButton,
                this.requestButton,
                this.problemButton,
                this.notificationButton,
                this.logoutButton);

        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);

        // Define button actions for navigating to different scenes
        this.consultProjectsButton.setOnMouseClicked((consultAction) -> {
            // Navigate to the scene where the user can consult ongoing projects
            this.sceneController.newScene("consultProjects");
        });

        this.consultEntravesButton.setOnMouseClicked((entravesAction) -> {
            // Navigate to the scene where the user can consult road obstructions
            this.sceneController.newScene("consultEntraves");
        });

        this.searchProjectsButton.setOnMouseClicked((searchAction) -> {
            // Navigate to the scene where the user can search for projects
            this.sceneController.newScene("searchProjects");
        });

        this.settingsButton.setOnMouseClicked((settingsAction) -> {
            // Navigate to the settings scene
            this.sceneController.newScene("settings");
        });

        this.requestButton.setOnMouseClicked((requestAction) -> {
            // Navigate to the scene for submitting a work request
            this.sceneController.newScene("request");
        });

        this.problemButton.setOnMouseClicked((problemAction) -> {
            // Navigate to the scene for reporting a problem
            this.sceneController.newScene("problem");
        });

        this.notificationButton.setOnMouseClicked((notificationAction) -> {
            // Navigate to the notifications scene
            this.sceneController.newScene("notifications");
        });

        this.logoutButton.setOnMouseClicked((logoutAction) -> {
            // Log the user out and navigate to the launch scene
            this.sceneController.newScene("launch");
        });
    }
}
