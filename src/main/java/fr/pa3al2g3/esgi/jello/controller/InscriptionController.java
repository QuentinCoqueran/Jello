package fr.pa3al2g3.esgi.jello.controller;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InscriptionController {
    @FXML
    private Button go_to_connexion;
    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField password;

    @FXML
    private Text errorConnect;

    public void goConnexion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.getInstance().getResource("connection-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MainApplication.setScene(new Scene(root1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inscriptionButton() throws IOException {
        String pseudoText = pseudo.getText();
        String passwordText = password.getText();
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();
        String connectQuery = "SELECT * from users WHERE pseudo = '" + pseudoText + "'";
        boolean exist = false;
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                //ResultSet processing here
                exist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (exist) {
            errorConnect.setText("Cette utilisateur existe déjà");
            return;
        }else if(!pseudoText.matches("[a-zA-Z0-9_-]*")){
            errorConnect.setText("Username invalide");
            return;
        }else if (passwordText.isEmpty()) {
            errorConnect.setText("Mot de passe vide");
            return;
        } else {
            connectQuery = "INSERT INTO users (pseudo,password) VALUES ('" + pseudoText + "','" + passwordText + "');";
            try {
                Statement statement = connectionDB.createStatement();
                statement.executeUpdate(connectQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            goConnexion();
        }

    }
}
