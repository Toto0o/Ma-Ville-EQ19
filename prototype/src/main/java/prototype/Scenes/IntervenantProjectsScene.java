package prototype.Scenes;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.Controllers.SceneController;
import prototype.Projects.Project;
import prototype.service.IntervenantFunctions;

public class IntervenantProjectsScene extends Scenes {

    private VBox vbox;
    private Button backButton;
    private Button saveButton;
    private List<Project> projectsList;

    public IntervenantProjectsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.projectsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.saveButton = new Button("Save Settings");
    }

    @Override
    public void setScene() {
        this.root.setTop(this.backButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createScrollableProjectBox());
        borderPane.setBottom(saveButton);

        this.root.setCenter(borderPane);

        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("intervenantMenu");
        });

        this.saveButton.setOnMouseClicked(event -> IntervenantFunctions.saveProjectChanges(projectsList, vbox));

        IntervenantFunctions.fetchProjectsForCurrentUser(projectsList, vbox, this::updateProjectDisplay);
    }

    private ScrollPane createScrollableProjectBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.vbox.setMaxHeight(400);
        this.vbox.setStyle("-fx-padding: 10;");

        return scrollPane;
    }

    private void updateProjectDisplay() {
        vbox.getChildren().clear();
        for (Project project : projectsList) {
            VBox projectBox = new VBox(5);
            projectBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-padding: 10;");
            projectBox.setPrefWidth(300);
            projectBox.setAlignment(Pos.TOP_LEFT);

            TextField titleField = new TextField(project.getTitle());
            TextField descriptionField = new TextField(project.getDescription());
            TextField typeField = new TextField(project.getType());
            TextField quartiersField = new TextField(project.getQuartiersAffected());
            TextField roadsField = new TextField(project.getRoadsAffected());
            TextField startDateField = new TextField(project.getStartDate());
            TextField endDateField = new TextField(project.getEndDate());
            TextField horaireTravauxField = new TextField(project.getHoraireTravaux());

            projectBox.getChildren().addAll(
                    new Text("Title:"), titleField,
                    new Text("Description:"), descriptionField,
                    new Text("Type:"), typeField,
                    new Text("Quartiers Affected:"), quartiersField,
                    new Text("Roads Affected:"), roadsField,
                    new Text("Start Date:"), startDateField,
                    new Text("End Date:"), endDateField,
                    new Text("Horaire Travaux:"), horaireTravauxField);

            vbox.getChildren().add(projectBox);
        }
    }
}
