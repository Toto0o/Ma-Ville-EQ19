package prototype.Users;

public class Intervenant extends Utilisateur {

    private String cityID;

    public Intervenant(String name, String lastName, String password, String birthday, String phone, String address,
            String email, String cityID, String accountType) {
        super(name, lastName, birthday, address, email, phone, password);
        this.cityID = cityID;

    }

    public String getCityID() {
        return this.cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

}
