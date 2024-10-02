package Scenes;

import Controllers.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ConsultProjectScene extends Scenes {

    private HBox menuBox;
    private VBox projectBox;

    private Button menuButton;

    public ConsultProjectScene(SceneController sceneController) {
        super(sceneController);

        this.menuBox = new HBox();
        this.projectBox = new VBox();

        this.menuButton = new Button("Menu");

    }

    public void setScene() {

        this.root.setTop(this.menuBox);
        this.menuBox.getChildren().add(this.menuButton);

        this.root.setCenter(this.projectBox);


        this.menuButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });


    }

}
