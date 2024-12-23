package prototype.scenes.intervenant;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.application.Platform;

import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.notifications.Notification;
import prototype.services.FirebaseCallback;
import prototype.services.ServiceSession;
import prototype.users.UserSession;
import prototype.projects.*;
import prototype.scenes.Scenes;
import prototype.users.Utilisateur;

/**
 * Scene de consultation des projets pour les intervenants
 * 
 * <p>Charge les projets avec {@link ApiController#getProjects(boolean)}</p>
 * <p>Accessible par {@link prototype.scenes.general.menu.MenuScene MenuScene}</p>
 */
public class IntervenantProjectsScene extends Scenes{

    private VBox vbox;
    private Button backButton;
    private ArrayList<Project> projectsList;
    private ApiController apiController;


    /**
     * Constructeur
     * @param sceneController
     */
    public IntervenantProjectsScene(SceneController sceneController) {
        super(sceneController);
        this.vbox = new VBox(10);
        this.vbox.setId("projectView");
        this.projectsList = new ArrayList<>();
        this.backButton = new Button("Back");
        this.backButton.setId("menu");
        this.apiController = ServiceSession.getInstance().getController();
        this.projectsList = new ArrayList<>();
        UserSession.getInstance().setUserId("FAl15hewCLTJdqZVSglbw4vIUo83");
    }

    @Override
    public void setScene() {
        // Set the back button at the top
        this.root.setTop(this.backButton);

        // Set up the BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createScrollableProjectBox());

        this.root.setCenter(borderPane);

        try {
            this.apiController.getProjects(projectsList, this::updateProjectDisplay);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Set the main content in the center


        // Add the back button's functionality
        this.backButton.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });
    }

    /**
     * Crée un {@link ScrollPane} pour afficher les projets
     * @return {@link ScrollPane}
     */
    private ScrollPane createScrollableProjectBox() {
        // Make the vbox scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Set max height for scrollable area
        this.vbox.setMaxHeight(400);
        this.vbox.setStyle("-fx-padding: 10;");
        this.vbox.setAlignment(Pos.CENTER);
        this.vbox.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");

        return scrollPane;
    }


    /**
     * Display pour mettre à jours les informations d'un projet
     * @param project le projet à mettre à jour
     * @return {@link VBox} affichant les champs modifiable
     */
    private void updateProjectDisplay() {
        vbox.getChildren().clear();

        for (Project project : projectsList) {
            VBox projectBox = new VBox(5);
            projectBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-padding: 10;" +
                    "-fx-background-color: white");
            projectBox.setAlignment(Pos.CENTER);
            projectBox.setMaxWidth(300);
            Button saveButton = new Button("Enregistrer");

            TextField titleField = new TextField(project.getTitle());
            titleField.setId("title");
            titleField.setMaxWidth(250);
            TextField descriptionField = new TextField(project.getDescription());
            descriptionField.setId("description");
            descriptionField.setMaxWidth(250);
            TextField typeField = new TextField(project.getType().toString());
            typeField.setId("type");
            typeField.setMaxWidth(250);
            TextField quartiersField = new TextField(project.getQuartiersAffected());
            quartiersField.setId("quartiers");
            quartiersField.setMaxWidth(250);
            TextField roadsField = new TextField(project.getStreetEntrave());
            roadsField.setId("roads");
            roadsField.setMaxWidth(250);
            TextField startDateField = new TextField(project.getStartDate());
            startDateField.setId("startDate");
            startDateField.setMaxWidth(250);
            TextField endDateField = new TextField(project.getEndDate());
            endDateField.setId("endDate");
            endDateField.setMaxWidth(250);
            TextField horaireTravauxField = new TextField(project.getHoraireTravaux());
            horaireTravauxField.setId("horaireTravaux");
            horaireTravauxField.setMaxWidth(250);

            projectBox.getChildren().addAll(
                    new Text("Title:"), titleField,
                    new Text("Description:"), descriptionField,
                    new Text("Type:"), typeField,
                    new Text("Quartiers Affected:"), quartiersField,
                    new Text("Roads Affected:"), roadsField,
                    new Text("Start Date:"), startDateField,
                    new Text("End Date:"), endDateField,
                    new Text("Horaire Travaux:"), horaireTravauxField,
                    saveButton);

            saveButton.setOnMouseClicked((me) -> {
                this.apiController.saveProjectChanges(project);

                //Send notification on project change
                Notification notification = new Notification(
                        "Projet mis à jour",
                        project.getTitle() + " a été mis à jour", project.getQuartiersAffected()
                );

                FirebaseCallback<ArrayList<Utilisateur>> callback = new FirebaseCallback<>() {
                    @Override
                    public void onSuccessReturn(ArrayList<Utilisateur> users) {
                        Platform.runLater(() -> {
                            apiController.addNotification(notification);
                        });
                    }

                    @Override
                    public void onFailureReturn(String message) {
                    }

                    @Override
                    public void onSuccess() {};
                };
                this.apiController.getResidents(callback);

            });

            vbox.getChildren().add(projectBox);
        }
    }
}
