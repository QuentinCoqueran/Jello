package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.MainApplication;
import fr.pa3al2g3.esgi.jello.model.TableModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TableController {

    @FXML
    Button add_list;

    @FXML
    public void createList(ActionEvent event) throws IOException {

        MainApplication.getModelManager().getTableModel().addList();
    }
}
