package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.api.firebase.FirebaseInitialize;
import prototype.controllers.*;

public class GUI extends Application {

    private SceneController sceneController;
    private RequestController requestController;
    private UserController userController;
    private ProjectController projectController;
    private ApiController apiController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        /* FirebaseInitialize.initialize(); */
        this.apiController = new ApiController();
        this.projectController = new ProjectController(this.apiController);
        this.userController = new UserController(this.apiController);
        this.requestController = new RequestController(this.userController, this.projectController);
        this.sceneController = new SceneController(primaryStage, this.userController, this.requestController, this.projectController, this.apiController);
        this.sceneController.start();
    }
}
