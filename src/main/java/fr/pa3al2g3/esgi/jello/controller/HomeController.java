package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeController {
    private int projectId;
    private HashMap<String, GridPane> hmap;
    private boolean alertFlag;

    @FXML
    public void onCreateButtonClick(ActionEvent event){
        alertFlag = false;
        projectId = -1;
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setPrefWidth(250);
        dialog.setTitle("Nouveau projet");

        GridPane gridPane = new GridPane();

        Text txt = new Text("Entrer le nom de votre projet");

        TextField projectName = new TextField();
        Platform.runLater(projectName::requestFocus);
        projectName.setText("Nouveau_projet");

        gridPane.addRow(0, txt);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.addRow(1, projectName);
        dialog.getDialogPane().setContent(gridPane);

        ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(btnOk, btnCancel);

        projectName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("^[a-zA-Z][a-zA-Z0-9_-]+$")){
                dialog.getDialogPane().lookupButton(btnOk).setDisable(false);
            }else{
                dialog.getDialogPane().lookupButton(btnOk).setDisable(true);
            }
        });


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnOk) {
                ConnectionDb connectNow = new ConnectionDb();
                Connection conn = connectNow.connect();
                String selectQuery = "SELECT id_project_trello FROM project_trello WHERE projectName = '" + projectName.getText() + "'";
                boolean exist = false;

                try {
                    Statement statement = conn.createStatement();
                    ResultSet queryOutput = statement.executeQuery(selectQuery);
                    while (queryOutput.next()) {
                        //ResultSet processing here
                        exist = true;
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if(!exist){
                    try {
                        String insertQuery = "INSERT INTO project_trello (projectName) VALUE ('"+ projectName.getText() + "')";
                        Statement statement = conn.createStatement();
                        statement.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
                        ResultSet rs= statement.getGeneratedKeys();
                        if (rs.next())
                        {
                            projectId = (int) rs.getLong(1);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    return projectName.getText();
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");

                    // Header Text: null
                    alert.setHeaderText(null);
                    alert.setContentText("Ce projet existe déjà!");
                    alert.showAndWait();
                    alertFlag = true;
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        while(alertFlag){ // Tant que le dialog ce ferme à cause de l'alerte, on réouvre le dialog
            alertFlag = false;
            result = dialog.showAndWait();
        }

        result.ifPresent(str ->{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("project-view.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
                MainApplication.setScene(new Scene(root));
                MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
                MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
                MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
                MainApplication.getStage().setMaximized(true);
                MainApplication.getModelManager().getHomeModel().addProject(str);
                MainApplication.getModelManager().getProjectModel().init(projectId);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

    }

    @FXML
    public void onClickConfigButton(ActionEvent event){
        this.hmap = new HashMap<>();
        Dialog<String> config_dialog = new Dialog<>();
        config_dialog.getDialogPane().setPrefWidth(1500); // changer valeur
        config_dialog.getDialogPane().setPrefHeight(900);
        config_dialog.setTitle("Configuration");
        config_dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setStyle("-fx-padding: 0");
        grid.setPrefHeight(900);
        grid.setPrefWidth(1500);

        GridPane gridLeft = new GridPane();
        gridLeft.setPrefWidth(375);
        gridLeft.setPrefHeight(900);
        gridLeft.setStyle("-fx-background-color: #FF8248");
        Text txt = new Text("Configuration");
        txt.setFont(new Font(30));
        txt.setFill(Paint.valueOf("white"));
        gridLeft.add(txt, 0, 0);
        GridPane.setMargin(txt, new Insets(20, 0, 50, 0));
        GridPane.setHalignment(txt, HPos.CENTER);
        ScrollPane scrollPaneConfig = new ScrollPane();
        scrollPaneConfig.setStyle("-fx-background:  #FF8248");
        scrollPaneConfig.getStyleClass().add("edge-to-edge");
        scrollPaneConfig.setPrefWidth(375);
        scrollPaneConfig.setPrefHeight(900);
        grid.add(gridLeft, 0, 0);

        ArrayList<String> testList = new ArrayList<String>();
        testList.add("abc");
        testList.add("def");
        testList.add("ghi");


        GridPane gridScrollPane = new GridPane();
        //gridScrollPane.setStyle("-fx-background-color: purple");

        GridPane rightGrid = new GridPane();

        int cpt = 0;

        for (String s : testList){
            /*Pane emptyLeft = new Pane();
            emptyLeft.setPrefWidth(68);
            emptyLeft.setPrefHeight(30);*/
            Button btn = new Button();
            btn.setText(s);
            //btn.setStyle("-fx-background-color: blue");
            btn.setPrefHeight(30);
            btn.setPrefWidth(150);
            //btn.setPadding(new Insets(10));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    rightGrid.getChildren().remove(0);
                    GridPane gridConfig = hmap.get(btn.getText());
                    rightGrid.add(gridConfig, 0, 0);
                }
            });


            /*Pane emptyRight = new Pane();
            emptyRight.setPrefWidth(68);
            emptyRight.setPrefHeight(30);*/
            /*gridScrollPane.add(emptyLeft, 0, cpt);
            gridScrollPane.add(btn, 1, cpt);
            gridScrollPane.add(emptyRight, 2, cpt);*/
            gridScrollPane.add(btn, 0, cpt);
            GridPane.setHalignment(btn, HPos.CENTER);
            cpt++;
            Pane empty = new Pane();
            //empty.setStyle("-fx-background-color: purple");
            empty.setPrefHeight(30);
            empty.setPrefWidth(287);
            gridScrollPane.add(empty, 0, cpt);
            cpt++;
        }

        scrollPaneConfig.setContent(gridScrollPane);
        gridLeft.add(scrollPaneConfig, 0, 1);
        //GridPane.setHalignment(gridScrollPane, HPos.CENTER);

        // Instanciation des differentes config
        initHmap();


        rightGrid.setStyle("-fx-background-color: #2b2b2c");
        rightGrid.setPrefHeight(900);
        rightGrid.setPrefWidth(1300);
        rightGrid.add(new GridPane(), 0, 0);
        grid.add(rightGrid, 1, 0);

        config_dialog.getDialogPane().setContent(grid);

        config_dialog.showAndWait();
    }

    public void initHmap(){
        GridPane testGrid = new GridPane();
        testGrid.setPrefHeight(25);
        testGrid.setPrefWidth(25);
        testGrid.setStyle("-fx-background-color: pink");
        this.hmap.put("abc", testGrid);

        GridPane test2Grid = new GridPane();
        test2Grid.setPrefHeight(100);
        test2Grid.setPrefWidth(100);
        test2Grid.setStyle("-fx-background-color: yellow");
        this.hmap.put("def", test2Grid);

        GridPane test3Grid = new GridPane();
        test3Grid.setPrefHeight(50);
        test3Grid.setPrefWidth(50);
        test3Grid.setStyle("-fx-background-color: blue");
        this.hmap.put("ghi", test3Grid);
    }
}
