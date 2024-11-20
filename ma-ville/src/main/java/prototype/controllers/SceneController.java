package prototype.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prototype.interfaces.UserController;
import prototype.scenes.general.ConsultProjectScene;
import prototype.scenes.general.InfoSettingsScene;
import prototype.scenes.general.LaunchScene;
import prototype.scenes.general.LoginScene;
import prototype.scenes.general.MenuScene;
import prototype.scenes.general.NotificationScene;
import prototype.scenes.general.NotificationSettingsScene;
import prototype.scenes.general.RegisterScene;
import prototype.scenes.general.SettingsScene;
import prototype.scenes.intervenant.ConsultRequestScene;
import prototype.scenes.resident.RequestScene;


public class SceneController {

    private Stage primaryStage;
    private Scene scene;

    /* Scenes */
    private LaunchScene launchScene;
    private LoginScene loginScene;
    private RegisterScene registerScene;
    private ConsultProjectScene consultProjectScene;
    private SettingsScene settingsScene;
    private RequestScene requestScene;
    private NotificationScene notificationScene;
    private InfoSettingsScene infoSettingsScene;
    private NotificationSettingsScene notificationSettingsScene;
    private ConsultRequestScene consultRequestScene;
    private MenuScene menuScene;
    private Boolean intervenant;

    private UserController userController;
    private RequestController requestController;

    public SceneController(Stage primaryStage, UserController userController, RequestController requestController) {
        this.primaryStage = primaryStage;
        this.userController = userController;
        this.requestController = requestController;
        this.intervenant = false;
    }

    public void start() {
        newScene("launch"); /* Default is the launching scene */
        this.primaryStage.setTitle("Ma ville - Équipe 19");
        this.primaryStage.show();
    }

    public void newScene(String scene) {


        switch (scene) {
            // General scenes

            case "launch" :
                this.scene = newLaunchScene();
                break;
            
            case "login" :
                this.scene = newLoginScene();
                break;

            //Settings
            case "settings" :
                this.scene = newSettingsScene();
                break;
            
            case "infoSettings" :
                this.scene = newInfoSettingsScene();
                break;

            case "notificationSettings" :
                this.scene = newNotificationSettingsScene();
                break;

            
            case "notifications" :
                this.scene = newNotificationScene();
                break;
            
            case "consult project" :
                this.scene = newConsultProjectScene(this.intervenant);
                break;


            // Resident scenes    
            case "resident register" :
                this.scene = newRegisterScene(false);
                break;
            
            case "request" :
                this.scene = newRequestScene();
                break;


            
            //Intervenant scenes
            case "intervenant register" :
                this.scene = newRegisterScene(true);
                break;
            
            case "menu" :
                this.scene = newMenuScene(this.intervenant);
                break;

            case "consult request" :
                this.scene = newConsultRequestScene();
                break;

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
        this.loginScene = new LoginScene(this);
        this.loginScene.setScene();
        return this.loginScene.getScene();
    }

    private Scene newRegisterScene(boolean intervenant) {

        this.registerScene = new RegisterScene(this, intervenant);
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
        this.menuScene = new MenuScene(this, intervenant);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

    private Scene newConsultRequestScene() {
        this.consultRequestScene = new ConsultRequestScene(this);
        this.consultRequestScene.setScene();
        return this.consultRequestScene.getScene();
    }



}
