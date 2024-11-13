package prototype.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import prototype.controllers.SceneController;

public abstract class Scenes {

    protected BorderPane root;
    protected Scene scene;

    protected SceneController sceneController;

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
