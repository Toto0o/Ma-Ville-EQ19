package prototype.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prototype.controllers.UserController;
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
import prototype.scenes.general.settings.SettingsScene;
import prototype.scenes.intervenant.ConsultRequestScene;
import prototype.scenes.resident.RequestScene;


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
    private ConsultRequestScene consultRequestScene;
    private ConsultEntraveScene consultEntraveScene;
    private MenuScene menuScene;
    private Boolean intervenant;

    private UserController userController;
    private RequestController requestController;
    private ProjectController projectController;
    private ApiController apiController;

    public SceneController(Stage primaryStage, UserController userController, RequestController requestController, ProjectController projectController, ApiController apiController) {
        this.primaryStage = primaryStage;
        this.userController = userController;
        this.requestController = requestController;
        this.projectController = projectController;
        this.apiController = apiController;
        this.intervenant = false;
    }

    public void start() {
        newScene("menu"); /* Default is the launching scene */
        this.primaryStage.setTitle("Ma ville - Équipe 19");
        this.primaryStage.show();
    }

    public void newScene(String scene) {


        switch (scene) {

            case "launch" -> this.scene = newLaunchScene();
            case "login" -> this.scene = newLoginScene();
            case "roleSelection" -> this.scene = newRoleSelectionScene();
            case "settings" -> this.scene = newSettingsScene();
            case "infoSettings" -> this.scene = newInfoSettingsScene();
            case "notificationSettings" -> this.scene = newNotificationSettingsScene();
            case "notifications" -> this.scene = newNotificationScene();
            case "consult entraves" -> this.scene = newConsultEntraveScene();
            
            case "consult project" -> this.scene = newConsultProjectScene(this.intervenant);
            case "residentRegistration" -> this.scene = newRegisterScene();
            
            case "request" -> this.scene = newRequestScene();
            
            case "intervenantRegistration" -> this.scene = newRegisterScene();
            
            case "menu" -> this.scene = newMenuScene(this.intervenant);

            case "consult request" -> this.scene = newConsultRequestScene();

        }
        
        this.primaryStage.setScene(this.scene);
        this.primaryStage.setMaximized(false);
        this.primaryStage.setMaximized(true);
        
    }

    public void setIntervenant(Boolean value) {
        this.intervenant = value;
    }

    private Scene newLaunchScene() {
        /* Set a new Launching Scene */
        this.launchScene = new LaunchScene(this);
        this.launchScene.setScene();
        return this.launchScene.getScene();
    }

    private Scene newLoginScene() {
        this.loginScene = new LoginScene(this, this.userController);
        this.loginScene.setScene();
        return this.loginScene.getScene();
    }

    private Scene newRoleSelectionScene() {
        this.roleSelectionScene = new RoleSelectionScene(this);
        this.roleSelectionScene.setScene();
        return this.roleSelectionScene.getScene();
    }

    private Scene newRegisterScene() {
        this.registerScene = new RegisterScene(this, this.userController);
        this.registerScene.setScene();
        return this.registerScene.getScene();
    }

    private Scene newConsultProjectScene(boolean intervenant) {
        this.consultProjectScene = new ConsultProjectScene(this, intervenant);
        this.consultProjectScene.setScene();
        return this.consultProjectScene.getScene();
    }

    private Scene newSettingsScene() {
        this.settingsScene = new SettingsScene(this);
        this.settingsScene.setScene();
        return this.settingsScene.getScene();
    }

    private Scene newRequestScene() {
        this.requestScene = new RequestScene(this, this.requestController);
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
        this.menuScene = new MenuScene(this, this.userController);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

    private Scene newConsultRequestScene() {
        this.consultRequestScene = new ConsultRequestScene(this);
        this.consultRequestScene.setScene();
        return this.consultRequestScene.getScene();
    }

    private Scene newConsultEntraveScene() {
        this.consultEntraveScene = new ConsultEntraveScene(this);
        this.consultEntraveScene.setScene();
        return this.consultEntraveScene.getScene();
    }



}
