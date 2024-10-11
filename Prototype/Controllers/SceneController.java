package Prototype.Controllers;

import javafx.stage.Stage;
import javafx.scene.*;
import Prototype.Scenes.*;


public class SceneController {

    private Stage primaryStage;
    private Scene scene;

    /* Scenes */
    private LaunchScene launchScene;
    private LoginScene loginScene;
    private ResidentMenuScene menuScene;
    private RegisterScene registerScene;
    private ConsultProjectScene consultProjectScene;
    private SettingsScene settingsScene;
    private RequestScene requestScene;
    private ProblemScene problemScene;
    private NotificationScene notificationScene;
    private InfoSettingsScene infoSettingsScene;
    private NotificationSettingsScene notificationSettingsScene;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        newScene("launch"); /* Default is the launching scene */
        this.primaryStage.setTitle("Ma ville - Équipe 19");
        this.primaryStage.show();
    }

    public void newScene(String scene) {


        switch (scene) {
            case "launch" :
                this.scene = newLaunchScene();
                break;
            
            case "login" :
                this.scene = newLoginScene();
                break;
            
            case "menu" :
                this.scene = newResidentMenuScene();
                break;

            case "register" :
                this.scene = newRegisterScene();
                break;
            
            case "consult" :
                this.scene = newConsultProjectScene();
                break;
            
            case "settings" :
                this.scene = newSettingsScene();
                break;

            case "request" :
                this.scene = newRequestScene();
                break;

            case "problem" :
                this.scene = newProblemScene();
                break;

            case "notifications" :
                this.scene = newNotificationScene();
                break;

            case "infoSettings" :
                this.scene = newInfoSettingsScene();
                break;

            case "notificationSettings" :
                this.scene = newNotificationSettingsScene();
                break;
        };
        
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

    private Scene newResidentMenuScene() {
        this.menuScene = new ResidentMenuScene(this);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

    private Scene newRegisterScene() {
        this.registerScene = new RegisterScene(this);
        this.registerScene.setScene();
        return this.registerScene.getScene();
    }

    private Scene newConsultProjectScene() {
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

}
