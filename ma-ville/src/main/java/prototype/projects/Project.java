package prototype.projects;

import prototype.users.Intervenant;

public class Project {

    private String title;
    private String description;
    private String quartier;
    private Type type;
    private Intervenant intervenant;

    private String startDate;
    private String endDate;
    private String schedule; 

    private String status; // E.g., "Prévu", "En cours", "Terminé"

    private int number;

   
    public Project(String title, Type type, String description, String quartier, String startDate, String endDate, Intervenant intervenant, int number) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.quartier = quartier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intervenant = intervenant;
        this.status = "Prévu"; 
        this.number = number;
    }

  
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuartier() {
        return this.quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Intervenant getIntervenant() {
        return this.intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    // Method to update the status of the project
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public int getNumber() {
        return this.number;
    }

    // Méthode pour afficher les détails du projet sous forme de chaîne formatée

    @Override
    public String toString() {
        return "Project{" +
                "title='" + this.title + '\'' +
                ", type=" + this.type.toString() +
                ", description='" + this.description + '\'' +
                ", quartier='" + this.quartier + '\'' +
                ", startDate='" + this.startDate + '\'' +
                ", endDate='" + this.endDate + '\'' +
                ", schedule='" + this.schedule + '\'' +
                ", status='" + this.status + '\'' +
                ", intervenant=" + (this.intervenant != null ? intervenant.getName() : "None") +
                '}';
    }
}
