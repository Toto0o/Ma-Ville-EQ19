package prototype;

import javafx.application.Application;
import javafx.stage.Stage;
import prototype.services.FirebaseInitialize;
import prototype.controllers.*;

/**
 * Interface graphique de l'application
 *
 */
public class MaVille extends Application {

    private SceneController sceneController;
    private ApiController apiController;

    public static void main (String[] args) {
        MaVille.launch(args);
    }

    /**
     * Démarrage de l'application.
     *
     * <p>Instancie le controlleur de scenes {@link SceneController}</p>
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.sceneController = new SceneController(primaryStage);
        primaryStage.setTitle("Ma ville - Équipe 19");
        primaryStage.show();
    }

    /**
     * Méthode pour le débuggage et pour les test
     * @return {@link SceneController}
     */
    public SceneController getSceneController() {
        return sceneController;
    }
}
