package prototype.Scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class NotificationScene extends Scenes {

    private HBox Hbox;
    private Text text;
    private Button menu;

    public NotificationScene(SceneController sceneController) {
        super(sceneController);
        this.Hbox = new HBox();
        this.text = new Text("Aucune notification à afficher");
        this.menu = new Button("Menu");
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.Hbox);
        this.root.setTop(this.menu);
        this.Hbox.getChildren().add(this.text);
        this.Hbox.setAlignment(Pos.CENTER);
        this.text.setFont(new Font("arial", 20));

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });
    }
}
