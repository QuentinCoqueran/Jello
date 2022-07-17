package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import fr.pa3al2g3.esgi.jello.model.HomeModel;
import fr.pa3al2g3.esgi.jello.model.ProjectModel;
import javafx.application.Platform;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectController {


    @FXML
    public void onClickReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("home-view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainApplication.setScene(new Scene(root));
        MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
        MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
        MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
        MainApplication.getStage().setMaximized(true);
        MainApplication.getModelManager().getHomeModel().init();
    }

    public void onClickCreateGroup(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setPrefWidth(250);
        dialog.setTitle("Nouveau groupe");

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
            dialog.getDialogPane().lookupButton(btnOk).setDisable(!newValue.matches("^[a-zA-Z][a-zA-Z0-9_-]+$"));
        });


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnOk) {
                ConnectionDb connectNow = new ConnectionDb();
                Connection conn = connectNow.connect();
                try {
                    String insertQuery = "INSERT INTO `group` (title,fk_id_project_trello) VALUE ('" + projectName.getText() + "','" + ProjectModel.projectId + "')";
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
        MainApplication.getModelManager().getProjectModel().init(ProjectModel.projectId);
    }
}
