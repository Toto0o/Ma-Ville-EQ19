package prototype.projects;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProjectVille {

    private String id,borough, status, reason, categorie, organization;
    private int number;

    public ProjectVille(String id, String borough, String status, String reason, String categorie, String organization, int number) {
        this.id = id;
        this.borough = borough;
        this.status = status;
        this.reason = reason;
        this.categorie = categorie;
        this.organization = organization;
        this.number = number;
    }

    public String getId() {return this.id;}

    public String getBorough() {return this.borough;}

    public String getStatus() {return this.status;}

    public String getReason() {return this.reason;}

    public String getcategorie() {return this.categorie;}

    public String getOrganization() {return this.organization;}

    public int getNumber() {return this.number;}

    public VBox afficher() {
        VBox projectBox = new VBox();
        projectBox.setSpacing(10);

        // Use filteredIndex for labeling
        Text projectLabel = new Text("Project " + this.number);
        projectLabel.setFont(new Font("Arial", 18));
        projectLabel.setStyle("-fx-font-weight: bold;");

        Text projectId = new Text("ID: " + this.id);
        Text projectBorough = new Text("Arrondissement: " + this.borough);
        Text projectStatus = new Text("Statut actuel: " + this.status);
        Text projectReason = new Text("Motif: " + this.reason);
        Text projectCategory = new Text("Catégorie: " + this.categorie);
        Text projectOrganization = new Text("Intervenant: " + this.organization);

        Font font = new Font("Arial", 16);
        projectId.setFont(font);
        projectBorough.setFont(font);
        projectStatus.setFont(font);
        projectReason.setFont(font);
        projectCategory.setFont(font);
        projectOrganization.setFont(font);

        projectBox.getChildren().addAll(projectLabel, projectId, projectBorough, projectStatus, projectReason,
                projectCategory, projectOrganization);
        projectBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return projectBox;
    }

}
