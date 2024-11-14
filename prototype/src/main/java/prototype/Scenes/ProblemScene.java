package prototype.Scenes;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;

public class ProblemScene extends Scenes {

    private Button menu, sendProblem;
    private VBox problemBox;
    private TextField typeField, descriptionField;
    private Text typeText, descriptionText;

    public ProblemScene(SceneController sceneController) {
        super(sceneController);
        this.menu = new Button("Menu");
        this.sendProblem = new Button("Envoyer la requête");
        this.problemBox = new VBox();
        this.descriptionField = new TextField();
        this.descriptionText = new Text("Ajouter une description du problème");
        this.typeField = new TextField();
        this.typeText = new Text("Type de problème"); /* Faire une liste déroulante??? */
    }

    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.problemBox);

        this.problemBox.getChildren().addAll(
                this.typeText,
                this.typeField,
                this.descriptionText,
                this.descriptionField,
                this.sendProblem);

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

    }

}
