package prototype.scenes.general;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;


public class LaunchScene extends Scenes {
    
    private VBox vBox;
    private HBox buttonsBox;

    private Text title;

    private Button loginButton, residentRegisterButton, intervenantRegisterButton;

    public LaunchScene(SceneController sceneController) {
        super(sceneController);

        this.vBox = new VBox();
        this.buttonsBox = new HBox();
        this.loginButton = new Button("S'authentifier");
        this.residentRegisterButton = new Button("S'inscrire : Résident");
        this.intervenantRegisterButton = new Button("S'inscrire : Intervenant");

        this.title = new Text("Ma Ville");
    }

    @Override
    public void setScene() {
        root.setCenter(this.vBox); 
        root.setTop(this.buttonsBox);

        vBox.getChildren().add(title);
        vBox.setAlignment(Pos.CENTER);

        buttonsBox.getChildren().addAll(this.loginButton, this.residentRegisterButton, this.intervenantRegisterButton);
        buttonsBox.setAlignment(Pos.CENTER);

        loginButton.setOnMouseClicked((loginAction) -> {
            this.sceneController.newScene("login");
        });
        residentRegisterButton.setOnMouseClicked((signinRAction) -> {
            this.sceneController.newScene("resident register");
        });

        intervenantRegisterButton.setOnMouseClicked((signinIAction) -> {
            this.sceneController.newScene("intervenant register");
        });
    }
}
