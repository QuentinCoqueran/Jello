
package fr.pa3al2g3.esgi.jello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ConnectionController {

    @FXML
    private Button go_to_incription;

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
}