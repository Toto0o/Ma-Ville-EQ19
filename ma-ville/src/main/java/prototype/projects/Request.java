package prototype.projects;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.users.Utilisateur;

public class Request {


    private Text title, description;
    private Text titleTitle, descriptionTitle, startDateTitle, typeTitle, residentTitle;
    private Text startDate;
    private Text type;

    private Text resident;

    private VBox vBox, titleBox, descriptionBox, dateBox, typeBox, residentBox;

    public Request(String title, String description, String startDate, Enum<Type> type, Utilisateur resident) {
        this.title = new Text(title);
        this.description = new Text(description);
        this.startDate = new Text(startDate);
        this.type = new Text(type.toString());
        this.resident = new Text(resident.getUsername());

        this.titleTitle = new Text("Titre du projet");
        this.descriptionTitle = new Text("Description détaillée");
        this.startDateTitle = new Text("Date de début espéré");
        this.typeTitle = new Text("Type de travaux");
        this.residentTitle = new Text("Résident effectuant la demande");

        this.vBox = new VBox();
        this.dateBox = new VBox();
        this.descriptionBox = new VBox();
        this.titleBox = new VBox();
        this.typeBox = new VBox();
        this.residentBox = new VBox();


    }

    public VBox afficher() {

        this.titleBox.getChildren().addAll(
            this.titleTitle,
            this.title
        );
        this.titleBox.setSpacing(5);

        this.descriptionBox.getChildren().addAll(
            this.descriptionTitle,
            this.description
        );
        this.descriptionBox.setSpacing(5);

        this.typeBox.getChildren().addAll(
            this.typeTitle,
            this.type
        );
        this.typeBox.setSpacing(5);

        this.dateBox.getChildren().addAll(
            this.startDateTitle,
            this.startDate
        );
        this.dateBox.setSpacing(5);

        this.residentBox.getChildren().addAll(
            this.residentTitle,
            this.resident
        );
        this.residentBox.setSpacing(5);

        this.vBox.getChildren().addAll(
            this.titleBox,
            this.descriptionBox,
            this.dateBox,
            this.typeBox
        );
        this.vBox.setSpacing(10);

        return this.vBox;
    }
}
