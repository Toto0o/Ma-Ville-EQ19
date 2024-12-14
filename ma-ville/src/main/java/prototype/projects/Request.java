package prototype.projects;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    // Constructor
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
     * Méthode pour afficher graphiquemen la requête sous forme d'une {@link HBox}
     */
    public HBox afficher() {

        String formattedDate = formatDate(getDate());

        String displayText = "Title: " + getTitle() + "\n"
                + "Description: " + getDescription() + "\n"
                + "Type: " + getType().toString() + "\n"
                + "Date: " + formattedDate + "\n"
                + "Status: " + getStatus() + "\n"
                + "Quartier: " + getQuartier();

        Text requestText = new Text(displayText);
        requestText.setStyle("-fx-font-family: 'Arial'; -fx-padding: 10;");

        // Create a bordered HBox for each request
        HBox requestBox = new HBox(10);
        requestBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        requestBox.getChildren().add(requestText);

        return requestBox;
    }

    private String formatDate(String date) {
        if (date != null && date.length() == 8) {
            // Convert from YYYYMMDD to YYYY/MM/DD
            return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        }
        return date; // Return as is if the format is incorrect
    }
}
