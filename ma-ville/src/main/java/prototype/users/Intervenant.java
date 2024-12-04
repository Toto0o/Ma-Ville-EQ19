package prototype.users;

public class Intervenant extends Utilisateur {

    private String cityID;

    public Intervenant() {}

    public Intervenant(String name, String lastname, String password, String birthday, String phone, String email, Address address, String cityID) {
        super(name, lastname, password, birthday, phone, email, address);
        this.cityID = cityID;
    }

    public String getCityID() {return this.cityID;}

    public void setCityID(String cityID) {this.cityID = cityID;}

}
