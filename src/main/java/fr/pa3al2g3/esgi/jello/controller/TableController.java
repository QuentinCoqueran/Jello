package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.MainApplication;
import fr.pa3al2g3.esgi.jello.model.TableModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class TableController {

    @FXML
    Button add_list;

    @FXML
    public void createList(ActionEvent event) throws IOException {
        MainApplication.getModelManager().getTableModel().addList();
    }

    public void onClickReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("project-view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainApplication.setScene(new Scene(root));
        MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
        MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
        MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
        MainApplication.getStage().setMaximized(true);
        MainApplication.getModelManager().getProjectModel().init(TableModel.projectId);
    }
}
