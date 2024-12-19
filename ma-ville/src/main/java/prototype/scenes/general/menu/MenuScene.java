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
import prototype.users.Intervenant;
import prototype.users.UserSession;
import prototype.users.Utilisateur;

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
    private Button consultButton, entraveButton, settingsButton, requestButton, logoutButton, notificationButton, intervenantProjectButton;
    private Text title;
    private Button consultRequestButton;
    private Utilisateur user;

    /**
     * Constructeur
     * @param sceneController
     */
    public MenuScene(SceneController sceneController) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.entraveButton = new Button("Consulter les entraves routières");
        this.settingsButton = new Button("Personnaliser le profil");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.notificationButton = new Button();
        this.logoutButton = new Button("Déconnexion");
        this.intervenantProjectButton = new Button("Consulter mes projets");

        this.vBox = new VBox();
        this.titleBox = new HBox();
        this.title = new Text("Bienvenu dans le menu");

        this.consultRequestButton = new Button("Consulter la liste des requêtes de travail");

        this.user = UserSession.getInstance().getUser();
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
        HBox notificationBox = new HBox();
        notificationBox.getChildren().add(this.notificationButton);
        notificationBox.setMargin(this.notificationButton, new Insets(10));
        topPane.setRight(notificationBox);


        // Intervenant option access
        this.consultRequestButton.setVisible(user.isIntervenant());
        this.consultRequestButton.setManaged(user.isIntervenant());

        this.intervenantProjectButton.setVisible(user.isIntervenant());
        this.intervenantProjectButton.setManaged(user.isIntervenant());

        // Resident option access
        this.requestButton.setVisible(!user.isIntervenant());
        this.requestButton.setManaged(!user.isIntervenant());


        this.consultButton.setOnMouseClicked(event -> newSceneAction(event, "consultProjects"));

        this.settingsButton.setOnMouseClicked(event -> newSceneAction(event, "settings"));

        this.requestButton.setOnMouseClicked(event -> newSceneAction(event, "request"));

        this.notificationButton.setOnMouseClicked(event -> newSceneAction(event, "notifications"));

        this.intervenantProjectButton.setOnMouseClicked(event -> newSceneAction(event, "intervenantProject"));

        this.logoutButton.setOnMouseClicked((event) -> {
            UserSession.disconnect();
            this.sceneController.newScene("launch");
        } );

        this.entraveButton.setOnMouseClicked(event -> newSceneAction(event, "consultEntraves"));

        this.consultRequestButton.setOnMouseClicked(event -> newSceneAction(event, "consultRequest"));
    }

}
