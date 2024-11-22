package prototype.Requests;

public class Request {
    private String title;
    private String description;
    private String type;
    private String date;
    private String status;
    private String quartier;

    // Constructor
    public Request(String title, String description, String type, String date, String status, String quartier) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.status = status;
        this.quartier = quartier;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
