
package fr.pa3al2g3.esgi.jello;

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
import javafx.stage.Screen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionController {

    @FXML
    private Button go_to_incription;

    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorConnect;

    public void goInscription(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("incription.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            MainApplication.setScene(new Scene(root1, screenSize.getWidth(), screenSize.getHeight()));
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
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                //ResultSet processing here
                empty = false;
            }
            if (empty) {
                errorConnect.setText("Erreur connection");
            } else {
                //si connection ok
                errorConnect.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}