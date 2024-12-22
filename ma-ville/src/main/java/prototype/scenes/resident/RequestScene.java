package prototype.scenes.resident;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.projects.Request;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.scenes.Scenes;
import prototype.services.ServiceSession;
import prototype.users.UserSession;
import prototype.users.Utilisateur;

/**
 * Scene de soumission d'une requête de travail pour les résidents
 *
 * <p>Utilise {@link ApiController#saveRequest(Request)} pour enregistrer la requête soumise</p>
 *
 * <p>Accessible par {@link prototype.scenes.general.menu.MenuScene MenuScene}</p>
 */
public class RequestScene extends Scenes{

    private Button menu, sendRequest, newRequest;

    private VBox requestBox;

    private TextField titleField;
    private DatePicker datePicker;
    private TextArea descriptionArea;
    private Text titleText, descriptionText, typeText, dateText;
    private ComboBox<Type> typeComboBox;
    private ApiController apiController;
    private Label label;

    /**
     * Constructeur
     * @param sceneController
     */
    public RequestScene(SceneController sceneController) {

        super(sceneController);
        this.requestBox = new VBox();
        this.requestBox.setId("requestBox");
        this.menu = new Button("Menu");
        this.menu.setId("menu");
        this.sendRequest = new Button("Envoyer la requête");
        this.sendRequest.setId("sendRequest");
        this.newRequest = new Button("Ajouter une nouvelle requête de travaux");
        this.newRequest.setId("newRequest");

        this.titleField = new TextField();
        this.titleField.setId("title");
        this.descriptionArea = new TextArea();
        this.descriptionArea.setId("description");
        this.datePicker = new DatePicker();
        this.datePicker.setId("date");

        this.titleText = new Text("Titre du projet");
        this.descriptionText = new Text("Description détaillée");
        this.typeText = new Text("Choisissez le type de projet");
        this.dateText = new Text("Date espéré de début du projet");

        this.typeComboBox = new ComboBox<>();
        this.typeComboBox.setId("type");

        this.label = new Label("Remplissez les champs pour envoyer la requête");
        this.label.setId("label");

        this.apiController = ServiceSession.getInstance().getController();


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
            this.datePicker,
            this.sendRequest,
            this.label
        );
        this.typeComboBox.getItems().addAll(Type.values());
        this.titleField.setMaxWidth(250);
        this.descriptionArea.setMaxWidth(250);
        this.typeComboBox.setMaxWidth(250);
        
        
        this.datePicker.setPromptText("Date de début espérée");
        this.datePicker.setValue(LocalDate.now());

        this.requestBox.setSpacing(20);
        this.requestBox.setAlignment(Pos.CENTER);

        this.newRequest.setOnMouseClicked((requestAction) -> {
            this.root.setCenter(this.requestBox);
        });

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

        this.sendRequest.setOnMouseClicked((newRequest) -> {
            Request request = new Request(
                    this.titleField.getText().trim(),
                    this.descriptionArea.getText(),
                    this.typeComboBox.getValue(),
                    this.datePicker.getValue().format(this.formatter),
                    "En attente d'apporbation",
                    UserSession.getInstance().getUser().getAddress().getBorough(),
                    UserSession.getInstance().getUser().getAddress().getStreet()
            );
            this.apiController.saveRequest(request);
            this.label.setText("Votre requête à été enregistré. Un intervenant consultera votre demande sous peu");

        });


    }

}
