package prototype.controllers;

import java.util.ArrayList;

import prototype.projects.Project;
import prototype.controllers.ApiController;

public class ProjectController {

    private ArrayList<Project> projects;
    private ApiController apiController;

    public ProjectController(ApiController apiController) {
        this.projects = new ArrayList<>();
        this.apiController = apiController;
    }

    public ArrayList<Project> getProjects() throws Exception{
        loadProject();
        return this.projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void loadProject() throws Exception{
        this.projects = this.apiController.getProjects();
    }
}
