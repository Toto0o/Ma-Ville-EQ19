package prototype.service;

import prototype.Interfaces.CredentialsVerifier;

public class UserCredentialsVerifier implements CredentialsVerifier {

    @Override
    public Boolean verifyCredentials(String username, String password) {
        // Implement your logic to verify credentials, e.g., check against a database
        return true; // Dummy return for example
    }

    @Override
    public Boolean verifiyMatchingPasswords(String password1, String password2) {
        return password1.equals(password2);
    }

    @Override
    public Boolean verifyBirthdayFormat(String birthday) {
        // Implement birthday format validation (e.g., YYYY-MM-DD)
        return birthday.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Override
    public Boolean verifyAdress(String adress) {
        // Implement address verification logic, e.g., using a web API or regex
        return true; // Dummy return for example
    }

    @Override
    public Boolean verifyIdentifier(String identifier) {
        // Implement identifier verification
        return true; // Dummy return for example
    }

}
