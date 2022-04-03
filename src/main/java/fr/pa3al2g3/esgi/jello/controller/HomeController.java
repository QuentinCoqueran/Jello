package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
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
                String selectQuery = "SELECT id FROM project_trello WHERE projectName = '" + projectName.getText() + "'";
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
}
