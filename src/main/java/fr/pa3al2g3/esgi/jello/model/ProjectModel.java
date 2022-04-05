package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProjectModel {

    private GridPane gridFavoris;
    private String projetName;

    public ProjectModel() {
        this.gridFavoris = new GridPane();
    }

    public void init(int projectId){

        Text projectNameText = (Text) MainApplication.getScene().lookup("#project_name");
        Font tempFont = projectNameText.getFont();


        ConnectionDb connectNow = new ConnectionDb();
        Connection conn = connectNow.connect();
        String selectQuery = "SELECT projectName FROM project_trello WHERE id_project_trello = " + projectId;
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQuery);
            while (queryOutput.next()) {
                projectNameText.setText(queryOutput.getString("projectName"));
                projectNameText.setFont(tempFont);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ScrollPane scrollFavoris = (ScrollPane) MainApplication.getScene().lookup("#favoris");
        gridFavoris.setPrefHeight(225);
        gridFavoris.setPrefWidth(340);
        gridFavoris.setStyle("-fx-background-color: grey");
        Pane empty = new Pane();
        empty.setPrefWidth(20);
        Pane empty2 = new Pane();
        empty2.setPrefWidth(20);
        gridFavoris.add(empty, 0, 0);
        Button tabButton = new Button();
        tabButton.setPrefWidth(300);
        tabButton.setPrefHeight(225);
        tabButton.setStyle("-fx-background-color: #ff9161");
        tabButton.setText("Titre tableau");
        tabButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("table.fxml"));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                    MainApplication.setScene(new Scene(root));
                    MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
                    MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
                    MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
                    MainApplication.getStage().setMaximized(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MainApplication.getModelManager().getTableModel().init();
            }
        });
        gridFavoris.add(tabButton, 1, 0);
        gridFavoris.add(empty2, 2, 0);
        scrollFavoris.setContent(gridFavoris);
    }
}
