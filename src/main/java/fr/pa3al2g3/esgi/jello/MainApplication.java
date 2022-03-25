package fr.pa3al2g3.esgi.jello;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage stage;
    private static Rectangle2D screenBounds;
    private static Window window;
    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        MainApplication.screenBounds = Screen.getPrimary().getBounds();
        stage.show();
        MainApplication.window = stage.getScene().getWindow();
    }

    public static void setScene(Scene scene){
        stage.setScene(scene);
        stage.setHeight(screenBounds.getHeight());
        stage.setWidth(screenBounds.getWidth());
        MainApplication.window = stage.getScene().getWindow();
        stage.setMaximized(true);
    }
    public static Scene getScene(){
        return stage.getScene();
    }

    public static Window getWindow() {
        return window;
    }

    public static void main(String[] args) {launch();}
}