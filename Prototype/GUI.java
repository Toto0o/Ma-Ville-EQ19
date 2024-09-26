import Controllers.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class GUI extends Application {

    private SceneController sceneController;

    public static void main (String[] args) {
        GUI.launch(args);
    }
    
    public void start(Stage primeryStage) throws Exception {
        this.sceneController = new SceneController(primeryStage);
        this.sceneController.start();
    }
}
