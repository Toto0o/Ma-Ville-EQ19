package prototype.controllers;

import java.util.ArrayList;

import prototype.projects.Project;

public class ProjectController {

    private ArrayList<Project> projects;

    public ProjectController() {
        this.projects = new ArrayList<>();
        loadProject();
    }

    public ArrayList<Project> getProject() {
        return this.projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    private void loadProject() {};

}
