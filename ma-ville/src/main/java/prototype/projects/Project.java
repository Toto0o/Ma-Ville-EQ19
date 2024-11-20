package prototype.projects;

import prototype.users.Intervenant;

public class Project  {

    private String title, description, quartier;
    private Type type;
    private Intervenant intervenant;



    public Project(String title, String type, String description, String quartier) {
        this.title = title;
        this.description = description;
        this.quartier = quartier;
    }
}
