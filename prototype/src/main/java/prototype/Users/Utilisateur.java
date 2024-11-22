package prototype.Users;

import javafx.util.Pair;

public class Utilisateur {

    private String userName;
    private String name;
    private String lastName;
    private String birthday;
    private String address;
    private String email;
    private String phone;
    private String password;
    private Boolean notificationOn;
    private Pair<String, String> hoursPreference;
    private Boolean[] notificationPreference;
    private String[] savedProject;
    private String userId;

    public Utilisateur(String name, String lastName, String birthday, String address, String email, String phone,
            String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}