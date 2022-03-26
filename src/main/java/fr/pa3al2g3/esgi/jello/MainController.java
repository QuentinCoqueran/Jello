package fr.pa3al2g3.esgi.jello;

import fr.pa3al2g3.esgi.jello.manager.ModelManager;
import fr.pa3al2g3.esgi.jello.model.HomeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {
    @FXML
    private Button initButton;

    @FXML
    public void onInitButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainApplication.setScene(new Scene(root));
        MainApplication.getModelManager().getHomeModel().init();
    }
}