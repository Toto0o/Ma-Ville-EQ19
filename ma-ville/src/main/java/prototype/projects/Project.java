package prototype.projects;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Objet contenant les informations d'un projet
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class Project {

    private String title;
    private String description;
    private Type type;
    private String quartiersAffected;
    private String startDate;
    private String endDate;
    private String horaireTravaux;
    private Status status;
    private String uid;
    private String firebaseKey; // New field for storing the Firebase key
    private String streetEntrave;

    /**
     * Constructeur vide pour la serialization
     */
    public Project() {}

    /**
     * Constructeur pour les projets soumis à travers le système
     * @param title le titre du projet
     * @param description la description du projet
     * @param type {@link Type} du projet
     * @param quartiersAffected le quartier où se déroule le projet
     * @param startDate la date de début
     * @param endDate la date de fin
     * @param horaireTravaux les heures de travail
     * @param status {@link Status} du projet
     * @param streetEntrave la(les) rue(s) entravée(s)
     */
    public Project(
            String title,
            String description,
            Type type,
            String quartiersAffected,
            String startDate,
            String endDate,
            String horaireTravaux,
            Status status,
            String userId,
            String streetEntrave) {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public Status getStatus() {
        this.status = checkStatus();
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStreetEntrave() {
        return this.streetEntrave;
    }

    public void setStreetEntrave(String streetEntrave) {
        this.streetEntrave = streetEntrave;
    }

    /**
     * Méthode pour afficher graphiquement le projet
     * @return {@link VBox}
     */
    public VBox afficher() {
        VBox projectBox = new VBox();
        projectBox.setSpacing(10);

        // Use filteredIndex for labeling

        Text projectId = new Text("ID: " + this.title);
        Text projectBorough = new Text("Arrondissement : " + this.quartiersAffected);
        Text projectStatus = new Text("Statut actuel : " + this.status);
        Text projectReason = new Text("Description : " + this.description);
        Text projectType = new Text("Type : " + this.type);
        Text projectOrganization = new Text("Intervenant : " + this.uid);
        Text projectStartDate = new Text("Début des travaux : " + this.startDate);
        Text projectEndDate = new Text("Fin des travaux : " + this.endDate);
        Text projectHoraireTravaux = new Text("Horaire de travaux : " + this.horaireTravaux);
        Text projectStreetEntrave = new Text("Rues impactées : " + this.streetEntrave);

        Font font = new Font("Arial", 16);
        projectId.setFont(font);
        projectBorough.setFont(font);
        projectStatus.setFont(font);
        projectType.setFont(font);
        projectReason.setFont(font);
        projectOrganization.setFont(font);
        projectStartDate.setFont(font);
        projectEndDate.setFont(font);
        projectStreetEntrave.setFont(font);
        projectHoraireTravaux.setFont(font);

        projectBox.getChildren().addAll(projectId, projectBorough, projectStatus, projectReason, projectType,
                 projectOrganization, projectStartDate, projectEndDate, projectHoraireTravaux, projectStreetEntrave);
        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return projectBox;
    }

    /**
     * Méthode pour vérifier le status d'un projet
     * @return {@link Status} ({@link Enum} des status possible
     */
    public Status checkStatus() {
        LocalDate start = LocalDate.parse(getStartDate());
        LocalDate end = LocalDate.parse(getEndDate());
        LocalDate now = LocalDate.now();
        if (now.isAfter(start)) {
            if (now.isBefore(end)) {
                return Status.EN_COURS;
            }
            return Status.TERMINE;
        }
        return Status.PREVU;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("type", type);  // Ensure type is a String
        map.put("quartiersAffected", quartiersAffected);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("horaireTravaux", horaireTravaux);
        map.put("status", status);  // Ensure status is a String
        map.put("uid", uid);
        map.put("streetEntrave", streetEntrave);

        return map;
    }



}
