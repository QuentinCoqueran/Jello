package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private GridPane gridGroups;
    public static int projectId;


    public ProjectModel() {
        this.gridFavoris = new GridPane();
    }

    public void init(int projectId) {
        setProjectName(projectId);

        setFavorite(projectId);
        setGroups(projectId);
    }

    public void setGroups(int projectId) {
        // CHARGEMENT DES GROUPES // chargement des scrollpanes
        ProjectModel.projectId = projectId;
        ConnectionDb connectNow = new ConnectionDb();
        Connection conn = connectNow.connect();
        ScrollPane scrollGroupV = (ScrollPane) MainApplication.getScene().lookup("#groups");
        GridPane gridAllGroups = new GridPane();
        String selectQueryGroupes = "SELECT DISTINCT `group`.title, id_group FROM `group` \n" +
                "WHERE `group`.fk_id_project_trello =" + projectId + ";";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQueryGroupes);
            int countGroup = 0;
            while (queryOutput.next()) {
                GridPane gridButton = new GridPane();
                this.gridGroups = new GridPane();
                Text groupTitle = new Text(queryOutput.getString("title"));
                int groupid = queryOutput.getInt("id_group");
                groupTitle.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 24));
                gridGroups.setPrefHeight(225);
                ScrollPane scrollGroupH = new ScrollPane();

                GridPane gridText = new GridPane();
                gridText.add(groupTitle, 0, 0);
                //ajouter membre dans gridText

                gridButton.add(gridText, 0, 0);

                setTables(groupid, gridButton, projectId);


                //scrollGroupH.setPrefHeight(225);
                scrollGroupH.setContent(gridButton);
                scrollGroupH.setStyle("-fx-background-color: yellow");
                gridAllGroups.add(scrollGroupH, 0, countGroup); // test

                countGroup += 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        scrollGroupV.setContent(gridAllGroups);
    }

    private void setTables(int groupid, GridPane gridButton, int projectId) {

        ConnectionDb connectNow = new ConnectionDb();
        Connection conn = connectNow.connect();
        String selectQuerytables = "SELECT id_table, table_trello.title, favorites FROM table_trello \n" +
                "WHERE table_trello.fk_id_group =" + groupid + ";";
        try {
            Statement statement2 = conn.createStatement();
            ResultSet queryOutput2 = statement2.executeQuery(selectQuerytables);
            int count2 = 0;
            while (queryOutput2.next()) {
                int tableId = queryOutput2.getInt("id_table");
                InputStream stream = new FileInputStream("src/main/java/fr/pa3al2g3/esgi/jello/asset/favoris.png");
                Image favorisimage = new Image(stream);
                ImageView favorisImg = new ImageView();
                favorisImg.setImage(favorisimage);
                favorisImg.setFitHeight(45);
                favorisImg.setFitWidth(45);
                GridPane.setMargin(favorisImg, new Insets(20, 20, 0, 0));
                GridPane.setHalignment(favorisImg, HPos.RIGHT);
                GridPane.setValignment(favorisImg, VPos.TOP);

                // button sur le favoris
                Button favorisButton = new Button();
                favorisButton.setPrefHeight(45);
                favorisButton.setPrefWidth(45);
                favorisButton.setOpacity(0);
                GridPane.setMargin(favorisButton, new Insets(20, 20, 0, 0));
                GridPane.setHalignment(favorisButton, HPos.RIGHT);
                GridPane.setValignment(favorisButton, VPos.TOP);
                favorisButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            ConnectionDb connectNow = new ConnectionDb();
                            Connection conn = connectNow.connect();
                            String updateQuery = "UPDATE table_trello SET favorites = 1 WHERE id_table = " + tableId + ";";
                            Statement statement5 = conn.createStatement();
                            statement5.executeUpdate(updateQuery);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        init(projectId);
                    }
                });
                Button tabButton = new Button();
                tabButton.setPrefWidth(300);
                tabButton.setPrefHeight(225);
                tabButton.setStyle("-fx-background-color: #ff9161; -fx-background-radius: 25");
                tabButton.setText(queryOutput2.getString("title"));
                tabButton.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 24));
                tabButton.setOnAction(new EventHandler<ActionEvent>() {
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
                        MainApplication.getModelManager().getTableModel().init(projectId,tableId);
                    }
                });
                Pane empty = new Pane();
                empty.setPrefWidth(50);

                gridGroups.add(empty, count2, 1);
                count2 += 1;
                gridGroups.add(tabButton, count2, 1);
                gridGroups.add(favorisImg, count2, 1);
                gridGroups.add(favorisButton, count2, 1);
                count2 += 1;
            }
            Button buttonAddTable = new Button();
            buttonAddTable.setText("Ajouter une table");
            buttonAddTable.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.getDialogPane().setPrefWidth(250);
                    dialog.setTitle("Nouvelle table");

                    GridPane gridPane = new GridPane();

                    Text txt = new Text("Entrer le nom de votre table");

                    TextField projectName = new TextField();
                    Platform.runLater(projectName::requestFocus);
                    projectName.setText("Nouvelle table");

                    gridPane.addRow(0, txt);
                    gridPane.setAlignment(Pos.CENTER);
                    gridPane.setVgap(10);
                    gridPane.addRow(1, projectName);
                    dialog.getDialogPane().setContent(gridPane);

                    ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    dialog.getDialogPane().getButtonTypes().addAll(btnOk, btnCancel);

                    projectName.textProperty().addListener((observable, oldValue, newValue) -> {
                        dialog.getDialogPane().lookupButton(btnOk).setDisable(!newValue.matches("^[a-zA-Z][a-zA-Z0-9_-]+$"));
                    });


                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == btnOk) {
                            ConnectionDb connectNow = new ConnectionDb();
                            Connection conn = connectNow.connect();
                            try {
                                String insertQuery = "INSERT INTO table_trello (title,fk_id_group) VALUE ('" + projectName.getText() + "','" + groupid + "')";
                                Statement statement = conn.createStatement();
                                statement.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
                                statement.getGeneratedKeys();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                        return null;
                    });
                    dialog.showAndWait();
                    init(projectId);
                }
            });
            gridButton.add(gridGroups, 0, 1);
            gridButton.add(buttonAddTable, 1, 1);

        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setFavorite(int projectId) {
        gridFavoris.getChildren().clear();
        ConnectionDb connectNow = new ConnectionDb();
        Connection conn = connectNow.connect();
        // CHARGEMENT DES FAVORIS
        ScrollPane scrollFavoris = (ScrollPane) MainApplication.getScene().lookup("#favoris");
        gridFavoris.setPrefHeight(600);
        String selectQueryFavorites = "SELECT id_table, table_trello.title, `group`.title as groupTitle, table_trello.favorites FROM table_trello \n" +
                "INNER JOIN `group` ON `group`.fk_id_project_trello =" + projectId + "\n" +
                "AND table_trello.fk_id_group = `group`.id_group\n" +
                "WHERE table_trello.favorites = true;";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQueryFavorites);
            int count = 0;
            while (queryOutput.next()) {
                Pane empty = new Pane();
                empty.setPrefWidth(50);
                int tableId = queryOutput.getInt("id_table");
                // image favoris jaune
                InputStream stream = new FileInputStream("src/main/java/fr/pa3al2g3/esgi/jello/asset/favorisjaune.png");
                Image favorisjauneimage = new Image(stream);
                ImageView favorisjaune = new ImageView();
                favorisjaune.setImage(favorisjauneimage);
                favorisjaune.setFitHeight(45);
                favorisjaune.setFitWidth(45);
                GridPane.setMargin(favorisjaune, new Insets(20, 20, 0, 0));
                GridPane.setHalignment(favorisjaune, HPos.RIGHT);
                GridPane.setValignment(favorisjaune, VPos.TOP);

                // button sur le favoris jaune
                Button favorisButton = new Button();
                favorisButton.setPrefHeight(45);
                favorisButton.setPrefWidth(45);
                favorisButton.setOpacity(0);
                GridPane.setMargin(favorisButton, new Insets(20, 20, 0, 0));
                GridPane.setHalignment(favorisButton, HPos.RIGHT);
                GridPane.setValignment(favorisButton, VPos.TOP);

                // carte Table trello
                Button tabButton = new Button();
                tabButton.setPrefWidth(300);
                tabButton.setPrefHeight(225);
                tabButton.setStyle("-fx-background-color: #ff9161; -fx-background-radius: 25");
                tabButton.setText(queryOutput.getString("title"));
                tabButton.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 24));
                tabButton.setOnAction(new EventHandler<ActionEvent>() {
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
                        MainApplication.getModelManager().getTableModel().init(projectId,tableId);
                    }
                });
                // ajout dans le scroll pane
                gridFavoris.add(empty, count, 0);
                count += 1;
                gridFavoris.add(tabButton, count, 0);
                gridFavoris.add(favorisjaune, count, 0);
                gridFavoris.add(favorisButton, count, 0);
                count += 1;
                favorisButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(tableId);
                        try {
                            ConnectionDb connectNow = new ConnectionDb();
                            Connection conn = connectNow.connect();
                            String updateQuery = "UPDATE table_trello SET favorites = 0 WHERE id_table = " + tableId + ";";
                            Statement statement6 = conn.createStatement();
                            statement6.executeUpdate(updateQuery);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        gridFavoris.getChildren().clear();
                        init(projectId);
                    }
                });
            }

        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
        scrollFavoris.setPrefHeight(600);
        scrollFavoris.setContent(gridFavoris);
    }

    private void setProjectName(int projectId) {
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
    }
}
