package prototype.users;

import prototype.controllers.ApiController;

import java.time.LocalDate;
import java.time.Period;

/**
 * Vérificateur des informations à l'enregistrement d'un nouvel utilisateur
 */
public class UserCredentialsVerifier {

    private ApiController apiController;

    /**
     * Constructeur
     * @param apiController pour vérifier l'adresse
     */
    public UserCredentialsVerifier(ApiController apiController) {
        this.apiController = apiController;
    }

    /**
     * Vérifie les informations d'un enregistrement résident
     * @param password1 mot de passe
     * @param password2 mot de passe répété
     * @param birthday la date de naissance
     * @param email le courriel
     * @param address l'adresse de résidence
     * @throws IllegalArgumentException lorsque des informations sont invalides
     */
    public void verifyResidentRegister(String password1, String password2, LocalDate birthday, String email, Address address) throws IllegalArgumentException {
        // Implement your logic to verify credentials, e.g., check against a database
            this.verifiyMatchingPasswords(password1, password2);
            this.verifyBirthday(birthday);
            this.verifyEmail(email);
            this.verifyAdress(address);
    }

    /**
     * Vérifie les informations sur l'enregistrement d'un intervenant
     * @param password1 le mot de passe
     * @param password2 le mot de passe répété
     * @param cityId l'identifiant de la ville
     * @throws Exception lorsque des informations sont invalides
     */
    public void verifyIntervenantRegister(String password1, String password2, String cityId) throws Exception {
        this.verifiyMatchingPasswords(password1, password2);
        this.verifyIdentifier(cityId); 
    }

    /**
     * Vérifie que le mot de passe et le mot de passe répété sont les même
     * @param password1 le mot de passe
     * @param password2 le mot de passe répété
     * @throws IllegalArgumentException sur différence dans les mots de passes
     */
    public void verifiyMatchingPasswords(String password1, String password2) throws IllegalArgumentException {
        if (!password1.equals(password2)) {
            throw new IllegalArgumentException("Passwords doesn't match");
        }
    }

    /**
     * Vérifie que l'utilisateur a plus de 16 ans
     * @param birthday la date de naissance
     * @throws IllegalArgumentException
     */
    public void verifyBirthday(LocalDate birthday) throws IllegalArgumentException {
        // Implement birthday format validation (e.g., YYYY-MM-DD)
        LocalDate today = LocalDate.now();

        if (birthday.equals(null)) throw new IllegalArgumentException("Birhtday cannot be empty");
        
        if (Period.between(birthday, today).getYears() < 16) {
            throw new IllegalArgumentException("Must be at least 16 years old to register");
        }
    }

    /**
     * Vérifie que l'adresse entré est dans la ville de Montréal
     *
     * <p>Utilise {@link ApiController#getCity(String)}</p>
     * @param address l'adresse de résidence
     * @throws IllegalArgumentException
     */
    public void verifyAdress(Address address) throws IllegalArgumentException {
        try {
        if (!this.apiController.getCity(address.getPostalCode()).equals("Montreal")) {
            throw new IllegalArgumentException("Address is not in Montreal");
        }} catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Address is not valid");
        }
    }

    /**
     * Vérifie que l'identifiant de la ville est valide
     * @param identifier identifiant
     * @throws IllegalArgumentException
     */
    public void verifyIdentifier(String identifier) throws IllegalArgumentException{
        // Implement identifier verification
    }

    /**
     * Vérifie que l'email est unique;
     *
     * <p>Firebase inclut une vérification avec son toolkit</p>
     * @param email l'adresse courriel de l'utilisateur
     * @throws IllegalArgumentException
     */
    private void verifyEmail(String email) throws IllegalArgumentException {
        //Verify if email is already in use!
    }


}
