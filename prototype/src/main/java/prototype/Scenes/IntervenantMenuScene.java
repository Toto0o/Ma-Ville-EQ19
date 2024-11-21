package prototype.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class IntervenantMenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultButton, settingsButton, requestButton, problemButton, logoutButton;
    private Text title;

    public IntervenantMenuScene(SceneController sceneController) {
        super(sceneController);
        this.settingsButton = new Button("Personnaliser le profil");
        this.consultButton = new Button("Consulter les requêtes de travail");
        this.requestButton = new Button("Soumettre un nouveau projet");
        this.problemButton = new Button("Modifier mes projets");
        this.logoutButton = new Button("Déconnexion");

        this.vBox = new VBox();
        this.titleBox = new HBox();
        this.title = new Text("Bienvenu dans le menu des intervenants!");
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
                this.logoutButton);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);

        this.consultButton.setOnMouseClicked((consultAction) -> {
            this.sceneController.newScene("consultRequests");
        });

        this.settingsButton.setOnMouseClicked((settingsAction) -> {
            this.sceneController.newScene("intervenantSettings");
        });

        this.requestButton.setOnMouseClicked((requestAction) -> {
            this.sceneController.newScene("intervenantRequest");
        });

        this.problemButton.setOnMouseClicked((problemAction) -> {
            this.sceneController.newScene("problem");
        });

        this.logoutButton.setOnMouseClicked((logoutAction) -> {
            this.sceneController.newScene("launch");
        });
    }

}
