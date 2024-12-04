package prototype.controllers;

import prototype.users.UserCredentialsVerifier;
import prototype.users.Utilisateur;
import prototype.users.Address;
import prototype.users.Intervenant;
import prototype.users.Resident;


public class UserController {

    private UserCredentialsVerifier verifier;
    private Utilisateur utilisateur;
    private ApiController apiController;

    public UserController() {
        this.verifier = new UserCredentialsVerifier();
    }

    public Utilisateur getUser() {return this.utilisateur;}
    
    public void setUser(Utilisateur utilisateur) {this.utilisateur = utilisateur;}

    public void register(String name, String lastname, String password1, String password2, String birthday, Address address, String phone, String email) throws Exception {
        // Resident register
        try {
            this.verifier.verifyResidentRegister(password1, password2, birthday, email, address);
            Resident resident = new Resident(
            name, lastname, password1, birthday, address, phone, email);
            this.apiController.residentRegister(resident);
        } catch (Exception e) {
            throw e;
        }
    }

    public void register(String name, String lastname, String password1, String password2, String cityId, String type) throws Exception{
        // Intervenant register
        try {
            this.verifier.verifyIntervenantRegister(password1, password2, cityId);
            Intervenant intervenant = new Intervenant(name, lastname, password1, cityId, type);
            this.apiController.intervenantRegister(intervenant);
        } catch (Exception e) {
            throw e;
        }
    }

    public Utilisateur login(String email, String password) throws Exception {
        return this.apiController.authenticate(email, password);
    }
}
