package Scenes;

import Controllers.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResidentMenuScene extends Scenes {

    private VBox vBox;
    private HBox titleBox;
    private Button consultButton, settingsButton, requestButton, problemButton;
    private Text title;

    public ResidentMenuScene(SceneController sceneController) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.settingsButton = new Button("Préférences");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.problemButton = new Button("Siganler un problème");

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
            this.problemButton
        );
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
    }

}
