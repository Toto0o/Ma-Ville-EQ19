package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.services.FirebaseInitialize;
import prototype.controllers.*;

public class GUI extends Application {

    private SceneController sceneController;
    private ApiController apiController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        FirebaseInitialize.initialize();

        this.apiController = new ApiController();

        this.sceneController = new SceneController(
                primaryStage,
                this.apiController);

        this.sceneController.start();
    }
}
