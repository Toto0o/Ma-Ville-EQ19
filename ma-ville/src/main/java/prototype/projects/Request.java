package prototype.projects;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Object contentant les informations d'un requêtes.
 * 
 * <p> Permet d'être affiché graphiquement avec {@link #afficher()} </p>
 *
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class Request {

    private String title;
    private String description;
    private Type type;
    private String date;
    private String status;
    private String quartier;
    private String street;

    /**
     * Constructeur
     * @param title le titre de la requête
     * @param description la description de la requête
     * @param type {@link Type} de la requête
     * @param date la date espérée
     * @param status {@link Status} de la requête
     * @param quartier le quartier
     * @param street la rue impacté
     */
    public Request(String title, String description, Type type, String date, String status, String quartier, String street) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.status = status;
        this.quartier = quartier;
        this.street = street;

    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter for quartier
    public String getQuartier() {
        return quartier;
    }

    // Setter for quartier (if needed)
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    /**
     * Méthode pour afficher graphiquemen la requête
     *
     * @return {@link HBox}
     */
    public VBox afficher() {
        List<Text> texts = new ArrayList<>();

        Text title = new Text("Titre : ");
        title.setStyle("-fx-font-weight: bold");
        HBox titleBox = new HBox(5);
        titleBox.getChildren().addAll(title, new Text(getTitle()));

        Text description = new Text("Description : ");
        description.setStyle("-fx-font-weight: bold");
        Text descriptionText = new Text(getDescription());
        descriptionText.setWrappingWidth(300);
        HBox descriptionBox = new HBox(5);
        descriptionBox.getChildren().addAll(description, descriptionText);

        Text type = new Text("Type : ");
        type.setStyle("-fx-font-weight: bold");
        HBox typeBox = new HBox(5);
        typeBox.getChildren().addAll(type /*,new Text(getType().toString())*/);
        Text date = new Text("Date : ");
        date.setStyle("-fx-font-weight: bold");

        Text status = new Text("Status : ");
        status.setStyle("-fx-font-weight: bold");
        HBox statusBox = new HBox(5);
        statusBox.getChildren().addAll(status, new Text(getStatus().toString()));

        Text quartier = new Text("Quartier : ");
        quartier.setStyle("-fx-font-weight: bold");
        HBox quartierBox = new HBox(5);
        quartierBox.getChildren().addAll(quartier);

        Text street = new Text("Street : ");
        street.setStyle("-fx-font-weight: bold");
        HBox streetBox = new HBox(5);
        streetBox.getChildren().addAll(street, new Text(getStreet()));

        // Create a bordered HBox for each request
        VBox requestBox = new VBox(10);
        requestBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;" +
                "-fx-background-color: white");
        requestBox.getChildren().addAll(
                titleBox,
                descriptionBox,
                typeBox,
                statusBox,
                quartierBox,
                streetBox
        );
        requestBox.setMaxWidth(400);
        return requestBox;
    }

    /**
     * Méthode pour formatter les dates
     * @param date la date a formater
     * @return {@link String} la date formattée
     */
    private String formatDate(String date) {
        if (date != null && date.length() == 8) {
            // Convert from YYYYMMDD to YYYY/MM/DD
            return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        }
        return date; // Return as is if the format is incorrect
    }
}
