package prototype.users;
import java.io.Serializable;
import java.util.ArrayList;

import prototype.notifications.Notification;
import prototype.projects.Project;

public abstract class Utilisateur implements Serializable {

    private String username, password, birthday, phone;
    private Address address;

    private ArrayList<Project> savedProjects;

    private ArrayList<Notification> notifications;

    public Utilisateur(String username, String password, String birthday, String phone, Address address) {
            this.username = username;
            this.password = password;
            this.birthday = birthday;
            this.phone = phone;
            this.address = address;
            this.savedProjects = new ArrayList<>();
            this.notifications = new ArrayList<>();
    }

    public String getUsername() {return this.username;}
    
    public String getPassword() {return this.password;}

    public String getBirthday() {return this.birthday;}

    public String getPhone() {return this.phone;}

    public Address getAddress() {return this.address;}

    public ArrayList<Project> getSavedProjects() {return this.savedProjects;}

    public void setUsername(String username) {this.username = username;}

    public void setPasword(String password) {this.password = password;}

    public void setBirthday(String birthday) {this.birthday = birthday;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setAdress(Address address) {this.address = address;}

    public void saveProject(Project project) {this.savedProjects.add(project);}
    

}
