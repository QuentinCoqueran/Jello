package fr.pa3al2g3.esgi.jello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button initButton;

    @FXML
    public void onInitButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainApplication.setScene(new Scene(root));
        TableModel home = new TableModel();
        TableController.setTableModel(home);
    }

}