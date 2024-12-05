package prototype.controllers;

import prototype.api.firebase.UserFirebase;
import prototype.api.ville.EntravesApi;
import prototype.api.ville.ProjectApi;
import prototype.users.*;
import prototype.projects.*;

import java.util.ArrayList;

import prototype.api.ville.QuartierApi;
import prototype.entraves.Entrave;

public class ApiController {

    private EntravesApi entravesApi;
    private ProjectApi projectApi;
    private UserFirebase userApi;
    private QuartierApi quartierApi;

    public ApiController() {
        this.entravesApi = new EntravesApi();
        this.projectApi = new ProjectApi();
        this.userApi = new UserFirebase();
    }

    public Utilisateur authenticate(String email, String password) throws Exception {
        try {
            return this.userApi.authenticate(email, password);    
        } catch (Exception e) {
            throw e;
        }
    }

    public void residentRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "resident");
    }

    public void intervenantRegister(Utilisateur utilisateur) throws Exception {
        this.userApi.saveUserToFirebase(utilisateur, "intervenant");
    }

    public ArrayList<ProjectVille> getProjects() throws Exception{
        return this.projectApi.getProject();
    }

    public ArrayList<Entrave> getEntraves() throws Exception {
        return this.entravesApi.getEntraves();
    }

    public String getQuartierName(String address) throws Exception {
        return this.quartierApi.getName(address);
        
    }

}
