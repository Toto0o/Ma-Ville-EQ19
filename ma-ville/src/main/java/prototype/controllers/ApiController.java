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
 *  <li> Entraves : {@link prototype.api.ville.EntravesApi} </li>
 *  <li> Projets de la ville :{@link prototype.api.ville.ProjectApi} </li>
 *  <li> Projets du système : {@link prototype.api.firebase.ProjectApiFirebase} </li>
 *  <li> Requêtes : {@link prototype.api.firebase.RequestApi} </li>
 *  <li> Utilisateurs : {@link prototype.api.firebase.UserFirebase} </li>
 *  </ul>
 * </p>
 * 
 * <p> Les méthodes utilisées :
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

    public Utilisateur authenticate(String email, String password) throws Exception {
        return this.userApi.authenticate(email, password);    
        
    }

    public void residentRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "resident");
    }

    public void intervenantRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "intervenant");
    }

    public void updateUserInfo(Utilisateur utilisateur, HashMap<String,String> changes) {
        this.userApi.updateInfo(utilisateur, changes);
    }

    public ArrayList<Project> getProjects() throws Exception {
        ArrayList<Project> projects = new ArrayList<>();

        projects.addAll(this.projectApi.getProjects());
        projects.addAll(this.projectApiFirebase.getProjects());

        return projects;
    }

    public void saveProjectChanges(String key, HashMap<String,String> changes) {
        this.projectApiFirebase.saveProjectChanges(key, changes);
    }

    public void addProject(String title, String description, String type, String quartiersAffected, String startDate, String endDate, String status, String userUid, String streetEntrave) {
        // implemeter avec Firebase pour sauvegarder un nouveau projet!
    }

    public void addRequest(Request request) {
        // implementer avec Firebase pour sauvegarder une requete!
    }

    public ArrayList<Request> getRequests() {
        return this.requestApi.getRequests();
    }

    public ArrayList<Entrave> getEntraves() throws Exception {
        return this.entravesApi.getEntraves();
    }

    

    /* public String getQuartierName(String address) throws Exception {
        return this.quartierApi.getName(address);
        
    } */

}
