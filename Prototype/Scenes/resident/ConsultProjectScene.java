package prototype.scenes.resident;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;


public class ConsultProjectScene extends Scenes {

    private HBox menuBox, searchBox;
    private VBox projectBox;
    private Text text;
    private Button menuButton;
    private TextField searchBar;

    public ConsultProjectScene(SceneController sceneController) {
        super(sceneController);

        this.menuBox = new HBox();
        this.projectBox = new VBox();
        this.searchBox = new HBox();
        this.text = new Text("No project at the moment");
        this.menuButton = new Button("Menu");
        this.searchBar = new TextField();

    }
    
    @Override
    public void setScene() {

        this.root.setTop(this.menuBox);
        this.menuBox.getChildren().add(this.menuButton);

        this.root.setCenter(this.projectBox);
        this.projectBox.getChildren().add(this.text);
        this.text.setFont(new Font("Arial", 20));
        this.projectBox.setAlignment(Pos.CENTER);
        


        this.menuButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });


    }

}
