package prototype.scenes.general.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

/**
 * Scene par défault sur le lancement de l'application
 *
 * <p> Donne accès à {@link prototype.scenes.general.login.LoginScene LoginScene} et
 * {@link prototype.scenes.general.register.RoleSelectionScene RoleSelectionScene}</p>
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class LaunchScene extends Scenes {

    private VBox vBox;
    private HBox buttonsBox;
    private Button loginButton, registerButton;

    /**
     * Constructeur
     * @param sceneController
     */
    public LaunchScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.buttonsBox = new HBox();
        this.loginButton = new Button("Login");
        this.loginButton.setId("loginButton");
        this.registerButton = new Button("Register");
        this.registerButton.setId("registerButton");
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene() {
        root.setCenter(vBox);
        root.setTop(buttonsBox);

        buttonsBox.setSpacing(10);

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/prototype/assets/logo.png")));

        vBox.getChildren().addAll(img, buttonsBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

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