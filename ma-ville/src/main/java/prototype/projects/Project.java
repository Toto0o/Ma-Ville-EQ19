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

   
    public Project(String title, Type type, String description, String quartier, String startDate, String endDate, Intervenant intervenant) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.quartier = quartier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;
        this.intervenant = intervenant;
        this.status = "Prévu"; 
    }

  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    // Method to update the status of the project
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Méthode pour afficher les détails du projet sous forme de chaîne formatée

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", quartier='" + quartier + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", schedule='" + schedule + '\'' +
                ", status='" + status + '\'' +
                ", intervenant=" + (intervenant != null ? intervenant.getUsername() : "None") +
                '}';
    }
}
