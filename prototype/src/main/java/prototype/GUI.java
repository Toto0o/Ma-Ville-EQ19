package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.Controllers.SceneController;
import prototype.service.FirebaseInitialize;

public class GUI extends Application {

    private SceneController sceneController;

    public static void main(String[] args) {
        GUI.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FirebaseInitialize.initialize();

        // Initialize and start the scene controller
        this.sceneController = new SceneController(primaryStage);
        this.sceneController.start();
    }
}
