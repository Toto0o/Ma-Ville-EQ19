package prototype.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import prototype.projects.Project;

/**
 * Controlleur des projets. Permet de
 * 
 * <ul>
 *  <li> Charger les projets : {@link #getProjects()} </li>
 *  <li> Sauvagrder un projet : {@link #saveProjectChanges(key, changes)} </li>
 *  <li> Ajouter un nouveau projet : {@link #addProject(title, description, quartierAffected, startDate, endDate, userUid, streetEntrave)}
 * </ul>
 * 
 * <p> Utilise {@link ApiController} pour charger les projets </p>
 * 
 * @param apiController
 * 
 * @author Antoine Tessier 
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class ProjectController {

    private ApiController apiController;

    public ProjectController(ApiController apiController) {
        this.apiController = apiController;
    }

    public ArrayList<Project> getProjects() throws Exception{
        return this.apiController.getProjects();
    }

    /**
     * Méthode pour sauvegarder les changements apportés à un {@link Project} avec {@link ApiController}
     * 
     * @param key la clé firebase du projet
     * @param key {@link HashMap} id,value des champs modifiés
     */
    public void saveProjectChanges(String key, HashMap<String,String> changes) {
        this.apiController.saveProjectChanges(key, changes);
    }

    public void addProject(String title, String description, String type, String quartiersAffected, String startDate, String endDate, String userUid, String streetEntrave) {
        this.apiController.addProject(
            title,
            description,
            type,
            quartiersAffected,
            startDate,
            endDate,
            "Prévu",
            userUid,
            streetEntrave

        );
    }
}
