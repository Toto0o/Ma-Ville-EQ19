package prototype.controllers;

import java.util.ArrayList;

import prototype.projects.Request;
import prototype.projects.Type;

/**
 * Controlleur des requêtes. Permet de :
 * 
 * <ul>
 *  <li> Charger les requêtes {@link #getRequests()} </li>
 *  <li> Ajouter un requête {@link #addRequest(title, description, type, date, status, quartier)} </li>
 *  <li> Ajouter la candidature d'un intervenant {@link #addCandidature(request, startDate, endDate, userUid)}
 * </ul>
 * 
 * <p> Utilise {@link prototype.controllers.ApiController} pour charger les requêtes </p>
 * 
 * @param userController
 * @param projectController
 * @param apiController
 * 
 * @author Antoine Tessier 
 * @author Anmar Rahman 
 * @author Mostafa Heider
 */

public class RequestController {

    private ArrayList<Request> requests;
    
    private ProjectController projectController;
    private UserController userController;
    private ApiController apiController;

    public RequestController(UserController userController, ProjectController projectController, ApiController apiController) {
        this.userController = userController;
        this.projectController = projectController;
        this.apiController = apiController;
        this.requests = new ArrayList<>();
    }

    public ArrayList<Request> getRequests() {
        this.requests = this.apiController.getRequests();
        return this.requests;
    }

    public void addRequest(String title, String description, String type, String date, String status, String quartier) {
        Request request = new Request(title, description, type, date, status, quartier);
        this.apiController.addRequest(request);
    }

    public void addCandidature(Request request, String startDate, String endDate, String userUid) {
        projectController.addProject(
            request.getTitle(),
            request.getDescription(),
            request.getType(),
            request.getQuartier(),
            startDate,
            endDate,
            userUid,
            "Street" // implementer ou en input pour la rue affecte (peut etre avec les info du demandeur, recuperer sa rue...)
        );
    }

}
