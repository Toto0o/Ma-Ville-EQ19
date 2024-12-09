package prototype.projects;

import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import prototype.entraves.Entrave;

/**
 * Objet contenant les informations d'un projet (de la ville ou du système). Contient deux constructeurs 
 * 
 * <ul>
 *  <li> Contructeur pour les projets de la ville </li>
 *  <li> Constructeur pour les projets du systeme </li>
 * </ul>
 * 
 * <p> Permet d'être affiché graphiquement avec : {@link #afficher()} </p>
 *
 *  @see prototype.controllers.ProjectController
 *  @param id
 *  @param quartierAffected
 *  @param status
 *  @param reason
 *  @param categorie
 *  @param organization
 *  @param startDate
 *  @param endDate
 *  @param streetEntrave
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class Project {

    private String title;
    private String description;
    private String type;
    private String quartiersAffected;
    private String startDate;
    private String endDate;
    private String horaireTravaux;
    private String status;
    private String uid;
    private String firebaseKey; // New field for storing the Firebase key
    private String id;
    private String intervenant;
    private String streetEntrave;

    // Constructor (without firebaseKey)
    public Project(String title, String description, String type, String quartiersAffected,
            String startDate, String endDate, String horaireTravaux, 
            String status, String userId, String streetEntrave) {
        //Cutom project (in the app)
        this.title = title;
        this.description = description;
        this.type = type;
        this.quartiersAffected = quartiersAffected;
        this.startDate = startDate;
        this.endDate = endDate;
        this.horaireTravaux = horaireTravaux;
        this.status = status;
        this.uid = userId;
        this.streetEntrave = streetEntrave;
    }

    public Project(String id, String quartierAffected, String status, String reason, String categorie, String organization, String startDate, String endDate, String streetEntrave) {
        // Projet de la ville (API)
        this.id = id;
        this.quartiersAffected = quartierAffected;
        this.type = categorie;
        this.description = reason;
        this.status = status;
        this.intervenant = organization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.streetEntrave = streetEntrave;
    }

    // Getter and Setter for Firebase key
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    // Getters and setters for the other fields
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuartiersAffected() {
        return quartiersAffected;
    }

    public void setQuartiersAffected(String quartiersAffected) {
        this.quartiersAffected = quartiersAffected;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHoraireTravaux() {
        return horaireTravaux;
    }

    public void setHoraireTravaux(String horaireTravaux) {
        this.horaireTravaux = horaireTravaux;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreetEntrave() {
        return this.streetEntrave;
    }

    public void setStreetEntrvave(String streetEntrave) {
        this.streetEntrave = streetEntrave;
    }

    public VBox afficher() {
        VBox projectBox = new VBox();
        projectBox.setSpacing(10);

        // Use filteredIndex for labeling

        Text projectId = new Text("ID: " + this.id);
        Text projectBorough = new Text("Arrondissement : " + this.quartiersAffected);
        Text projectStatus = new Text("Statut actuel : " + this.status);
        Text projectReason = new Text("Motif : " + this.description);
        Text projectCategory = new Text("Catégorie : " + this.type);
        Text projectOrganization = new Text("Intervenant : " + this.intervenant);
        Text projectStartDate = new Text("Début des travaux : " + this.startDate);
        Text projectEndDate = new Text("Fin des travaux : " + this.endDate);
        Text projectStreetEntrave = new Text("Rues impactées : " + this.streetEntrave);

        Font font = new Font("Arial", 16);
        projectId.setFont(font);
        projectBorough.setFont(font);
        projectStatus.setFont(font);
        projectReason.setFont(font);
        projectCategory.setFont(font);
        projectOrganization.setFont(font);
        projectStartDate.setFont(font);
        projectEndDate.setFont(font);
        projectStreetEntrave.setFont(font);

        projectBox.getChildren().addAll(projectId, projectBorough, projectStatus, projectReason,
                projectCategory, projectOrganization, projectStartDate, projectEndDate, projectStreetEntrave);
        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return projectBox;
    }
}
