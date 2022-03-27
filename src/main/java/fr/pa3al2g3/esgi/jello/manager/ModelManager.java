package fr.pa3al2g3.esgi.jello.manager;

import fr.pa3al2g3.esgi.jello.model.HomeModel;
import fr.pa3al2g3.esgi.jello.model.ProjectModel;
import fr.pa3al2g3.esgi.jello.model.TableModel;

public class ModelManager {

    private HomeModel homeModel;
    private ProjectModel projectModel;
    private TableModel tableModel;

    public ModelManager() {
        this.homeModel = new HomeModel();
        this.projectModel = new ProjectModel();
        this.tableModel = new TableModel();
    }

    public HomeModel getHomeModel() {
        return homeModel;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}
