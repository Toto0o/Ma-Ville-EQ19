package prototype.scenes.general.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

/**
 * Scene par défault sur le lancement de l'application
 * 
 * @param sceneController
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class LaunchScene extends Scenes {

    private VBox vBox;
    private HBox buttonsBox;

    private Text title;

    private Button loginButton, registerButton;

    public LaunchScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.buttonsBox = new HBox();
        this.loginButton = new Button("Log In");
        this.registerButton = new Button("Register");
        this.title = new Text("Ma Ville");
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene() {
        root.setCenter(vBox);
        root.setTop(buttonsBox);

        vBox.getChildren().add(title);
        vBox.setAlignment(Pos.CENTER);

        buttonsBox.getChildren().addAll(loginButton, registerButton);
        buttonsBox.setAlignment(Pos.CENTER);

        loginButton.setOnMouseClicked((loginAction) -> {
            this.sceneController.newScene("login");
        });

        // Updated action for registerButton to navigate to roleSelection
        registerButton.setOnMouseClicked((signinAction) -> {
            this.sceneController.newScene("roleSelection");
        });
    }
}