package Scenes;

import Controllers.SceneController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


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

    public Scene getScene() { return this.scene; }

    public void setScene() {
        root.setCenter(vBox); 
        root.setTop(buttonsBox);

        vBox.getChildren().add(title);
        vBox.setAlignment(Pos.CENTER);

        buttonsBox.getChildren().addAll(loginButton, registerButton);

        loginButton.setOnMouseClicked((loginAction) -> {
            this.sceneController.newScene("login");
        });

        registerButton.setOnMouseClicked((signinAction) -> {
            this.sceneController.newScene("register");
        });
    }
}
