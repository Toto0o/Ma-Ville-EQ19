package prototype.users;

/**
 * {@link Utilisateur} de type résident
 */
public class Resident extends Utilisateur {

    /**
     * Constructeur vide pour la désérialisation
     */
    public Resident() {}

    /**
     * Constructeur à l'instanciation
     * @param name le prénom
     * @param lastname le nom de famille
     * @param password le mot de passe
     * @param birthday la date de naissance
     * @param address l'adresse de résidence
     * @param phone le numéro de téléphonne
     * @param email l'adresse courriel
     */
    public Resident(String name, String lastname, String password, String birthday, Address address, String phone, String email) {
        super(name, lastname, password, birthday, phone, email, address);
    }

    @Override
    public boolean isIntervenant() {return false;}

}
