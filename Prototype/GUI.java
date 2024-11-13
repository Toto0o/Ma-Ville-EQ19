package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.controllers.SceneController;

public class GUI extends Application {

    private SceneController sceneController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception {
        this.sceneController = new SceneController(primaryStage);
        this.sceneController.start();
    }
}
