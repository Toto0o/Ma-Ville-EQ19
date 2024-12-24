package prototype.entraves;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Objet contentant les informations d'une entrave.
 * 
 * <p> Permet d'afficher l'entrave graphiquement avec {@link #afficher()} <p>
 *
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class Entrave {

    private String id_request, streetid, shortname, streetimpacttype;
    private VBox entraveBox;

    /**
     * Constructeur
     * @param id_request le id de l'entrave
     * @param streetid le nom de la rue
     * @param shortname le type d'entrave
     * @param streetimpacttype l'impact sur la voie
     */
    public Entrave(String id_request, String streetid, String shortname, String streetimpacttype) {
        this.id_request = id_request;
        this.streetid = streetid;
        this.shortname = shortname;
        this.streetimpacttype = streetimpacttype;
        this.entraveBox = new VBox();
    }

    public String getstreetid() {
        return this.streetid;
    }

    public String getid_request() {
        return this.id_request;
    }

    public String getshortname() {
        return this.shortname;
    }

    public String getstreetimpacttype() {
        return this.streetimpacttype;
    }

    /**
     * Méthode pour afficher graphiquement l'entrave
     * 
     * @return {@link VBox} javafx layout VBox
     */
    public VBox afficher() {

        // Add the selected details into the VBox
        Text entraveIdRequest = new Text("Identifiant du travail: " + this.id_request);
        Text entraveStreetId = new Text("Identifiant de la rue: " + this.streetid);
        Text entraveShortname = new Text("Nom de la rue: " + this.shortname);
        Text entraveImpactType = new Text("Impact sur la rue: " + this.streetimpacttype);

        
        entraveBox.setSpacing(10);
        Text entraveLabel = new Text("Entrave routière");
        entraveLabel.setFont(new Font("Arial", 18));
        entraveLabel.setStyle("-fx-font-weight: bold;");

        // Add to the VBox
        entraveBox.getChildren().addAll(entraveLabel, entraveIdRequest, entraveStreetId,
                entraveShortname, entraveImpactType);

        // Add a border to the entrave VBox
        entraveBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return entraveBox;
    }

}
