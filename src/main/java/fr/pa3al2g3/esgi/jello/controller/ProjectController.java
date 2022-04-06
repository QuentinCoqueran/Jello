package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.MainApplication;
import fr.pa3al2g3.esgi.jello.model.HomeModel;
import fr.pa3al2g3.esgi.jello.model.ProjectModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ProjectController {




    @FXML
    public void onClickReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("home-view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainApplication.setScene(new Scene(root));
        MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
        MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
        MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
        MainApplication.getStage().setMaximized(true);
        MainApplication.getModelManager().getHomeModel().init();
    }
}
