package Controllers;

import javafx.stage.Stage;
import javafx.scene.*;
import Scenes.*;


public class SceneController {

    private Stage primaryStage;
    private Scene scene;

    /* Scenes */
    private LaunchScene launchScene;
    private LoginScene loginScene;
    private MenuScene menuScene;

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
                this.scene = newMenuScene();
            

        };
        primaryStage.setScene(this.scene);
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

    private Scene newMenuScene() {
        this.menuScene = new MenuScene(this);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

}
