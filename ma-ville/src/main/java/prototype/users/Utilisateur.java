package prototype.users;
import java.io.Serializable;
import java.util.List;

import com.google.firebase.database.PropertyName;

/**
 * Utilisateur général
 */
public abstract class Utilisateur implements Serializable {

    protected String name, lastname, password, birthday, phone, email;
    protected String id;
    protected Address address;;
    protected Horaire horaire;
    protected boolean intervenant;

    /**
     * Constructeur vide pour la désérialisation
     */
    public Utilisateur() {
    }

    /**
     * Constreucteur à l'instanciation
     * @param name
     * @param lastname
     * @param password
     * @param birthday
     * @param phone
     * @param email
     * @param address
     */
    public Utilisateur(String name, String lastname, String password, String birthday, String phone, String email, Address address) {
            this.name = name;
            this.lastname = lastname;
            this.password = password;
            this.birthday = birthday;
            this.phone = phone;
            this.address = address;
            this.email = email;
            this.horaire = new Horaire();
    }

    public String getName() {return this.name;}

    public String getLastname() {return this.lastname;}

    public String getPassword() {return this.password;}

    public String getBirthday() {return this.birthday;}

    public String getPhone() {return this.phone;}

    public Address getAddress() {return this.address;}

    public String getEmail() {return this.email;}

    public void setName(String name) {this.name = name;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public void setPasword(String password) {this.password = password;}

    public void setBirthday(String birthday) {this.birthday = birthday;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setAdress(Address address) {this.address = address;}

    public void setEmail(String email) {this.email = email;}

    @PropertyName("intervenant")
    public void setIntervenant(boolean intervenant) {this.intervenant = intervenant;}

    public Horaire getHoraire() {return this.horaire;}

    @PropertyName("horaire")
    public void setHoraire(List<List<Boolean>> horaire) {
        Horaire rawHoraire = new Horaire();
        rawHoraire.setSchedule(horaire);
        this.horaire = rawHoraire;
    }

    public String getId() {return this.id;}

    public void setId(String id) {this.id = id;}

    @PropertyName("intervenant")
    public abstract boolean isIntervenant();

    /**
     * Méthode utile pour faciliter les setters; À utiliser dans une forloop
     * @param id le champ a set
     * @param value la valeur a set
     */
    public void set(String id, String value) {
        switch (id) {
            case "name" -> setName(value);
            case "lastname" -> setLastname(value);
            case "password" -> setPasword(value);
            case "birthday" -> setBirthday(value);
            case "phone" -> setPhone(value);
            case "email" -> setEmail(value);
            case "address number" -> {
                this.address.setNumber(value);
            }
            case "address street" -> {
                this.address.setStreet(value);
            }
            case "postal code" -> {
                this.address.setPostalCode(value);
            }
        }
    }
}
