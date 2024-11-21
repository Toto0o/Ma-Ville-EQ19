package prototype.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class RoleSelectionScene extends Scenes {

    private Button residentButton, intervenantButton, backButton;

    public RoleSelectionScene(SceneController sceneController) {
        super(sceneController);
        setScene();
    }

    @Override
    public void setScene() {
        // Initialize buttons
        residentButton = new Button("Resident");
        intervenantButton = new Button("Intervenant");
        backButton = new Button("Back");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                new Text("Select Your Role"),
                residentButton,
                intervenantButton,
                backButton);

        this.root.setCenter(vBox);

        // Set button actions
        residentButton.setOnMouseClicked(event -> this.sceneController.newScene("residentRegistration"));
        intervenantButton.setOnMouseClicked(event -> this.sceneController.newScene("intervenantRegistration"));
        backButton.setOnMouseClicked(event -> this.sceneController.newScene("launch"));
    }
}
