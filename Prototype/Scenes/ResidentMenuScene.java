package Scenes;

import Controllers.SceneController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ResidentMenuScene extends Scenes {

    private VBox vBox;
    private Button consultButton, settingsButton, requestButton, problemButton;

    public ResidentMenuScene(SceneController sceneController) {
        super(sceneController);
        this.consultButton = new Button("Consulter des travaux en cours");
        this.settingsButton = new Button("Réglages");
        this.requestButton = new Button("Soumettre une requête de travail");
        this.problemButton = new Button("Siganler un problème");
    }

    public void setScene() {
        this.root.setCenter(vBox);

        vBox.getChildren().addAll(
            this.consultButton,
            this.settingsButton,
            this.requestButton,
            this.problemButton
        );

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
