package prototype.Users;

public class Resident extends Utilisateur {

    private String quartier;

    public Resident(String name, String lastName, String birthday, String address, String email, String phone,
            String password, String quartier) {
        super(name, lastName, birthday, address, email, phone, password);
        this.quartier = quartier; // Ensure that the quartier is set correctly
    }

    public String getQuartier() {
        return this.quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }
}
