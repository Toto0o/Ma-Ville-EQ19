package Scenes;

import Controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestScene extends Scenes{

    private Button menu, sendRequest, newRequest;

    private VBox requestBox;

    private TextField titleField, descriptionField, dateField;
    private Text titleText, descriptionText, typeText, dateText;

    private ComboBox typeComboBox;
    private String[] types;


    public RequestScene(SceneController sceneController) {
        super(sceneController);
        this.requestBox = new VBox();
        this.menu = new Button("Menu");
        this.sendRequest = new Button("Envoyer la requête");
        this.newRequest = new Button("Ajouter une nouvelle requête de travaux");

        this.titleField = new TextField();
        this.descriptionField = new TextField();
        this.dateField = new TextField();

        this.titleText = new Text("Titre du projet");
        this.descriptionText = new Text("Description détaillée");
        this.typeText = new Text("Choisissez le type de projet");
        this.dateText = new Text("Date espéré de début du projet");

        
        this.types = new String[] {"Travaux routier", "Travaux de gaz ou électricité", "Construction ou renovation"};
        this.typeComboBox = new ComboBox(FXCollections.observableArrayList(this.types));
    }

    public void setScene() {
        this.root.setTop(this.menu);

        this.root.setCenter(this.newRequest);

        this.requestBox.getChildren().addAll(
            this.titleText,
            this.titleField,
            this.descriptionText,
            this.descriptionField,
            this.typeText,
            this.typeComboBox,
            this.dateText,
            this.dateField,
            this.sendRequest
        );
        this.requestBox.setSpacing(20);

        this.newRequest.setOnMouseClicked((requestAction) -> {
            this.root.setCenter(this.requestBox);
        });

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });


    }

}
