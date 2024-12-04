package prototype.scenes.general.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;

import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class MenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultButton, entraveButton, settingsButton, requestButton, logoutButton, notificationButton;
    private Text title;
    private Button consultRequestButton;
    private Boolean intervenant;

    public MenuScene(SceneController sceneController, Boolean intervenant) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.entraveButton = new Button("Consulter les entraves routières");
        this.settingsButton = new Button("Personnaliser le profil");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.notificationButton = new Button();
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
        this.title.setFont(new Font("Arial", 25));
        this.titleBox.getChildren().add(this.title);
        this.titleBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(
            this.consultButton,
            this.entraveButton,
            this.settingsButton,
            this.requestButton,
            this.consultRequestButton,
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
        HBox notificationBox = new HBox();
        notificationBox.getChildren().add(this.notificationButton);
        notificationBox.setMargin(this.notificationButton, new Insets(10));
        topPane.setRight(notificationBox);



        this.consultRequestButton.setVisible(this.intervenant);
        this.consultRequestButton.setManaged(this.intervenant);

        this.requestButton.setVisible(!this.intervenant);
        this.requestButton.setManaged(!this.intervenant);


        this.consultButton.setOnMouseClicked(event -> newSceneAction(event, "consult project"));

        this.settingsButton.setOnMouseClicked(event -> newSceneAction(event, "settings"));

        this.requestButton.setOnMouseClicked(event -> newSceneAction(event, "request"));

        this.notificationButton.setOnMouseClicked(event -> newSceneAction(event, "notifications"));

        this.logoutButton.setOnMouseClicked((event) -> {
            this.sceneController.setIntervenant(false);
            this.sceneController.newScene("launch");
        } );

        this.entraveButton.setOnMouseClicked(event -> newSceneAction(event, "consult entraves"));

        this.consultRequestButton.setOnMouseClicked(event -> newSceneAction(event, "consult request"));
    }

}
