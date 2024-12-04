package prototype.interfaces;

public interface CredentialsVerifier {

    public Boolean verifyCredentials(String username, String password) throws IllegalArgumentException; /* Search database for Username and Password -> return true if match, return false otherwise */

    Boolean verifiyMatchingPasswords(String password1, String password2) throws IllegalArgumentException; /* Verify if password match for the 2 fields */

    Boolean verifyBirthday(String birthday) throws IllegalArgumentException; /* Verify if date of birth entered is in the good format */

    Boolean verifyAdress(String adress) throws IllegalArgumentException; /* Verify if adress is valid (API MTL city) */
    
    Boolean verifyIdentifier(String identifier) throws IllegalArgumentException; /* Verify if "Identifiant de la ville de l'intevenant" is valid */

}
