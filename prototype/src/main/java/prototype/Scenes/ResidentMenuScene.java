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
    private Button consultButton, settingsButton, requestButton, problemButton, logoutButton, notificationButton;
    private Text title;

    public ResidentMenuScene(SceneController sceneController) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.settingsButton = new Button("Personnaliser le profil");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.problemButton = new Button("Siganler un problème");
        this.notificationButton = new Button("Consulter les notifications");
        this.logoutButton = new Button("Déconnexion");

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
        this.vBox.getChildren().addAll(
                this.consultButton,
                this.settingsButton,
                this.requestButton,
                this.problemButton,
                this.notificationButton,
                this.logoutButton);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);

        this.consultButton.setOnMouseClicked((consultAction) -> {
            this.sceneController.newScene("consult");
        });

        this.settingsButton.setOnMouseClicked((settingsAction) -> {
            this.sceneController.newScene("settings");
        });

        this.requestButton.setOnMouseClicked((requestAction) -> {
            this.sceneController.newScene("request");
        });

        this.problemButton.setOnMouseClicked((problemAction) -> {
            this.sceneController.newScene("problem");
        });

        this.notificationButton.setOnMouseClicked((notificationAction) -> {
            this.sceneController.newScene("notifications");
        });

        this.logoutButton.setOnMouseClicked((logoutAction) -> {
            this.sceneController.newScene("launch");
        });
    }

}
