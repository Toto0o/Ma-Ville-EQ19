package prototype.users;

public class Intervenant extends Utilisateur {

    private String cityID;
    private IntervenantType type;

    public Intervenant() {}

    public Intervenant(String name, String lastname, String password, String birthday, String phone, String email, Address address, String cityID) {
        super(name, lastname, password, birthday, phone, email, address);
        /*this.type = type;*/
        this.cityID = cityID;
    }

    public String getCityID() {return this.cityID;}

    public void setCityID(String cityID) {this.cityID = cityID;}

    public String getType() {return this.type.name();}

    public void setType(IntervenantType type) {this.type = type;}

    @Override
    public boolean isIntervenant() {return true;}

}
