package prototype.interfaces;

import prototype.users.Address;
import prototype.users.Utilisateur;

public interface UserController {

    String getUsername();

    String getPassword();

    String getBirthday();

    String getPhone();

    String getCityID();

    Address getAddress();

    void setUsername(String username) throws IllegalArgumentException;

    void setPassword(String password) throws IllegalArgumentException;

    void setBirthday(String birthday) throws IllegalArgumentException;

    void setAddress(Address address) throws IllegalArgumentException;

    void setPhone(String phone) throws IllegalArgumentException;

    void setCityID(String id) throws IllegalArgumentException;

    void newUser(Boolean resident);

    String[] getSavedProjects();

    void saveProject(String projectid);

    Utilisateur getUser();

    
    boolean verifyLogin(String username, String password);

    boolean validateRegistrationInfo(String birthday, String email, String password1, String password2, String address);

    boolean validateCityID(String cityID);

    boolean validatePasswordMatch(String password1, String password2);

    boolean validateBirthday(String birthday);

    boolean validateAddress(String address);

    boolean validateIdentifier(String identifier);
}

