package fr.pa3al2g3.esgi.jello;

import fr.pa3al2g3.esgi.jello.enumerator.Importance;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("connection.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}