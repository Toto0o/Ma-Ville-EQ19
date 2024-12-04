package prototype.users;

public class Resident extends Utilisateur {

    public Resident() {}

    public Resident(String name, String lastname, String password, String birthday, Address address, String phone, String email) {
        super(name, lastname, password, birthday, phone, email, address);
    }

}
