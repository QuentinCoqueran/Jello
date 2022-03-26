package fr.pa3al2g3.esgi.jello.manager;

import fr.pa3al2g3.esgi.jello.model.HomeModel;
import fr.pa3al2g3.esgi.jello.model.ProjectModel;

public class ModelManager {

    private HomeModel homeModel;
    private ProjectModel projectModel;

    public ModelManager(){
        this.homeModel = new HomeModel();
        this.projectModel = new ProjectModel();
    }

    public HomeModel getHomeModel() {
        return homeModel;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }
}
