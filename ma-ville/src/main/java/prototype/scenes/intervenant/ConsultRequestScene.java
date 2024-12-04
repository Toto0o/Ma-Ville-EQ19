package prototype.scenes.intervenant;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class ConsultRequestScene extends Scenes {

    private VBox vBox;
    private Text title;

    public ConsultRequestScene(SceneController sceneController) {
        super(sceneController);

        this.title = new Text("Requêtes de travail");
        this.vBox = new VBox();
    }

    @Override
    public void setScene() {
        this.root.setTop(this.menuButton);
        this.root.setCenter(this.vBox);
        this.vBox.getChildren().add(this.title);

        this.menuButton.setOnMouseDragOver(event -> newSceneAction(event, "menu"));
    }

}
