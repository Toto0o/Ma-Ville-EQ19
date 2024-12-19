package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.services.FirebaseInitialize;
import prototype.controllers.*;
import prototype.services.UserServices;
import prototype.users.Address;
import prototype.users.Horaire;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.ArrayList;

/**
 * Interface graphique de l'application
 *
 */
public class GUI extends Application {

    private SceneController sceneController;
    private ApiController apiController;

    public static void main (String[] args) {
        GUI.launch(args);
    }

    /**
     * Initialise Firebase avec {@link FirebaseInitialize#initialize()}
     * <p>Instancie {@link ApiController} et {@link SceneController}</p>
     * <p>Lance l'appication avec {@link SceneController#start()}</p>
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FirebaseInitialize.initialize();

        UserServices service = new UserServices();
        ArrayList< Horaire > horaires = service.getPreferencesHoraires();
        System.out.println(horaires.size());
        this.apiController = new ApiController();

        this.sceneController = new SceneController(
                primaryStage,
                this.apiController);

        this.sceneController.start();
    }
}
