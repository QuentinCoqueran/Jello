package fr.pa3al2g3.esgi.jello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TableController {
    private static TableModel tableModel;
    @FXML
    Button add_list;

    @FXML
    public void createList(ActionEvent event) throws IOException {
        tableModel.addList();
    }

    public static TableModel getTableModel() {
        return tableModel;
    }

    public static void setTableModel(TableModel tableModel) {
        TableController.tableModel = tableModel;
    }
}
