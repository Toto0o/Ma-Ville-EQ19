package Prototype.Scenes;

import Prototype.Controllers.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ConsultProjectScene extends Scenes {

    private HBox menuBox;
    private VBox projectBox;
    private Text text;
    private Button menuButton;

    public ConsultProjectScene(SceneController sceneController) {
        super(sceneController);

        this.menuBox = new HBox();
        this.projectBox = new VBox();
        this.text = new Text("No project at the moment");
        this.menuButton = new Button("Menu");

    }

    public void setScene() {

        this.root.setTop(this.menuBox);
        this.menuBox.getChildren().add(this.menuButton);
        this.projectBox.getChildren().add(this.text);
        this.text.setFont(new Font("Arial", 20));
        this.projectBox.setAlignment(Pos.CENTER);
        this.root.setCenter(this.projectBox);


        this.menuButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });


    }

}
