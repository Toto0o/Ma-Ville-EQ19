package prototype.notifications;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.projects.Project;

/**
 * Objet de notification
 *
 * <p>Les utilisateurs ayant la visibilité sur la notification sont souvegardé avec leur id dans une {@link ArrayList}</p>
 */
public class Notification implements Serializable {

    private String title, description, id;
    private boolean lu;
    private ArrayList<String> usersId;

    /**
     * Constructeur vide pour la déserialisation
     */
    public Notification() {}

    /**
     * Constructeur de nouvelles notifications
     *
     * @param title le titre
     * @param description la description de la notification
     */
    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
        this.lu = false;
        usersId = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getId() {
        return id;
    }
    public boolean isLu() {
        return lu;
    }
    public void setLu(boolean lu) {
        this.lu = lu;
    }
    public ArrayList<String> getUsersId() {
        return usersId;
    }
    public void setUsersId(ArrayList<String> usersId) {
        this.usersId = usersId;
    }

    public VBox afficher() {
        VBox vb = new VBox();
        Text titleText = new Text(title);
        Text descriptionText = new Text(description);
        CheckBox lu = new CheckBox("Lu");
        lu.setOnAction(event -> {setLu(lu.isSelected());});
        vb.getChildren().addAll(titleText, descriptionText);
        vb.setSpacing(10);

        if (!isLu()) {
            vb.getChildren().add(lu);
            vb.setStyle("-fx-background-color: lightred;" + "-fx-border-color: black;" + "-fx-border-width: 1px;");
            return vb;
        }
        vb.setStyle("-fx-border-color: black;" + "-fx-border-width: 1px;");
        return vb;
    }
}
