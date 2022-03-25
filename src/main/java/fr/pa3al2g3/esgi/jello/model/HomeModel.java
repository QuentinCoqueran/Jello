package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class HomeModel {
    private ArrayList<String> projects;

    public HomeModel() {
        this.projects = new ArrayList<>();
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");
        addProject("aBc");
        addProject("Test");





        init();
    }

    private void addProject(String projectName) {
        this.projects.add(projectName);
    }

    private void init() {
        ScrollPane scrollPane = (ScrollPane) MainApplication.getScene().lookup("#project_scroll_pane");
        scrollPane.setStyle("-fx-background-color: #FFE4B5");
        scrollPane.setStyle("-fx-background: #FFE4B5");
        scrollPane.getStyleClass().add("edge-to-edge");

        GridPane scrollGrid = new GridPane();
        int row = 0;
        for (String s : projects) {

            GridPane gridButton = new GridPane();

            Pane emptySpaceButtonLeft = new Pane();
            emptySpaceButtonLeft.setPrefHeight(this.calcHeigth(74));
            emptySpaceButtonLeft.setPrefWidth(this.calcWidth(55));

            Pane emptySpaceButtonRight = new Pane();
            emptySpaceButtonRight.setPrefHeight(this.calcHeigth(74));
            emptySpaceButtonRight.setPrefWidth(this.calcWidth(55));

            Button btn = new Button();
            btn.setText(s);
            btn.setFont(new Font(20));
            btn.setStyle("-fx-font-weight: 800");
            btn.setTextAlignment(TextAlignment.CENTER);
            btn.setTextFill(Paint.valueOf("white"));
            btn.setStyle("-fx-background-color: #FF8248");
            btn.setPrefHeight(this.calcHeigth(74));
            btn.setPrefWidth(this.calcWidth(372));

            if(projects.size() >= 9){
                emptySpaceButtonLeft.setPrefWidth(this.calcWidth(53));
                emptySpaceButtonRight.setPrefWidth(this.calcWidth(53));
                btn.setPrefWidth(this.calcWidth(367));
            }

            gridButton.add(emptySpaceButtonLeft, 0, 0);
            gridButton.add(btn, 1, 0);
            gridButton.add(emptySpaceButtonRight, 2, 0);

            scrollGrid.addRow(row, gridButton);
            row = row + 1;

            Pane emptySpace = new Pane();
            emptySpace.setPrefHeight(this.calcHeigth(66));

            scrollGrid.add(emptySpace, 0, row);
            row = row + 1;

        }
        scrollPane.setContent(scrollGrid);


    }

    private double calcWidth(double size) {
        return ((size * 100 / 1728) * (MainApplication.getWindow().getWidth() / 100));
    }

    private double calcHeigth(double size) {
        return ((size * 100 / 1728) * (MainApplication.getWindow().getHeight() / 100));
    }
}
