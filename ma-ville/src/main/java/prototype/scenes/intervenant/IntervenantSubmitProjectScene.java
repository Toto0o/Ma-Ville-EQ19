package prototype.scenes.intervenant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.notifications.Notification;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.services.FirebaseCallback;
import prototype.services. QuartiersServices;
import prototype.scenes.Scenes;
import prototype.services.ServiceSession;
import prototype.users.UserSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import prototype.users.Utilisateur;

public class IntervenantSubmitProjectScene extends Scenes {

    private Button menu, sendRequest, newRequest;
    private VBox requestBox;
    private TextField titleField,streetsField;
    private DatePicker startDatePicker, endDatePicker;
    private TextArea descriptionArea;
    private Text titleText, descriptionText, typeText, dateText, streetText, boroughText;
    private ComboBox<Type> typeComboBox;
    private ComboBox<String> boroughComboBox, streetComboBox;
    private ApiController apiController;
    private Label label;

    // To store quartiers and streets dynamically
    private List<String> availableQuartiers;
    private List<String> availableStreets;

    // QuartiersServices to fetch data
    private QuartiersServices quartiersServices;

    public IntervenantSubmitProjectScene(SceneController sceneController) {
        super(sceneController);
        this.requestBox = new VBox();
        this.requestBox.setId("requestBox");
        this.menu = new Button("Menu");
        this.menu.setId("menu");
        this.sendRequest = new Button("Envoyer le projet");
        this.sendRequest.setId("sendRequest");
        this.newRequest = new Button("Ajouter un nouveau projet");
        this.newRequest.setId("newRequest");

        this.titleField = new TextField();
        this.titleField.setId("title");
        this.streetsField = new TextField();
        this.streetsField.setId("streets");
        this.descriptionArea = new TextArea();
        this.descriptionArea.setId("description");
        this.startDatePicker = new DatePicker();
        this.startDatePicker.setId("startDate");
        this.endDatePicker = new DatePicker();
        this.endDatePicker.setId("endDate");

        this.titleText = new Text("Titre du projet");
        this.descriptionText = new Text("Description détaillée");
        this.typeText = new Text("Choisissez le type de projet");
        this.streetText = new Text("Entrez les rues affectées");
        this.dateText = new Text("Dates de début et de fin");
        this.boroughText = new Text("Sélectionnez les quartiers affectés");

        this.typeComboBox = new ComboBox<>();
        this.typeComboBox.setId("type");

        this.boroughComboBox = new ComboBox<>();
        this.boroughComboBox.setId("borough");


        this.label = new Label("Remplissez les champs pour envoyer le projet");
        this.label.setId("label");

        this.apiController = ServiceSession.getInstance().getController();
        this.quartiersServices = new QuartiersServices();  // Initialize QuartiersServices

        this.availableQuartiers = quartiersServices.quartiersInMontreal();

        // Populate boroughComboBox with the fetched quartiers
        this.boroughComboBox.getItems().addAll(availableQuartiers);



    }

    @Override
    public void setScene() {
        this.root.setTop(this.menu);

        this.root.setCenter(this.newRequest);

        this.requestBox.getChildren().addAll(
                this.titleText,
                this.titleField,
                this.descriptionText,
                this.descriptionArea,
                this.typeText,
                this.typeComboBox,
                this.boroughText,
                this.boroughComboBox,
                this.streetText,
                this.streetsField,
                this.dateText,
                this.startDatePicker,
                this.endDatePicker,
                this.sendRequest,
                this.label
        );

        this.typeComboBox.getItems().addAll(Type.values());

        this.titleField.setMaxWidth(250);
        this.descriptionArea.setMaxWidth(250);
        this.typeComboBox.setMaxWidth(250);
        this.boroughComboBox.setMaxWidth(250);
        this.streetsField.setMaxWidth(250);

        this.startDatePicker.setPromptText("Date de début");
        this.endDatePicker.setPromptText("Date de fin");
        this.startDatePicker.setValue(LocalDate.now());
        this.endDatePicker.setValue(LocalDate.now());

        this.requestBox.setSpacing(20);
        this.requestBox.setAlignment(Pos.CENTER);

        this.newRequest.setOnMouseClicked((requestAction) -> {
            this.root.setCenter(this.requestBox);
        });

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

        this.sendRequest.setOnMouseClicked((newRequest) -> {
            // Gather the project data from the form inputs
            String title = this.titleField.getText().trim();
            String description = this.descriptionArea.getText();
            Type type = this.typeComboBox.getValue();
            String quartiersAffected = this.boroughComboBox.getValue();
            String streetsAffected = this.streetsField.getText().trim();
            String startDate = this.startDatePicker.getValue().format(this.formatter);
            String endDate = this.endDatePicker.getValue().format(this.formatter);
            String horaireTravaux = "9:00 - 17:00";  // Example work hours

            // Create a new Project object with quartiers and streets included

                    Project project = new Project(
                    title,
                    description,
                    type,
                    quartiersAffected,
                    startDate,
                    endDate,
                    horaireTravaux,
                    Status.PREVU,  // Set status to "PREVU"
                    UserSession.getInstance().getUserId(),
                    streetsAffected  // Include the streets
            );

            this.apiController.addProject(project);
            // Send notification on new project added
            Notification notification = new Notification(
                    project.getTitle(),
                    "Un nouveau projet à été approuvé dans votre quartier : " + project.getQuartiersAffected(), project.getQuartiersAffected()
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
                public void onSuccess() {}
            };
            this.apiController.getResidents(callback);


            // Save the project to Firebase
//            saveProjectToFirebase(project);

            // Update label with confirmation message
            this.label.setText("Votre projet a été enregistré. Il sera traité sous peu.");
        });
    }

    /**
     * Save the project data to Firebase
     * @param project The project to be saved
     */
    public void saveProjectToFirebase(Project project) {
        try {
            // Generate a random project ID
            String projectId = java.util.UUID.randomUUID().toString();
            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference projectRef = database.getReference("projects").child(projectId);

            // Set the project data under the "projects" node
            projectRef.setValueAsync(project);
            System.out.println("Project saved to Firebase under ID: " + projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
