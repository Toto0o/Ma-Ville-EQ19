package prototype.users;

/**
 * {@link Utilisateur} de type intervenant
 */
public class Intervenant extends Utilisateur {

    private String cityID;

    /**
     * Constructeur vide pour la déserialisation
     */
    public Intervenant() {}

    /**
     * Constructeur a l'instanciation
     * @param name le prénom
     * @param lastname le nom de famille
     * @param password le mot de passe
     * @param birthday la date de naissance
     * @param phone le numéro de téléphonne
     * @param email l'adresse email
     * @param address l'adresse de résidence
     * @param cityID l'identidiant de la ville
     */
    public Intervenant(String name, String lastname, String password, String birthday, String phone, String email, Address address, String cityID) {
        super(name, lastname, password, birthday, phone, email, address);
        this.cityID = cityID;
    }

    public String getCityID() {return this.cityID;}

    public void setCityID(String cityID) {this.cityID = cityID;}

    @Override
    public boolean isIntervenant() {return true;}

}
