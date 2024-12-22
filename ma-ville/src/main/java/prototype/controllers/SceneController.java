package prototype.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prototype.scenes.Scenes;
import prototype.scenes.resident.NotificationScene;
import prototype.scenes.general.consult.ConsultEntraveScene;
import prototype.scenes.general.consult.ConsultProjectScene;
import prototype.scenes.general.login.LoginScene;
import prototype.scenes.general.menu.LaunchScene;
import prototype.scenes.general.menu.MenuScene;
import prototype.scenes.general.register.RegisterScene;
import prototype.scenes.general.register.RoleSelectionScene;
import prototype.scenes.general.settings.InfoSettingsScene;
import prototype.scenes.general.settings.NotificationSettingsScene;
import prototype.scenes.general.settings.PreferencesPlagesHorairesScene;
import prototype.scenes.general.settings.SettingsScene;
import prototype.scenes.intervenant.ConsultRequestsScene;
import prototype.scenes.intervenant.IntervenantProjectsScene;
import prototype.scenes.resident.RequestScene;
import prototype.scenes.general.consult.SearchProjectsScene;
import prototype.users.Resident;
import prototype.users.UserSession;


/**
 * Controlleur des scenes. Permet de charger une nouvelle scene avec {@link #newScene(String)}
 * 
 * <p> Assigne les différents controlleurs aux scenes necessitant un controlleur </p>
 *
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */
public class SceneController {

    private Stage primaryStage;
    private Scene scene;
    private Scenes currentScene;

    /**
     *
     * @param primaryStage {@link Stage}
     */
    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        newScene("launch");
    }

    /**
     * Méthode pour changer de scene. Assigne la nouvelle scene à la scene en cours ({@link #scene})
     *
     * @param scene {@link String} la nouvelle scene à afficher
     * 
     */
    public void newScene(String scene) {

        // Insure currentScene is asserted
         currentScene = null;

        switch (scene) {

            // General scenes (accessible to intervenants and residents)
            case "launch" -> currentScene = new LaunchScene(this);
            case "login" -> currentScene = new LoginScene(this);
            case "roleSelection" -> currentScene = new RoleSelectionScene(this);
            case "menu" -> currentScene = new MenuScene(this);
            case "settings" -> currentScene = new SettingsScene(this);
            case "infoSettings" -> currentScene = new InfoSettingsScene(this);
            case "notificationSettings" -> currentScene = new NotificationSettingsScene(this);
            case "notifications" -> currentScene = new NotificationScene(this);
            case "preferencesPlagesHoraires" -> currentScene = new PreferencesPlagesHorairesScene(this);
            case "consultEntraves" -> currentScene = new ConsultEntraveScene(this);
            case "searchProject" -> currentScene = new SearchProjectsScene(this);


            // Scenes accessible only to residents
            case "residentRegistration" -> currentScene = new RegisterScene(this, false);
            case "request" -> currentScene = new RequestScene(this);
            case "consultProjects" -> currentScene = new ConsultProjectScene(this);

            // Scenes accessible only to intervenants
            case "intervenantRegistration" -> currentScene = new RegisterScene(this,true);
            case"intervenantProject" -> currentScene = new IntervenantProjectsScene(this);
            case "consultRequest" -> currentScene = new ConsultRequestsScene(this);

        }

        // Insure currentScene has been assigned
        assert currentScene != null;
        currentScene.setScene();
        // Assign current scene to scene field
        this.scene = currentScene.getScene();
        this.primaryStage.setScene(this.scene);

        // To get full screen on scene change
        this.primaryStage.setMaximized(false);
        this.primaryStage.setMaximized(true);
    }

    /**
     * Méthode de débuggage et pour les test
     * @return {@link Scenes} en cours
     */
    public Scenes getScene() {
        return currentScene;
    }
}
