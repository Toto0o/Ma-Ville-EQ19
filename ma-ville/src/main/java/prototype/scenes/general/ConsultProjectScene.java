package prototype.scenes.general;

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
    private Button menuButton, createProjectButton, updateProjectButton;
    private TextField searchBar;
    private boolean intervenant;

    public ConsultProjectScene(SceneController sceneController, boolean intervenant) {
        super(sceneController);

        this.menuBox = new HBox();
        this.projectBox = new VBox();
        this.searchBox = new HBox();
        this.text = new Text("No project at the moment");
        this.menuButton = new Button("Menu");
        this.searchBar = new TextField();
        this.createProjectButton = new Button("Soumettre un nouveau projet");
        this.updateProjectButton = new Button("Mettre à jours les informations d'un projet");
        this.intervenant = intervenant;

    }
    
    @Override
    public void setScene() {

        this.root.setTop(this.menuBox);
        this.menuBox.getChildren().add(this.menuButton);

        this.root.setCenter(this.projectBox);
        this.projectBox.getChildren().addAll(this.createProjectButton, this.updateProjectButton, this.text);
        this.text.setFont(new Font("Arial", 20));
        this.projectBox.setAlignment(Pos.CENTER);
        this.projectBox.setSpacing(10);

        this.createProjectButton.setVisible(this.intervenant);
        this.createProjectButton.setManaged(this.intervenant);

        this.updateProjectButton.setVisible(this.intervenant);
        this.updateProjectButton.setManaged(this.intervenant);

        this.menuButton.setOnMouseClicked(event -> newSceneAction(event, "menu"));

        this.createProjectButton.setOnMouseClicked(event -> newSceneAction(event, "create project"));

        this.updateProjectButton.setOnMouseClicked(event -> newSceneAction(event, "update project"));


    }

}
