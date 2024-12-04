package prototype.controllers;

import prototype.api.firebase.UserFireBase;
import prototype.api.ville.EntravesApiLoader;
import prototype.api.ville.ProjectApiLoader;
import prototype.users.*;
import prototype.projects.*;

import java.util.ArrayList;

import prototype.entraves.Entrave;

public class ApiController {

    private EntravesApiLoader entravesApi;

    private ProjectApiLoader projectApi;

    private UserFireBase userApi;

    public ApiController() {
        this.entravesApi = new EntravesApiLoader();
        this.projectApi = new ProjectApiLoader();
        this.userApi = new UserFireBase();
    }

    public Utilisateur authenticate(String email, String password) throws Exception {
        try {
            return this.userApi.authenticate(email, password);    
        } catch (Exception e) {
            throw e;
        }
    }

    public void residentRegister(Utilisateur utilisateur) throws Exception{
        try {
            this.userApi.saveUserToFirebase(utilisateur);
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<ProjectVille> getProjects() throws Exception{
        try {
            return this.projectApi.getProject();
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Entrave> getEntraves() throws Exception {
        try {
            return this.entravesApi.getEntraves();
        } catch (Exception e) {
            throw e;
        }
    }

}
