package prototype.users;

public class Intervenant extends Utilisateur {

    private String cityID;

    public Intervenant(String username, String password, String birthday, String phone, Address address, String cityID) {
        super(username, password, birthday, phone, address);
        this.cityID = cityID;
    }

    public String getCityID() {return this.cityID;}

    public void setCityID(String cityID) {this.cityID = cityID;}

}
