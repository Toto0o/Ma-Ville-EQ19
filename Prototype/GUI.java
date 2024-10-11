package Prototype;

import Prototype.Controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

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
