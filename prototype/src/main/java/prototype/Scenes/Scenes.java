package prototype.Scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import prototype.Controllers.SceneController;

public abstract class Scenes {

    BorderPane root;
    Scene scene;

    SceneController sceneController;

    public Scenes(SceneController sceneController) {
        this.sceneController = sceneController;
        this.root = new BorderPane();
        this.scene = new Scene(this.root);

    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract void setScene();

}
