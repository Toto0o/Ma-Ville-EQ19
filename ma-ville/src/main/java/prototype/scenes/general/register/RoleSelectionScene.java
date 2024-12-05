package prototype.scenes.general.register;

import prototype.scenes.Scenes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;

public class RoleSelectionScene extends Scenes {

    private Button residentButton, intervenantButton, backButton;

    public RoleSelectionScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void setScene() {
        // Initialize buttons
        this.residentButton = new Button("Resident");
        this.intervenantButton = new Button("Intervenant");
        this.backButton = new Button("Back");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                new Text("Select Your Role"),
                this.residentButton,
                this.intervenantButton,
                this.backButton);

        this.root.setCenter(vBox);

        // Set button actions
        residentButton.setOnMouseClicked(event -> this.sceneController.newScene("residentRegistration"));
        intervenantButton.setOnMouseClicked(event -> this.sceneController.newScene("intervenantRegistration"));
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("launch"));
    }

}
