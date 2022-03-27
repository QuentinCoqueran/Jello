package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.MainApplication;
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
import java.util.Optional;

public class HomeController {

    @FXML
    public void onCreateButtonClick(ActionEvent event){
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
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        projectName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("^[a-zA-Z][a-zA-Z0-9_-]+$")){
                dialog.getDialogPane().lookupButton(btnOk).setDisable(false);
            }else{
                dialog.getDialogPane().lookupButton(btnOk).setDisable(true);
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnOk) {
               /* ConnectionDb connectNow = new ConnectionDb();
                Connection connectionDB = connectNow.connect();
                String selectQuery = "SELECT id FROM PROJET WHERE name = " + projectName.getText();*/
                // FAIRE UN CHECK
                return projectName.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

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
                MainApplication.getModelManager().getProjectModel().init(str);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

    }
}
