package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class HomeModel {
    private ArrayList<String> projects;

    public HomeModel(){
        this.projects = new ArrayList<>();
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");

        init();
    }

    private void addProject(String projectName){
        this.projects.add(projectName);
    }

    private void init(){
        ScrollPane scrollPane = (ScrollPane) MainApplication.getScene().lookup("#project_scroll_pane");
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: yellow");
        //grid.setAlignment(Pos.CENTER);
        if(projects.size() == 1){
            grid.setPrefHeight(74);
            grid.setPrefWidth(486);
            Button btn = new Button();
            btn.setText(projects.get(0));
            grid.add(btn, 0, 0);
        }else{
            int calcSize = (74 * projects.size()) + (64 * (projects.size() - 1));
            grid.setPrefHeight(calcSize);
            grid.setPrefWidth(540);
            int row = 0;
            for(String s : projects){
                Button btn = new Button();
                btn.setText(s);
                btn.setPrefHeight(74);
                btn.setPrefWidth(400);
                if(row != projects.size() || row != 0){
                    grid.addRow(row, btn);
                    row = row + 1;
                    Text emptyText = new Text("");
                    emptyText.prefHeight(64);
                    grid.add(emptyText, 0, row);
                    row = row + 1;
                }else {
                    grid.add(btn, 0, row);
                    row = row + 1;
                }
                GridPane.setHalignment(btn, HPos.CENTER);
            }
        }
        scrollPane.setContent(grid);
    }
}
