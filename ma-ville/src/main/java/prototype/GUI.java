package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.controllers.SceneController;
import prototype.interfaces.UserController;

public class GUI extends Application {

    private SceneController sceneController;

    private UserController userController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception {
        
        this.sceneController = new SceneController(primaryStage, userController);
        this.sceneController.start();
    }
}
