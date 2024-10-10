package Prototype.Scenes;

import Prototype.Controllers.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NotificationSettingsScene extends Scenes {

    private Text aVenir;
    private HBox hBox;
    private Button menu;

    public NotificationSettingsScene(SceneController SceneController) {
        super(SceneController);
        this.aVenir = new Text("Option à venir");
        this.hBox = new HBox();
        this.menu = new Button("Retour");
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.hBox);
        this.root.setTop(this.menu);
        this.hBox.getChildren().add(this.aVenir);
        this.hBox.setAlignment(Pos.CENTER);
        this.aVenir.setFont(new Font("arial", 20));

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("settings");
        });
    }
}
