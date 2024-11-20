package prototype.scenes.general;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class MenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultButton, settingsButton, requestButton, problemButton, logoutButton, notificationButton;
    private Text title;
    private Button consultRequestButton;
    private Boolean intervenant;

    public MenuScene(SceneController sceneController, Boolean intervenant) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.settingsButton = new Button("Personnaliser le profil");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.problemButton = new Button("Siganler un problème");
        this.notificationButton = new Button("Consulter les notifications");
        this.logoutButton = new Button("Déconnexion");

        this.vBox = new VBox();
        this.titleBox = new HBox();
        this.title = new Text("Bienvenu dans le menu");

        this.consultRequestButton = new Button("Consulter la liste des requêtes de travail");

        this.intervenant = intervenant;
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.vBox);
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
            this.consultRequestButton,
            this.logoutButton
        );
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);

        this.consultRequestButton.setVisible(this.intervenant);
        this.consultRequestButton.setManaged(this.intervenant);

        this.requestButton.setVisible(!this.intervenant);
        this.requestButton.setManaged(!this.intervenant);


        this.consultButton.setOnMouseClicked(event -> newSceneAction(event, "consult project"));

        this.settingsButton.setOnMouseClicked(event -> newSceneAction(event, "settings"));

        this.requestButton.setOnMouseClicked(event -> newSceneAction(event, "request"));

        this.problemButton.setOnMouseClicked(event -> newSceneAction(event, "problem"));

        this.notificationButton.setOnMouseClicked(event -> newSceneAction(event, "notifications"));

        this.logoutButton.setOnMouseClicked(event -> newSceneAction(event, "launch"));

        this.consultRequestButton.setOnMouseClicked(event -> newSceneAction(event, "consult request"));
    }

}
