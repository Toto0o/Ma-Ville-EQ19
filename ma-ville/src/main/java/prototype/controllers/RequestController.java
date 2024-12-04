package prototype.controllers;

import java.util.ArrayList;

import prototype.interfaces.UserController;
import prototype.projects.Project;
import prototype.projects.Request;
import prototype.projects.Type;
import prototype.users.Intervenant;

public class RequestController {

    private ArrayList<Request> requests;
    
    private ProjectController projectController;
    private UserController userController;

    /* private FireBaseLoader data; */

    public RequestController(UserController userController/* , FireBaseLoader data */, ProjectController projectController) {
        this.userController = userController;
        this.projectController = projectController;
        /* this.data = data; */
        this.requests = new ArrayList<>();
    }

    public void addRequest(String title, String description, Type type, String date) {
        Request request = new Request(title, description, date, type, this.userController.getUsername(), this.userController.getAddress().getStreet(), this);
        this.requests.add(request);
    }

    public ArrayList<Request> getRequests() {
        return this.requests;
    }

    public void loadRequest() {
        /* this.requests = this.data.getRequests(); */
    }

    public void addProject(String title, Type type, String description, String quartier, String startDate, String endDate, int number) {
        Project project = new Project(title, type, description, quartier, startDate, endDate, (Intervenant) this.userController.getUser(), number);
        this.projectController.addProject(project);
    }


}
