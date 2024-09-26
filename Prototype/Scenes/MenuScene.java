package Scenes;

import Controllers.SceneController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MenuScene {

    private SceneController sceneController;
    private BorderPane root;
    private Scene scene;

    public MenuScene(SceneController sceneController) {
        this.sceneController = sceneController;
        this.root = new BorderPane();
        this.scene = new Scene(root);
    }

    public void setScene() {}

    public Scene getScene() { return this.scene; }
}
