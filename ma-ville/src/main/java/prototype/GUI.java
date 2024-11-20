package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.controllers.ProjectController;
import prototype.controllers.RequestController;
import prototype.controllers.SceneController;
import prototype.interfaces.UserController;

public class GUI extends Application {

    private SceneController sceneController;
    private RequestController requestController;
    private UserController userController;
    private ProjectController projectController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.projectController = new ProjectController();
        this.requestController = new RequestController(this.userController, this.projectController);
        this.sceneController = new SceneController(primaryStage, this.userController, this.requestController);
        this.sceneController.start();
    }
}
