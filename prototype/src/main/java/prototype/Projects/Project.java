package prototype.Projects;

public class Project {
    private String title;
    private String description;
    private String type;
    private String quartiersAffected;
    private String roadsAffected;
    private String startDate;
    private String endDate;
    private String horaireTravaux;
    private String status;
    private String uid;
    private String firebaseKey; // New field for storing the Firebase key

    // Constructor (without firebaseKey)
    public Project(String title, String description, String type, String quartiersAffected,
            String roadsAffected, String startDate, String endDate,
            String horaireTravaux, String status, String userId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.quartiersAffected = quartiersAffected;
        this.roadsAffected = roadsAffected;
        this.startDate = startDate;
        this.endDate = endDate;
        this.horaireTravaux = horaireTravaux;
        this.status = status;
        this.uid = userId;
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

    public String getRoadsAffected() {
        return roadsAffected;
    }

    public void setRoadsAffected(String roadsAffected) {
        this.roadsAffected = roadsAffected;
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
}
