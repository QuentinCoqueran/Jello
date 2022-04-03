package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        // TITRE PROJET
        String selectQueryProject = "SELECT projectName FROM project_trello WHERE id_project_trello = " + projectId;
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQueryProject);
            while (queryOutput.next()) {
                projectNameText.setText(queryOutput.getString("projectName"));
                projectNameText.setFont(tempFont);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // CHARGEMENT DES FAVORIS
        ScrollPane scrollFavoris = (ScrollPane) MainApplication.getScene().lookup("#favoris");
        gridFavoris.setPrefHeight(225);
        String selectQueryFavorites = "SELECT table_trello.title, `group`.title as groupTitle, table_trello.favorites FROM table_trello \n" +
                                        "INNER JOIN `group` ON `group`.fk_id_project_trello =" + projectId + "\n"+
                                        "AND table_trello.fk_id_group = `group`.id_group\n" +
                                        "WHERE table_trello.favorites = true;";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQueryFavorites);
            int count = 0;
            while (queryOutput.next()) {
                Pane empty = new Pane();
                empty.setPrefWidth(50);

                // image favoris jaune
                InputStream stream = new FileInputStream("src/main/java/fr/pa3al2g3/esgi/jello/asset/favorisjaune.png");
                Image favorisjauneimage = new Image(stream);
                ImageView favorisjaune = new ImageView();
                favorisjaune.setImage(favorisjauneimage);
                favorisjaune.setFitHeight(45);
                favorisjaune.setFitWidth(45);
                GridPane.setMargin(favorisjaune, new Insets(20,20,0,0));
                GridPane.setHalignment(favorisjaune, HPos.RIGHT);
                GridPane.setValignment(favorisjaune, VPos.TOP);

                // button sur le favoris jaune
                Button favorisButton = new Button();
                favorisButton.setPrefHeight(45);
                favorisButton.setPrefWidth(45);
                favorisButton.setOpacity(0);
                GridPane.setMargin(favorisButton, new Insets(20,20,0,0));
                GridPane.setHalignment(favorisButton, HPos.RIGHT);
                GridPane.setValignment(favorisButton, VPos.TOP);


                // carte Table trello
                Button tabButton = new Button();
                tabButton.setPrefWidth(300);
                tabButton.setPrefHeight(225);
                tabButton.setStyle("-fx-background-color: #ff9161; -fx-background-radius: 25");
                tabButton.setText(queryOutput.getString("title"));
                tabButton.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 24));
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
                // ajout dans le scroll pane
                gridFavoris.add(empty, count, 0);
                count +=1;
                gridFavoris.add(tabButton, count, 0);
                gridFavoris.add(favorisjaune, count, 0);
                gridFavoris.add(favorisButton, count , 0);
                count += 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // CHARGEMENT DES GROUPES // chargement des scrollpanes
        String selectQueryGroupes = "SELECT `group`.title as groupTitle FROM `group` \n" +
                                    "WHERE `group`.fk_id_project_trello = 1;";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQueryGroupes);
            while (queryOutput.next()) {
                String selectQuerytables = "SELECT `group`.title as groupTitle FROM `group` \n" +
                        "WHERE `group`.fk_id_project_trello = 1;";
                try {
                    Statement statement2 = conn.createStatement();
                    ResultSet queryOutput2 = statement.executeQuery(selectQuerytables);
                    int count2 = 0;
                    while (queryOutput.next()) {
                    }
                }catch (SQLException throwables){

                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        scrollFavoris.setPrefHeight(225);
        scrollFavoris.setContent(gridFavoris);
    }
}
