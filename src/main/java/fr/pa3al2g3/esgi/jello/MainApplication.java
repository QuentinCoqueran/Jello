package fr.pa3al2g3.esgi.jello;

import fr.pa3al2g3.esgi.jello.manager.DatabaseManager;
import fr.pa3al2g3.esgi.jello.manager.ModelManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApplication extends Application {
    private static Stage stage;
    private static Rectangle2D screenBounds;

    private static Window window;
    private static Class instance;
    private static ModelManager modelManager;
    private static Connection dbConnection;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, SQLException {
        MainApplication.dbConnection = new DatabaseManager().getDb();
        MainApplication.instance = getClass();
        MainApplication.stage = stage;
        MainApplication.modelManager = new ModelManager();
        Parent root = FXMLLoader.load(getClass().getResource("connection-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setMaximized(true);
        MainApplication.screenBounds = Screen.getPrimary().getBounds();
        stage.show();
        MainApplication.window = stage.getScene().getWindow();

    }

    public static Connection getDatabase(){
        return dbConnection;
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static Scene getScene() {
        return stage.getScene();
    }

    public static Window getWindow() {
        return window;
    }

    public static Class getInstance() {
        return instance;
    }


    public static ModelManager getModelManager() {
        return modelManager;

    }

    public static Stage getStage () {
        return stage;
    }

    public static Rectangle2D getScreenBounds () {
        return screenBounds;
    }

    public static void setWindow (Window window){
        MainApplication.window = window;
    }

    public static void main (String[]args){
        launch();
    }
}