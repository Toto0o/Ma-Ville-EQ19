package prototype.scenes.general.register;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import prototype.scenes.Scenes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;

/**
 * Scene de choix du role de l'utilisateur pour l'enregistrement
 */
public class RoleSelectionScene extends Scenes {

    private Button residentButton, intervenantButton, backButton;

    /**
     * Constructeur
     * @param sceneController
     */
    public RoleSelectionScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void setScene() {
        // Initialize buttons
        this.residentButton = new Button("Resident");
        this.residentButton.setMaxWidth(80);
        this.intervenantButton = new Button("Intervenant");
        this.intervenantButton.setMaxWidth(80);
        this.backButton = new Button("Back");
        this.backButton.setMaxWidth(80);


        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        Text title = new Text("Choisissez votre rôle");
        title.setFill(Color.WHITE);
        title.setFont(new Font(20));
        vBox.getChildren().addAll(
                title,
                this.residentButton,
                this.intervenantButton,
                this.backButton);

        this.root.setCenter(vBox);

        // Set button actions
        residentButton.setOnMouseClicked(event -> this.sceneController.newScene("residentRegistration"));
        intervenantButton.setOnMouseClicked(event -> {this.sceneController.newScene("intervenantRegistration");});
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("launch"));
    }

}
