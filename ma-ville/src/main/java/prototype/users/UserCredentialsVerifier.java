package prototype.users;

import java.time.LocalDate;
import java.time.Period;


public class UserCredentialsVerifier {

    
    public void verifyResidentRegister(String password1, String password2, String birthday, String email, Address address) throws IllegalArgumentException {
        // Implement your logic to verify credentials, e.g., check against a database
            this.verifiyMatchingPasswords(password1, password2);
            this.verifyBirthday(birthday);
            this.verifyEmail(email);
            this.verifyAdress(address);
    }

    public void verifyIntervenantRegister(String password1, String password2, String cityId) throws Exception {
        this.verifiyMatchingPasswords(password1, password2);
        this.verifyIdentifier(cityId); 
    }

    
    public void verifiyMatchingPasswords(String password1, String password2) throws IllegalArgumentException {
        if (!password1.equals(password2)) {
            throw new IllegalArgumentException("Passwords doesn't match");
        }
    }

    
    public void verifyBirthday(String birthday) throws IllegalArgumentException {
        // Implement birthday format validation (e.g., YYYY-MM-DD)
        LocalDate today = LocalDate.now();
        LocalDate ldbirthday = LocalDate.parse(birthday);

        if (birthday.equals(null)) throw new IllegalArgumentException("Birhtday cannot be empty");
        
        if (Period.between(ldbirthday, today).getYears() < 16) {
            throw new IllegalArgumentException("Must be at least 16 years old to register");
        }
    }

    
    public void verifyAdress(Address adress) throws IllegalArgumentException {
        // Implement address verification logic, e.g., using a web API or regex
    }

    
    public void verifyIdentifier(String identifier) throws IllegalArgumentException{
        // Implement identifier verification
    }

    private void verifyEmail(String email) throws IllegalArgumentException {
        //Verify if email is already in use!
    }


}
