package prototype.controllers;

import prototype.api.firebase.UserFirebase;
import prototype.api.ville.EntravesApi;
import prototype.api.ville.ProjectApi;
import prototype.api.firebase.RequestApi;
import prototype.api.firebase.ProjectApiFirebase;
import prototype.users.*;
import prototype.projects.*;

import java.util.ArrayList;
import java.util.HashMap;

import prototype.api.ville.QuartierApi;
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

    private EntravesApi entravesApi;
    private ProjectApi projectApi;
    private ProjectApiFirebase projectApiFirebase;
    private UserFirebase userApi;
    private QuartierApi quartierApi;
    private RequestApi requestApi;

    public ApiController() {
        this.entravesApi = new EntravesApi();
        this.projectApi = new ProjectApi();
        this.userApi = new UserFirebase();
        this.requestApi = new RequestApi();
        this.projectApiFirebase = new ProjectApiFirebase();
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
    public Utilisateur authenticate(String email, String password) throws Exception {
        return this.userApi.authenticate(email, password);    
        
    }


    /**
     * Enregistre un utilisateur comme résident avec {@link UserFirebase}
     * 
     * @param utilisateur le nouvel utilisateur à enrgistrer
     * 
     * @throws Exception sur erreur de chargement de l'api
     */
    public void residentRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "resident");
    }  

    /**
     * Enregistre un utilisateur comme intervenant avec {@link UserFirebase}
     * 
     * @param utilisateur le nouvel utilisateur à enregistrer
     */
    public void intervenantRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "intervenant");
    }

    /**
     * 
     * Enregistre les informations modifiés de l'utilisateur
     * 
     * @param utilisateur l'utilisateur
     * @param changes Map (id, value) des champs modifiés
     */
    public void updateUserInfo(Utilisateur utilisateur, HashMap<String,String> changes) {
        this.userApi.updateInfo(utilisateur, changes);
    }

    /**
     * Méthode pour récuperer les projets de la ville et du système avec {@link ProjectApi} et {@link ProjectApiFirebase}
     * 
     * @return {@link ArrayList} une liste des {@link Project projets} chargés
     * 
     * @throws Exception sur erreur de chargement des api
     */
    public ArrayList<Project> getProjects() throws Exception {
        ArrayList<Project> projects = new ArrayList<>();

        projects.addAll(this.projectApi.getProjects());
        projects.addAll(this.projectApiFirebase.getProjects());

        return projects;
    }

    /**
     * Sauvegarde les informations changées d'un {@link Projects projet} avec {@link ProjectApiFirebase}
     * 
     * @param key la clé Firebase associé au projet
     * @param changes Map (id, field) des champs modifiés
     */
    public void saveProjectChanges(String key, HashMap<String,String> changes) {
        this.projectApiFirebase.saveProjectChanges(key, changes);
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
    public void addProject(String title, String description, String type, String quartiersAffected, String startDate, String endDate, String status, String userUid, String streetEntrave) {
        // implemeter avec Firebase pour sauvegarder un nouveau projet!
    }
    
    /**
     * Ajoute d'une requête dans la base de données avec {@link RequestApi}
     * 
     * @param request la {@link Request requête}
     */
    public void addRequest(Request request) {
        // implementer avec Firebase pour sauvegarder une requete!
    }

    /**
     * Méthode pour récuperer les {@link Request requêtes} avec {@link RequestApi}
     * 
     * @return {@link ArrayList} de {@link Request requêtes}
     */
    public ArrayList<Request> getRequests() {
        return this.requestApi.getRequests();
    }

    /**
     * Méthode pour récupérer les {@link Entrave entraves} avec {@link EntravesApi}
     * 
     * @return {@link ArrayList} d'{@link Entrave entraves}
     */
    public ArrayList<Entrave> getEntraves() throws Exception {
        return this.entravesApi.getEntraves();
    }

    

    /* public String getQuartierName(String address) throws Exception {
        return this.quartierApi.getName(address);
        
    } */

}
