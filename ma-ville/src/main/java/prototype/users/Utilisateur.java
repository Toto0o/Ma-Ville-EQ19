package prototype.users;
import java.io.Serializable;
import java.util.ArrayList;

import prototype.notifications.Notification;
import prototype.projects.Project;

public abstract class Utilisateur implements Serializable {

    private String name, lastname, password, birthday, phone, email;
    private Address address;

    private ArrayList<Project> savedProjects;

    private ArrayList<Notification> notifications;

    public Utilisateur() {}

    public Utilisateur(String name, String lastname, String password, String birthday, String phone, String email, Address address) {
            this.name = name;
            this.lastname = lastname;
            this.password = password;
            this.birthday = birthday;
            this.phone = phone;
            this.address = address;
            this.email = email;
            this.savedProjects = new ArrayList<>();
            this.notifications = new ArrayList<>();
    }

    public String getName() {return this.name;}

    public String getLastname() {return this.lastname;}
    
    public String getPassword() {return this.password;}

    public String getBirthday() {return this.birthday;}

    public String getPhone() {return this.phone;}

    public Address getAddress() {return this.address;}

    public String getEmail() {return this.email;}

    public ArrayList<Project> getSavedProjects() {return this.savedProjects;}

    public void setName(String name) {this.name = name;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public void setPasword(String password) {this.password = password;}

    public void setBirthday(String birthday) {this.birthday = birthday;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setAdress(Address address) {this.address = address;}

    public void setEmail(String email) {this.email = email;}

    public void saveProject(Project project) {this.savedProjects.add(project);}
    

}
