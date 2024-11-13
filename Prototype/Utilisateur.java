package Prototype;

import javafx.util.Pair;

public class Utilisateur {

    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String birthday;

    public Utilisateur(String userName, String password, String email, String phone, String address, String birthday) {
        this.address = address;
        this.birthday = birthday;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }
    public String getEmail() {return this.email;}
    public String getAddress() {
        return address;
    }
    public String getPhone() {return this.phone;}
    public String getUserName() {
        return this.userName;
    }

    public String getBirthday() {
        return birthday;
    }
}