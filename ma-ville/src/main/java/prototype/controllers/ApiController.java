package prototype.controllers;

import prototype.services.*;
import prototype.users.*;
import prototype.projects.*;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;

import prototype.entraves.Entrave;

/**
 * Controlleur des Api pour Ma-Ville
 * 
 * <p> Les api utilisé :
 *  <ul> 
 *  <li> Entraves : {@link EntravesApi} </li>
 *  <li> Projets de la ville :{@link ProjectApi} </li>
 *  <li> Projets du système : {@link ProjectApiFirebase} </li>
 *  <li> Requêtes : {@link RequestApi} </li>
 *  <li> Utilisateurs : {@link UserFirebase} </li>
 *  </ul>
 * </p>
 * 
 * <p> Les méthodes clés incluent :
 *  <ul>
 *      <li> {@link #authenticate(email, pasword)} </li>
 *      <li> {@link #updateUserInfo(utilisateur, changes)} </li>
 *      <li> {@link #getProjects()} </li>
 *      <li> {@link #saveProjectChange(key, changes)} </li>
 *      <li> {@link #addProject(title, description, type, quartier, startDate, endDate, status, userUid, streetEntrave)} </li>
 *      <li> {@link #addRequest(request)} </li>
 *      <li> {@link #getRequests()} </li>
 *      <li> {@link #getEntraves()} </li>
 *  </ul>
 * </p>
 * 
 * <p> Toutes classes nécéssitant un accès à la base de donnée du système ou de la ville passent par cette classe pour traiter l'information
 *
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 * 
 */

public class ApiController {

    private EntravesServices entravesServices;
    private ProjectService projectService;
    private IntervenantServices intervenantServices;
    private UserServices userServices;
    private AddressService addressService;
    private RequestService requestService;

    public ApiController() {
        this.entravesServices = new EntravesServices();
        this.projectService = new ProjectService();
        this.intervenantServices = new IntervenantServices();
        this.userServices = new UserServices();
        this.addressService = new AddressService();
        this.requestService = new RequestService();
    }

    /**
     * Authentifie l'utilisateur avec {@link UserFirebase}
     * 
     * @param email le nom d'utilisateur
     * @param password le mot de passe de l'utilisateur
     * 
     * @return {@link Utilisateur} l'utilisateur de la session en cours sur succès de l'authentification
     * 
     * @throws Exception sur erreur de chargement de l'Api
     */
    public void authenticate(String email, String password, Label label) throws Exception {
        this.userServices.authenticateWithFirebase(email, password, label);
    }


    /**
     * Enregistre un utilisateur comme résident avec {@link UserFirebase}
     *
     * 
     * @throws Exception sur erreur de chargement de l'api
     */
    public void register(Resident resident) throws Exception {
        this.userServices.register(resident);
    }  

    /**
     * Enregistre un utilisateur comme intervenant avec {@link UserFirebase}
     * 
     * @param utilisateur le nouvel utilisateur à enregistrer
     */
    public void intervenantRegister(Intervenant intervenant) throws Exception {
        this.userServices.register(intervenant);
    }

    /**
     * 
     * Enregistre les informations modifiés de l'utilisateur
     * 
     * @param userId le id de l'utilisateur
     * @param changes Map (id, value) des champs modifiés
     */
    public void updateUserInfo(String userId, HashMap<String,String> changes) {
        this.userServices.updateInfo(userId, changes);
    }

    public ArrayList<Project> getProjects(boolean intervenant) throws Exception {
        if (intervenant) {
            return this.intervenantServices.getProjects();
        }
        return this.projectService.getProjects();

    }

    /**
     * Sauvegarde les informations changées d'un {@link Projects projet} avec {@link ProjectApiFirebase}
     * 
     * @param key la clé Firebase associé au projet
     * @param changes Map (id, field) des champs modifiés
     */
    public void saveProjectChanges(String key, HashMap<String,String> changes) {
        this.intervenantServices.updateProject(changes, key);
    }

    /**
     * Ajoute un nouveau projet à la base de donnée avec {@link ProjectApiFirebase}
     * 
     * @param title le titre du projet
     * @param description la description du projet
     * @param type le type du projet
     * @param quartiersAffected le quartier dans lequel se déroule le projet
     * @param startDate la date de début
     * @param endDate la date de fin du projet
     * @param streetEntrave la(les) rue(s) entravée(s) par le projet
     * 
     */    
    public void addProject(Project project) {
        this.projectService.saveProjectToFirebase(project);
    }


    public void addRequest(Request request) {
        this.requestService.newRequest(request);
    }


    /**
     * Méthode pour récuperer les {@link Request requêtes} avec {@link RequestApi}
     * 
     * @return {@link ArrayList} de {@link Request requêtes}
     */
    public ArrayList<Request> getRequests() {
        return this.requestService.getRequests();
    }

    /**
     * Ajoute d'une requête dans la base de données avec {@link RequestApi}
     *
     * @param request la {@link Request requête}
     */
    public void saveRequest(Request request) {
        this.requestService.newRequest(request);
    }

    /**
     * Méthode pour récupérer les {@link Entrave entraves} avec {@link EntravesApi}
     * 
     * @return {@link ArrayList} d'{@link Entrave entraves}
     */
    public ArrayList<Entrave> getEntraves() throws Exception {
        return this.entravesServices.getEntraves();
    }

    public String getQuartier(String postalCode) throws Exception {
        return this.addressService.getQuartier(postalCode);
    }

    public String getCity(String postalCode) throws Exception {
        return this.addressService.getCity(postalCode);
    }

}
