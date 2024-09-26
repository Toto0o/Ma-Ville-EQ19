package Interfaces;

import javax.swing.text.StyledEditorKit.BoldAction;

public interface CredentialsVerifier {

    public Boolean verifyCrednetials(String username, String password); /* Search database for Username and Password -> return true if match, return false otherwise */

    public Boolean verifiyMatchingPasswords(String password1, String password2); /* Verify if password match for the 2 fields */

    public Boolean verifyAdress(String adress); /* Verify if adress is valid (API MTL city) */
    
    public Boolean verifyIdentifier(String identifier); /* Verify if "Identifiant de la ville de l'intevenant" is valid */

}
