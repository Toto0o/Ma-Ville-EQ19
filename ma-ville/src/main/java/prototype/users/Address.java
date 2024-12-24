package prototype.users;

import java.io.Serializable;

/**
 * Objet d'adresse pour les utilisateurs
 */
public class Address implements Serializable {

    private String number, street, postalCode, borough;

    /**
     * Constructeur vide pour la serialisation avec {@link com.google.firebase.database.FirebaseDatabase FirebaseDatabase}
     */
    public Address() {}

    /**
     * Constructeur
     * @param number le numéro de la résidence
     * @param street le nom de la rue
     * @param postalCode le code postal
     */
    public Address(String number, String street, String postalCode) {
        this.number = number;
        this.street = street;
        this.postalCode = postalCode;
    }


    public String getNumber() {return this.number;}

    public String getStreet() {return this.street;}

    public String getPostalCode() {return this.postalCode;}

    public String getBorough() {
        return this.borough.replaceAll(" - ", "-");
    }

    public void setNumber(String number) {this.number = number;}

    public void setStreet(String street) {this.street = street;}

    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}

    public void setBorough(String borough) {this.borough = borough;}

}
