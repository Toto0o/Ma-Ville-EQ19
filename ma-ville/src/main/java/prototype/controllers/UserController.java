package prototype.controllers;

import prototype.users.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Controlleur de l'utilisateur. Permet d'avoir l'instance de l'utilisateur et d'assurer qu'il soit unique durant une session.
 * 
 * <p> Utilise {@link prototype.users.UserCredentialsVerifier UserCredentialsVerifier} pour valider les informations lors de l'authentification ou de l'enregistrement </p>
 * 
 * @param apiController
 * 
 * @author Antoine Tessier
 * @author Anmar Rahman
 * @author Mostafa Heider
 */

public class UserController {

    private UserCredentialsVerifier verifier;
    private Utilisateur utilisateur;
    private ApiController apiController;
    private DateTimeFormatter formatter;

    public UserController(ApiController apiController) {
        this.verifier = new UserCredentialsVerifier();
        this.apiController = apiController;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public Utilisateur getUser() {return this.utilisateur;}
    
    public void setUser(Utilisateur utilisateur) {this.utilisateur = utilisateur;}

    public void register(String name, String lastname, String password1, String password2, LocalDate birthday, Address address, String phone, String email) throws Exception {
        // Resident register
        String birthdayString;

        if (birthday != null) {
            birthdayString = birthday.format(this.formatter);
        }
        else {
            throw new IllegalArgumentException("Birhtday cannot be empty");
        }
        this.verifier.verifyResidentRegister(password1, password2, birthdayString, email, address);
        Resident resident = new Resident(
        name, lastname, password1, birthdayString, address, phone, email);
        this.apiController.residentRegister(resident);
        this.utilisateur = resident;
        
    }

    public void register(String name, String lastname, String password1, String password2, LocalDate birthday, Address address, String phone, String email, String cityId, IntervenantType type) throws Exception{
        // Intervenant register
        String birthdayString;
        if (birthday != null) {
            birthdayString = birthday.format(this.formatter);
        }
        else {
            throw new IllegalArgumentException("Birhtday cannot be empty");
        }
        this.verifier.verifyIntervenantRegister(password1, password2, cityId);
        Intervenant intervenant = new Intervenant(name, lastname, password1, birthdayString, phone, email, address, cityId, type);
        this.apiController.intervenantRegister(intervenant);
        this.utilisateur = intervenant;
    }

    public Utilisateur login(String email, String password) throws Exception {
        return this.apiController.authenticate(email, password);
    }

    public void updateInfo(HashMap<String,String> changes) {
        this.apiController.updateUserInfo(this.utilisateur, changes);
    }
}
