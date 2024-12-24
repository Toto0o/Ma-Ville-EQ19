package prototype.notifications;

import java.io.Serializable;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Objet de notification
 *
 * <p>Les utilisateurs ayant la visibilité sur la notification sont souvegardé avec leur id dans une {@link ArrayList}</p>
 */
public class Notification implements Serializable {

    private String title, description, id, quartier;
    private boolean lu;

    // Firebase-specific field for the unique key
    private String firebaseKey;  // Field to store the Firebase key (ID)

    /**
     * Constructeur vide pour la déserialisation
     */
    public Notification() {}

    /**
     * Constructeur de nouvelles notifications
     *
     * @param title le titre
     * @param description la description de la notification
     * @param quartier le quartier lié à la notification
     */
    public Notification(String title, String description, String quartier) {
        this.title = title;
        this.description = description;
        this.lu = false;
        this.quartier = quartier;
    }

    // Getter and setter for Firebase key (needed for updating specific notifications)
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public VBox afficher() {
        VBox vb = new VBox();
        Text titleText = new Text(title);
        Text descriptionText = new Text(description);
        Text quartierText = new Text(quartier);

        CheckBox luCheckBox = new CheckBox("Lu");
        luCheckBox.setOnAction(event -> { setLu(luCheckBox.isSelected()); });
        vb.getChildren().addAll(titleText, descriptionText, quartierText);
        vb.setSpacing(10);

        if (!isLu()) {
            vb.getChildren().add(luCheckBox);
            vb.setStyle("-fx-background-color: lightred;" + "-fx-border-color: black;" + "-fx-border-width: 1px;");
            return vb;
        }
        vb.setStyle("-fx-border-color: black;" + "-fx-border-width: 1px;");
        return vb;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quartier='" + quartier + '\'' +
                ", lu=" + lu +
                '}';
    }
}
