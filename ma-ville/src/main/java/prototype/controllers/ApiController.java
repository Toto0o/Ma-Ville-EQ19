package prototype.controllers;

import org.apache.http.auth.InvalidCredentialsException;
import prototype.notifications.Notification;
import prototype.services.*;
import prototype.users.*;
import prototype.projects.*;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;

import prototype.entraves.Entrave;

/**
 * Controlleur des services d'api pour Ma-Ville
 * 
 * <p> Les api utilisé :
 *  <ul>
 *      <li> {@link AddressService} pour valider l'adresse et obtenir le quartier</li>
 *      <li> {@link EntravesServices} pour charger les entraves routières</li>
 *      <li> {@link ProjectService} pour charger les travaux de la ville de Montréal</li>
 *      <li> {@link IntervenantServices} pour les projets associés à un intervenant
 *      <li> {@link RequestService} pour charger et sauvegarder les requêtes</li>
 *      <li> {@link UserServices} pour l'authentification et l'enregistrement d'utilisateur </li>
 *  </ul>
 * </p>
 * 
 * <p> Les méthodes clés incluent :
 *  <ul>
 *      <li> {@link #authenticate(String, String, Label)} </li>
 *      <li> {@link #updateUserInfo(String, HashMap)} </li>
 *      <li> {@link #getProjects(boolean)} </li>
 *      <li> {@link #saveProjectChanges(String, HashMap)} </li>
 *      <li> {@link #addProject(Project)} </li>
 *      <li> {@link #addRequest(Request)} </li>
 *      <li> {@link #getRequests()} </li>
 *      <li> {@link #getEntraves()} </li>
 *  </ul>
 * </p>
 * 
 * <p> Toutes classes nécéssitant un accès à la base de donnée du système ou de la ville passent par cette classe pour traiter l'information </p>
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
    private NotificationService notificationService;

    /**
     * Instancie les classes de services
     */
    public ApiController() {
        this.entravesServices = new EntravesServices();
        this.projectService = new ProjectService();
        this.intervenantServices = new IntervenantServices();
        this.userServices = new UserServices();
        this.addressService = new AddressService();
        this.requestService = new RequestService();
        this.notificationService = new NotificationService();
    }

    /**
     * Authentifie l'utilisateur avec {@link UserServices#authenticateWithFirebase(String, String, Label)}
     *
     * @param email    le nom d'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @param label    le status de l'authentification; permet d'afficher les messages d'erreur
     * @return
     * @throws Exception sur erreur de chargement ou des indentifiants invalide
     */
    public void authenticate(String email, String password) throws IllegalArgumentException {
        this.userServices.authenticateWithFirebase(email, password);
    }

    /**
     * Enregistre un utilisateur comme résident avec {@link UserServices#register(Resident)}
     *
     * @param resident le résident a enregistrer
     * 
     * @throws Exception sur erreur de chargement de l'api
     */
    public void register(Resident resident) throws Exception {
        this.userServices.register(resident);
    }  

    /**
     * Enregistre un utilisateur comme intervenant avec {@link UserServices#register(Intervenant)}
     * 
     * @param intervenant l'intervenant a enregistrer
     */
    public void register(Intervenant intervenant) throws Exception {
        this.userServices.register(intervenant);
    }

    /**
     * 
     * Enregistre les informations modifiés de l'utilisateur
     *
     */
    public void updateUserInfo(Utilisateur utilisateur) {
        this.userServices.updateUser(utilisateur);
    }

    public Utilisateur getUser(String id) {
        return this.userServices.getUser(id);
    }

    public ArrayList<String> getUsers() {
        return this.userServices.getUsers();
    }

    /**
     * Charge les projets avec {@link ProjectService#getProjects()} (ou {@link IntervenantServices#getProjects()}
     * si l'utilisateur est un intervenant)
     *
     * @param intervenant true si l'utilisateur est intervenant
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     * @throws Exception sur erreur de chargement
     */
    public ArrayList<Project> getProjects() throws Exception {
        return this.projectService.getProjects();
    }

    public void getProjects(ArrayList<Project> projects, Runnable updateDisplayCallback) throws Exception {
        this.intervenantServices.getProjects(projects, updateDisplayCallback);
    }

    /**
     * Sauvegarde les informations changées d'un {@link Project projet} avec {@link IntervenantServices#saveProjectChanges(HashMap, String)}
     * 
     * @param key la clé Firebase associé au projet
     * @param changes {@link HashMap} (id, field) des champs modifiés
     */
    public void saveProjectChanges(String key, HashMap<String,String> changes) {
        this.intervenantServices.updateProject(changes, key);
    }

    /**
     * Ajoute un nouveau projet à la base de donnée avec {@link ProjectService#saveProjectToFirebase(Project)}
     *
     * @param project {@link Project} à sauvegarder
     */    
    public void addProject(Project project) {
        this.projectService.saveProjectToFirebase(project);
    }

    /**
     * Ajoute une nouvelle {@link Request} à la base de donnée avec {@link RequestService#saveRequest(Request)}
     * @param request {@link Request} à sauvegarder
     */
    public void addRequest(Request request) {
        this.requestService.newRequest(request);
    }


    /**
     * Méthode pour charger les {@link Request requêtes} avec {@link RequestService#getRequests()}
     * 
     * @return {@link ArrayList}&lt;{@link Request}&gt;
     */
    public void getRequests(ArrayList<Request> requestsList, Runnable updateDisplayCallback) {
        this.requestService.getRequests(requestsList, updateDisplayCallback);
    }

    /**
     * Ajoute d'une requête dans la base de données avec {@link RequestService#saveRequest(Request)}
     *
     * @param request {@link Request} a sauvegarder
     */
    public void saveRequest(Request request) {
        this.requestService.newRequest(request);
    }

    /**
     * Méthode pour récupérer les {@link Entrave entraves} avec {@link EntravesServices#getEntraves()}
     * 
     * @return {@link ArrayList}&lt;{@link Entrave entraves}&gt;
     *
     * @throws Exception sur erreur de chargement
     */
    public ArrayList<Entrave> getEntraves() throws Exception {
        return this.entravesServices.getEntraves();
    }

    /**
     * Charger le quartier d'une adresse avec {@link AddressService#getQuartier(String)}
     *
     * @param postalCode le code postal de l'adresse
     * @return {@link String} le quartier associé
     * @throws Exception sur erreur de chargement (ou sur un code postal invalide)
     */
    public String getQuartier(String postalCode) throws Exception {
        return this.addressService.getQuartier(postalCode);
    }

    /**
     * Charger la ville d'une adresse avec {@link AddressService#getCity(String)}
     * @param postalCode le code postal de l'adresse
     * @return {@link String} la ville associée
     * @throws Exception sur erreur de chargement (ou sur un code postal invalide)
     */
    public String getCity(String postalCode) throws Exception {
        return this.addressService.getCity(postalCode);
    }

    public ArrayList<Notification> getNotifications() throws Exception {
        return this.notificationService.getNotifications();
    }

    public void addNotification(Notification notification) {
        this.notificationService.addNotification(notification);
    }

}
