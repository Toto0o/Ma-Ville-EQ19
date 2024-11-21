package prototype.Controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prototype.Scenes.ConsultEntraveScene;
import prototype.Scenes.ConsultProjectScene;
import prototype.Scenes.InfoSettingsScene;
import prototype.Scenes.IntervenantMenuScene;
import prototype.Scenes.IntervenantRegistrationScene;
import prototype.Scenes.LaunchScene;
import prototype.Scenes.LoginScene;
import prototype.Scenes.NotificationScene;
import prototype.Scenes.NotificationSettingsScene;
import prototype.Scenes.ProblemScene;
import prototype.Scenes.RequestScene;
import prototype.Scenes.ResidentMenuScene;
import prototype.Scenes.ResidentRegistrationScene;
import prototype.Scenes.RoleSelectionScene;
import prototype.Scenes.SearchProjectsScene;
import prototype.Scenes.SettingsScene;

public class SceneController {

    private Stage primaryStage;
    private Scene scene;

    // Scene declarations
    private LaunchScene launchScene;
    private LoginScene loginScene;
    private ResidentMenuScene residentMenuScene;
    private IntervenantMenuScene intervenantMenuScene;
    private ConsultProjectScene consultProjectScene;
    private ConsultEntraveScene consultEntraveScene;
    private SearchProjectsScene searchProjectsScene;
    private SettingsScene settingsScene;
    private RequestScene requestScene;
    private ProblemScene problemScene;
    private NotificationScene notificationScene;
    private InfoSettingsScene infoSettingsScene;
    private NotificationSettingsScene notificationSettingsScene;
    private RoleSelectionScene roleSelectionScene;
    private IntervenantRegistrationScene intervenantRegistrationScene;
    private ResidentRegistrationScene residentRegistrationScene;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        newScene("launch");
        this.primaryStage.setTitle("Ma ville - Équipe 19");
        this.primaryStage.show();
    }

    public void newScene(String sceneName) {
        switch (sceneName) {
            case "launch":
                this.scene = newLaunchScene();
                break;
            case "login":
                this.scene = newLoginScene();
                break;
            case "residentMenu":
                this.scene = newResidentMenuScene();
                break;
            case "intervenantMenu":
                this.scene = newIntervenantMenuScene();
                break;
            case "consultProjects":
                this.scene = newConsultProjectScene();
                break;
            case "searchProjects":
                this.scene = newSearchProjectsScene();
                break;
            case "consultEntraves":
                this.scene = newConsultEntraveScene();
                break;
            case "settings":
                this.scene = newSettingsScene();
                break;
            case "request":
                this.scene = newRequestScene();
                break;
            case "problem":
                this.scene = newProblemScene();
                break;
            case "notifications":
                this.scene = newNotificationScene();
                break;
            case "infoSettings":
                this.scene = newInfoSettingsScene();
                break;
            case "notificationSettings":
                this.scene = newNotificationSettingsScene();
                break;
            case "roleSelection":
                this.scene = newRoleSelectionScene();
                break;
            case "residentRegistration":
                this.scene = newResidentRegistrationScene();
                break;
            case "intervenantRegistration":
                this.scene = newIntervenantRegistrationScene();
                break;
        }

        // Update the stage with the new scene
        this.primaryStage.setScene(this.scene);
        this.primaryStage.setMaximized(false); // Optionally restore window state
        this.primaryStage.setMaximized(true); // Maximize window for all scenes
    }

    // Scene creation methods
    private Scene newLaunchScene() {
        this.launchScene = new LaunchScene(this);
        this.launchScene.setScene();
        return this.launchScene.getScene();
    }

    private Scene newLoginScene() {
        this.loginScene = new LoginScene(this);
        this.loginScene.setScene();
        return this.loginScene.getScene();
    }

    private Scene newResidentMenuScene() {
        this.residentMenuScene = new ResidentMenuScene(this);
        this.residentMenuScene.setScene();
        return this.residentMenuScene.getScene();
    }

    private Scene newIntervenantMenuScene() {
        this.intervenantMenuScene = new IntervenantMenuScene(this);
        this.intervenantMenuScene.setScene();
        return this.intervenantMenuScene.getScene();
    }

    private Scene newConsultProjectScene() {
        this.consultProjectScene = new ConsultProjectScene(this);
        this.consultProjectScene.setScene();
        return this.consultProjectScene.getScene();
    }

    private Scene newSearchProjectsScene() {
        this.searchProjectsScene = new SearchProjectsScene(this);
        this.searchProjectsScene.setScene();
        return this.searchProjectsScene.getScene();
    }

    private Scene newConsultEntraveScene() {
        this.consultEntraveScene = new ConsultEntraveScene(this);
        this.consultEntraveScene.setScene();
        return this.consultEntraveScene.getScene();
    }

    private Scene newSettingsScene() {
        this.settingsScene = new SettingsScene(this);
        this.settingsScene.setScene();
        return this.settingsScene.getScene();
    }

    private Scene newRequestScene() {
        this.requestScene = new RequestScene(this);
        this.requestScene.setScene();
        return this.requestScene.getScene();
    }

    private Scene newProblemScene() {
        this.problemScene = new ProblemScene(this);
        this.problemScene.setScene();
        return this.problemScene.getScene();
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

    private Scene newRoleSelectionScene() {
        this.roleSelectionScene = new RoleSelectionScene(this);
        this.roleSelectionScene.setScene();
        return this.roleSelectionScene.getScene();
    }

    private Scene newResidentRegistrationScene() {
        this.residentRegistrationScene = new ResidentRegistrationScene(this);
        this.residentRegistrationScene.setScene();
        return this.residentRegistrationScene.getScene();
    }

    private Scene newIntervenantRegistrationScene() {
        this.intervenantRegistrationScene = new IntervenantRegistrationScene(this);
        this.intervenantRegistrationScene.setScene();
        return this.intervenantRegistrationScene.getScene();
    }
}
