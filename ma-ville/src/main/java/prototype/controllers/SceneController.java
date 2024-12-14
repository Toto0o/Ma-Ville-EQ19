package prototype.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prototype.scenes.general.consult.NotificationScene;
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
import prototype.users.UserSession;
import prototype.scenes.general.consult.SearchProjectsScene;



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

    /* Scenes */
    private LaunchScene launchScene;
    private LoginScene loginScene;
    private RoleSelectionScene roleSelectionScene;
    private RegisterScene registerScene;
    private ConsultProjectScene consultProjectScene;
    private SettingsScene settingsScene;
    private RequestScene requestScene;
    private NotificationScene notificationScene;
    private InfoSettingsScene infoSettingsScene;
    private NotificationSettingsScene notificationSettingsScene;
    private ConsultRequestsScene consultRequestScene;
    private ConsultEntraveScene consultEntraveScene;
    private IntervenantProjectsScene intervenantProjectScene;
    private SearchProjectsScene searchProjectsScene;
    private MenuScene menuScene;
    private PreferencesPlagesHorairesScene preferencesPlagesHorairesScene;


    private ApiController apiController;

    /**
     *
     * @param primaryStage {@link Stage}
     * @param apiController {@link ApiController}
     */
    public SceneController(Stage primaryStage, ApiController apiController) {

        this.primaryStage = primaryStage;
        this.apiController = apiController;
    }


    /**
     * Méthode pour lancer l'application. La scène par défault est {@link LaunchScene}
     * 
     */
    public void start() {
        newScene("launch"); /* Default is the launching scene */
        this.primaryStage.setTitle("Ma ville - Équipe 19");
        this.primaryStage.show();
    }

    /**
     * Méthode pour changer de scene. Assigne la nouvelle scene à la scene en cours ({@link #scene})
     *
     * @param scene {@link String} la nouvelle scene à afficher
     * 
     */
    public void newScene(String scene) {


        switch (scene) {

            case "launch" -> this.scene = newLaunchScene();
            case "login" -> this.scene = newLoginScene();
            case "roleSelection" -> this.scene = newRoleSelectionScene();
            case "settings" -> this.scene = newSettingsScene();
            case "infoSettings" -> this.scene = newInfoSettingsScene();
            case "notificationSettings" -> this.scene = newNotificationSettingsScene();
            case "notifications" -> this.scene = newNotificationScene();
            case "preferencesPlagesHoraires" -> this.scene = newPreferencesPlagesHorairesScene();
            case "residentRegistration" -> this.scene = newRegisterScene(false);
            case "consult entraves" -> this.scene = newConsultEntraveScene();
            case "search project" -> this.scene = newSearchProjectsScene();
            case "request" -> this.scene = newRequestScene();
            case "intervenantRegistration" -> this.scene = newRegisterScene(true);
            case"intervenantProject" -> this.scene = newIntervenantProjectScene();
            case "consultRequest" -> this.scene = newConsultRequestScene();

        }
        
        this.primaryStage.setScene(this.scene);
        this.primaryStage.setMaximized(false);
        this.primaryStage.setMaximized(true);
        
    }


    private Scene newLaunchScene() {
        /* Set a new Launching Scene */
        this.launchScene = new LaunchScene(this);
        this.launchScene.setScene();
        return this.launchScene.getScene();
    }

    private Scene newLoginScene() {
        this.loginScene = new LoginScene(this);
        this.loginScene.setScene();
        return this.loginScene.getScene();
    }

    private Scene newRoleSelectionScene() {
        this.roleSelectionScene = new RoleSelectionScene(this);
        this.roleSelectionScene.setScene();
        return this.roleSelectionScene.getScene();
    }

    private Scene newRegisterScene(boolean intervenant) {
        this.registerScene = new RegisterScene(this, intervenant);
        this.registerScene.setScene();
        return this.registerScene.getScene();
    }

    private Scene newConsultProjectScene(boolean intervenant) {
        this.consultProjectScene = new ConsultProjectScene(this);
        this.consultProjectScene.setScene();
        return this.consultProjectScene.getScene();
    }

    private Scene newSettingsScene() {
        this.settingsScene = new SettingsScene(this);
        this.settingsScene.setScene();
        return this.settingsScene.getScene();
    }

    private Scene newRequestScene() {
        this.requestScene = new RequestScene(this);

        this.requestScene = new RequestScene(this);

        this.requestScene.setScene();
        return this.requestScene.getScene();
    }

    private Scene newNotificationScene() {
        this.notificationScene = new NotificationScene(this);
        this.notificationScene.setScene();
        return this.notificationScene.getScene();
    }

    private Scene newInfoSettingsScene() {
        this.infoSettingsScene = new InfoSettingsScene(this);
        this.infoSettingsScene.setScene();
        return this.infoSettingsScene.getScene();
    }

    private Scene newNotificationSettingsScene() {
        this.notificationSettingsScene = new NotificationSettingsScene(this);
        this.notificationSettingsScene.setScene();
        return this.notificationSettingsScene.getScene();
    }

    private Scene newMenuScene(Boolean intervenant) {
        this.menuScene = new MenuScene(this);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

    private Scene newConsultRequestScene() {
        this.consultRequestScene = new ConsultRequestsScene(this);
        this.consultRequestScene.setScene();
        return this.consultRequestScene.getScene();
    }

    private Scene newConsultEntraveScene() {
        this.consultEntraveScene = new ConsultEntraveScene(this);
        this.consultEntraveScene.setScene();
        return this.consultEntraveScene.getScene();
    }

    private Scene newIntervenantProjectScene() {
        this.intervenantProjectScene = new IntervenantProjectsScene(this);
        this.intervenantProjectScene.setScene();
        return this.intervenantProjectScene.getScene();
    }


    private Scene newPreferencesPlagesHorairesScene() {
        this.preferencesPlagesHorairesScene = new PreferencesPlagesHorairesScene(this);
        this.preferencesPlagesHorairesScene.setScene();
        return this.preferencesPlagesHorairesScene.getScene();
    }

    private Scene newSearchProjectsScene() {
        this.searchProjectsScene = new SearchProjectsScene(this);
        this.searchProjectsScene.setScene();
        return this.searchProjectsScene.getScene();
    }


    public ApiController getApiController() {
        return apiController;
    }

}
