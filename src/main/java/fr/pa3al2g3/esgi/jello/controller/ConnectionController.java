
package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionController {

    @FXML
    private Button go_to_incription;

    @FXML
    private Button connect;

    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField password;

    @FXML
    private Text errorConnect;

    public void goInscription(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("incription-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MainApplication.setScene(new Scene(root1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectButton() {
        String pseudoText = pseudo.getText();
        String passwordText = password.getText();
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();
        String connectQuery = "SELECT * from users WHERE pseudo = '" + pseudoText + "' AND password = '" + passwordText + "'";
        boolean empty = true;
        int userId = 0;
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                //ResultSet processing here
                userId = queryOutput.getInt("id_user");
                empty = false;
            }
            if (empty) {
                errorConnect.setText("Username/Password invalide");
            } else {
                //si connection ok
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("home-view.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                MainApplication.setScene(new Scene(root));
                MainApplication.getStage().setHeight(MainApplication.getScreenBounds().getHeight());
                MainApplication.getStage().setWidth(MainApplication.getScreenBounds().getWidth());
                MainApplication.setWindow(MainApplication.getStage().getScene().getWindow());
                MainApplication.getStage().setMaximized(true);
                MainApplication.getModelManager().getHomeModel().init(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}