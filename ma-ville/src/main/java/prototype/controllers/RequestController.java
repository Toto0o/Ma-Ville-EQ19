package prototype.controllers;

import java.util.ArrayList;

import main.java.prototype.controllers.UserController;
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
 * <p> Utilise {@link ApiController} pour charger les requêtes </p>
 * <p> Utilise {@link prototype.controllers.ProjectController ProjectController} pour créer un nouveaux projet lors de l'approbation d'une candidature d'un intervenant </p>
 * <p> Utilise {@link prototype.controllers.UserController UserController} pour accéder aux informations relatives de l'utilisateur comme le quartier et la rue </p>
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

    /**
     * @return {@link ArrayList} {@link Request} avec {@link ApiController#getRequests()}
     */
    public ArrayList<Request> getRequests() {
        this.requests = this.apiController.getRequests();
        return this.requests;
    }

    /**
     * Ajoute une nouvelle requête avec {@link ApiController#addRequest(request)}
     * 
     * @param title le titre de la requête
     * @param description la description de la requête
     * @param date la date de début espéré
     * @param type le type de travail souhaité
     * @param status le status de la requête, initialement "pending"
     * 
     * <p> Le quartier est obtenu avec {@link UserController#getUser()}, {@link prototype.users.Utilisatuer#getAddress() Utilisateur.getAddress()} et {@link prototype.users.Address#getBorough() Address.getBorough()} </p>
     */
    public void addRequest(String title, String description, String type, String date, String status) {
        Request request = new Request(title, description, type, date, status, userController.getUser().getAddress().getBorough());
        this.apiController.addRequest(request);
    }

    /**
     * Méthode pour ajouter la candidature d'un intervenant à une requête.
     * 
     * <p> Utilise {@link ProjectController#addProject(title, description, type, quartier, startDate, endDate, userUid, streetEntrave)} pour créer un nouveau
     * projet associé à l'intervenant qui a posé sa candidature </p>
     * 
     * @param request la requête associé
     * @param startDate la date de début du projet
     * @param endDate la date de fin du projet
     * @param userUid le Uid du candidat
     */
    public void addCandidature(Request request, String startDate, String endDate, String userUid) {
        projectController.addProject(
            request.getTitle(),
            request.getDescription(),
            request.getType(),
            request.getQuartier(),
            startDate,
            endDate,
            userUid,
            request.getStreet() 
        );
    }

}
