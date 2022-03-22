package fr.pa3al2g3.esgi.jello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableModel {
    private int numberList = 0;
    private GridPane grid_new = new GridPane();
    private ScrollPane scrollPane = (ScrollPane) MainApplication.getScene().lookup("#main_scrollbar");
    private int widthGrid = 0;
    @FXML
    private TextField titleField;

    @FXML
    private Button buttonAddList;

    @FXML
    private Button add_list;

    public TableModel() {
        titleField = new TextField();
        buttonAddList = new Button();
        init();

    }

    private void init() {
        grid_new.setPrefWidth(800);
        grid_new.setPrefHeight(800);
        grid_new.setStyle("-fx-background-color: orange");
        selectList();
    }

    @FXML
    public void selectList() {
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();

        String connectQuery = "select title from list_trello where fk_id_table = 1;";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                Text text = new Text("toto");
                text.prefWidth(100);
                buttonAddList = new Button();
                buttonAddList.setText(queryOutput.getString(1));
                buttonAddList.setPrefWidth(100);
                buttonAddList.setPrefHeight(50);
                grid_new.add(buttonAddList,numberList, 0);
                numberList += 1;
                grid_new.add(text,numberList,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        widthGrid = 200 * numberList;
        //grid_new.setPrefWidth(widthGrid);
        scrollPane.setContent(grid_new);
    }

    @FXML
    public void addList() {
        titleField = new TextField();
        numberList += 1;
        titleField.setPromptText("Votre titre...");
        titleField.setId("title_list");
        titleField.setPrefWidth(100);
        titleField.setPrefHeight(50);
        buttonAddList.setText("Creer votre liste");
        buttonAddList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConnectionDb connectNow = new ConnectionDb();
                Connection connectionDB = connectNow.connect();
                String connectQuery = "INSERT INTO list_trello (title) VALUES ('" + titleField.getText() + "');";
                try {
                    Statement statement = connectionDB.createStatement();
                    statement.executeUpdate(connectQuery);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        grid_new.add(titleField, numberList, 0);
        grid_new.add(buttonAddList, numberList, 1);
    }
}
