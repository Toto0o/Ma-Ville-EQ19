package prototype.projects;

import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.RequestController;

public class Request {


    private Text titleText, descriptionText;
    private Text titleTitle, descriptionTitle, startDateTitle, typeTitle, residentTitle, adresseTitle;
    private Text startDateText;
    private Text typeText;
    private Text residentUsernameText;
    private Text adresseText;
    private boolean intervenant;
    private Button acceptButton, enregister;
    private DatePicker startDatePicker, endDatePicker;
    private String startDate, endDate;
    private RequestController requestController;
    private DateTimeFormatter formatter;
    private String title, description;
    private Type type;
    private String adresse;

    private VBox vBox, titleBox, descriptionBox, dateBox, typeBox, residentBox, adresseBox, acceptBox;

    public Request(String title, String description, String startDate, Type type, String userName, String adresse, RequestController requestController) {
        this.titleText = new Text(title);
        this.descriptionText = new Text(description);
        this.startDateText = new Text(startDate);
        this.typeText = new Text(type.toString());
        this.residentUsernameText = new Text(userName);
        this.adresseText = new Text(adresse);

        this.title = title;
        this.description = description;
        this.type = type;
        this.adresse = adresse;

        this.titleTitle = new Text("Titre du projet");
        this.descriptionTitle = new Text("Description détaillée");
        this.startDateTitle = new Text("Date de début espéré");
        this.typeTitle = new Text("Type de travaux");
        this.residentTitle = new Text("Résident effectuant la demande");
        this.adresseTitle = new Text("Adresse de la demande");

        this.acceptButton = new Button("Accepter la requête");
        this.enregister = new Button("Enregsitrer");

        this.vBox = new VBox();
        this.dateBox = new VBox();
        this.descriptionBox = new VBox();
        this.titleBox = new VBox();
        this.typeBox = new VBox();
        this.residentBox = new VBox();
        this.adresseBox = new VBox();
        this.acceptBox = new VBox();

        this.startDatePicker = new DatePicker();
        this.endDatePicker = new DatePicker();

        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.requestController = requestController;
    }

    public VBox afficher() {

        this.titleBox.getChildren().addAll(
            this.titleTitle,
            this.titleText
        );
        this.titleBox.setSpacing(5);

        this.descriptionBox.getChildren().addAll(
            this.descriptionTitle,
            this.descriptionText
        );
        this.descriptionBox.setSpacing(5);

        this.typeBox.getChildren().addAll(
            this.typeTitle,
            this.typeText
        );
        this.typeBox.setSpacing(5);

        this.dateBox.getChildren().addAll(
            this.startDateTitle,
            this.startDateText
        );
        this.dateBox.setSpacing(5);

        this.residentBox.getChildren().addAll(
            this.residentTitle,
            this.residentUsernameText
        );
        this.residentBox.setSpacing(5);

        this.adresseBox.getChildren().addAll(
            this.adresseTitle,
            this.adresseText
        );
        this.adresseBox.setSpacing(10);

        this.vBox.getChildren().addAll(
            this.titleBox,
            this.descriptionBox,
            this.dateBox,
            this.typeBox
        );
        this.vBox.getChildren().add(this.acceptButton);

        this.vBox.setSpacing(10);
        this.vBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 10;");
        this.vBox.setAlignment(Pos.CENTER);

        this.acceptButton.setOnMouseClicked((accept) -> {
            this.acceptBox.getChildren().addAll(
                this.startDatePicker,
                this.endDatePicker,
                this.enregister
            );
            this.acceptBox.setSpacing(10);
            this.acceptBox.setAlignment(Pos.CENTER);
            this.vBox.getChildren().clear();
            this.vBox.getChildren().add(this.acceptBox);
        //valide date? valide with conflicts...
            
        });

        this.enregister.setOnMouseClicked((enregistrer) -> {
            this.requestController.addProject(
                this.title,
                this.type,
                this.description,
                this.adresse,
                this.startDatePicker.getValue().format(this.formatter),
                this.endDatePicker.getValue().format(this.formatter)
            );
        });

        return this.vBox;
    }
}
