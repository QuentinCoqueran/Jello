package fr.pa3al2g3.esgi.jello.model;

import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableModel {

    private ScrollPane scrollPane;
    private GridPane grid_new = new GridPane();
    private GridPane gridList = new GridPane();
    private GridPane gridCard = new GridPane();

    private int numberList = 0;
    private int countCard = 0;

    private boolean checkAddList = true;
    private boolean moveCard = false;
    private boolean checkAddCard = false;


    private String btnSelectedId = "";
    private String btnSelectedIdList = "";


    @FXML
    private TextField titleField;

    public TableModel() {

    }

    public void init() {
        scrollPane = (ScrollPane) MainApplication.getScene().lookup("#main_scrollbar");
        scrollPane.getStyleClass().add("edge-to-edge");
        scrollPane.setStyle("-fx-background-color: #FFE4B5;-fx-background: #FFE4B5");
        selectList();
    }

    private void updateCardOfList(String idList, String textIdCard) {
        Integer idCard = Integer.parseInt(textIdCard);
        String connectQuery = "update card set fk_id_list = '" + idList + "' where id_card = " + idCard + " ;";
        try {
            ConnectionDb connectNow = new ConnectionDb();
            Connection connectionDB = connectNow.connect();
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        moveCard = false;
        selectList();
    }

    @FXML
    private void selectList() {
        grid_new.getChildren().remove(grid_new);
        grid_new = new GridPane();
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();

        String connectQuery = "select list_trello.title,id_list, card.title, card.id_card from list_trello \n" +
                "inner join card on card.fk_id_list =list_trello.id_list\n" +
                "where fk_id_table = 1\n" +
                "UNION\n" +
                "select list_trello.title,id_list,'','' from list_trello\n" +
                "where fk_id_table = 1 order by id_list;";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                Text titleList = new Text(queryOutput.getString(1));
                titleList.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                int idList = queryOutput.getInt(2);
                Button btnCard = new Button();
                btnCard.setPrefWidth(200);
                btnCard.setPrefHeight(50);


                btnCard.setText(queryOutput.getString(3));
                InputStream stream = new FileInputStream("src/main/java/fr/pa3al2g3/esgi/jello/asset/share.png");

                Image image = new Image(stream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);

                GridPane gridImageMoveCard = new GridPane();
                gridImageMoveCard.add(imageView, 0, 0);


                Label textIdCard = new Label(queryOutput.getString(4));
                textIdCard.setPrefWidth(1);
                textIdCard.setPrefHeight(1);
                textIdCard.setStyle("fx-background-color: transparent");
                Label textIdList = new Label(queryOutput.getString(2));
                textIdList.setPrefWidth(1);
                textIdList.setPrefHeight(1);
                textIdList.setStyle("fx-background-color: transparent");

                if (moveCard && textIdCard.getText().equals(btnSelectedId)) {
                    btnCard.setStyle("-fx-border-width: 2px;-fx-border-color: red;");
                } else {
                    btnCard.setStyle("-fx-border-width: 0px;-fx-border-color: transparent;");
                    btnCard.setStyle("-fx-background-color: white;-fx-background-radius:10");
                }


                Pane panebot = new Pane();
                panebot.setPrefHeight(30);
                panebot.setPrefWidth(200);
                panebot.setStyle("-fx-background-color: transparent");

                Pane panebotEnd = new Pane();
                panebotEnd.setPrefHeight(10);
                panebotEnd.setPrefWidth(200);
                panebotEnd.setStyle("-fx-background-color: transparent");

                Pane panebotEndAdd = new Pane();
                panebotEndAdd.setPrefHeight(30);
                panebotEndAdd.setPrefWidth(200);
                panebotEndAdd.setStyle("-fx-background-color: transparent");

                Pane paneRightEnd = new Pane();
                paneRightEnd.setPrefHeight(1);
                paneRightEnd.setPrefWidth(40);
                paneRightEnd.setStyle("-fx-background-color: transparent");

                Pane paneLeftEnd = new Pane();
                paneLeftEnd.setPrefHeight(1);
                paneLeftEnd.setPrefWidth(40);
                paneLeftEnd.setStyle("-fx-background-color: transparent");

                Pane pane = new Pane();
                Pane paneList = new Pane();
                paneList.setPrefHeight(30);
                paneList.setPrefWidth(1);
                pane.setPrefHeight(100);
                pane.setPrefWidth(100);
                pane.setStyle("-fx-background-color: transparent");

                Button addCard = new Button();
                addCard.setText("+");
                addCard.setStyle("-fx-background-color:transparent;-fx-border-width: 3px;-fx-border-color: black;-fx-border-radius:60;-fx-padding:5px;");
                addCard.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
                addCard.setPrefWidth(37);


                Button btnMoveCardInThisList = new Button();
                btnMoveCardInThisList.setPrefWidth(200);
                btnMoveCardInThisList.setText("Deplacer votre carte ici");
                btnMoveCardInThisList.setVisible(moveCard);
                btnMoveCardInThisList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        updateCardOfList(textIdList.getText(), btnSelectedId);
                    }
                });
                if (textIdList.getText().equals(btnSelectedIdList)) {
                    btnMoveCardInThisList.setVisible(false);
                }

                gridList = new GridPane();
                gridList.setPrefWidth(200);
                gridList.setStyle("-fx-background-color:#FFF1D7;-fx-background-radius:20;-fx-effect: dropshadow(three-pass-box, grey, 2, 0, 0, 5);");

                GridPane.setHalignment(titleList, HPos.CENTER);
                GridPane.setHalignment(addCard, HPos.CENTER);

                if (!Objects.equals(btnCard.getText(), "")) {
                    gridCard.add(panebot, 0, countCard);
                    countCard += 1;
                    gridCard.add(btnCard, 0, countCard);
                    gridCard.add(gridImageMoveCard, 1, countCard);
                    gridImageMoveCard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            if (textIdCard.getText().equals(btnSelectedId) || !moveCard) {
                                if (moveCard) {
                                    moveCard = false;
                                    selectList();
                                } else {
                                    moveCard = true;
                                    System.out.println(btnCard.getText());
                                    System.out.println(textIdCard.getText());
                                    System.out.println(textIdList.getText());
                                    btnSelectedId = textIdCard.getText();
                                    btnSelectedIdList = textIdList.getText();
                                    selectList();
                                }
                            }
                        }
                    });
                }

                if ((queryOutput.getString(3).isEmpty() || queryOutput.isLast())) {
                    countCard = 0;
                    gridList.add(paneLeftEnd, 0, 2);
                    gridList.add(btnMoveCardInThisList, 1, 0);
                    gridList.add(titleList, 1, 1);
                    gridList.add(paneList, 1, 2);
                    gridList.add(gridCard, 1, 3);
                    gridList.add(panebotEndAdd, 1, 4);
                    gridList.add(addCard, 1, 5);
                    gridList.add(panebotEnd, 1, 6);
                    gridList.add(paneRightEnd, 2, 2);

                    gridCard = new GridPane();
                    numberList += 1;
                    grid_new.add(pane, numberList, 0);
                    numberList += 1;
                    grid_new.add(gridList, numberList, 0);
                }
                GridPane finalGridList = gridList;
                addCard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!checkAddCard) {
                            checkAddCard = true;
                            createCard(finalGridList, idList);
                        }
                    }
                });

                countCard += 1;
                scrollPane.setContent(grid_new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createCard(GridPane finalGridList, int idList) {
        if (checkAddCard) {
            GridPane gridSaveCard = new GridPane();
            TextField cardTitle = new TextField();
            Button btnSaveTitleCard = new Button();
            Button btnAnnulerTitleCard = new Button();
            cardTitle.setPromptText("Titre de votre carte...");
            btnSaveTitleCard.setText("Enregistrer");
            btnSaveTitleCard.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!cardTitle.getText().equals("")) {
                        String connectQuery = "INSERT INTO card (title,fk_id_list) VALUES ('" + cardTitle.getText() + "'," + idList + ");";
                        try {
                            ConnectionDb connectNow = new ConnectionDb();
                            Connection connectionDB = connectNow.connect();
                            Statement statement = connectionDB.createStatement();
                            statement.executeUpdate(connectQuery);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        checkAddCard = false;
                    }
                    finalGridList.getChildren().remove(gridSaveCard);
                    finalGridList.setPrefWidth(200);
                    selectList();
                }
            });
            btnAnnulerTitleCard.setText("Annuler");
            btnAnnulerTitleCard.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    finalGridList.getChildren().remove(gridSaveCard);
                    checkAddCard = false;
                }
            });
            gridSaveCard.add(cardTitle, 0, 0);
            gridSaveCard.add(btnSaveTitleCard, 0, 1);
            gridSaveCard.add(btnAnnulerTitleCard, 0, 2);
            finalGridList.add(gridSaveCard, 1, 7);
        }

    }

    @FXML
    public void addList() {
        if (checkAddList) {
            checkAddList = false;
            Pane text = new Pane();
            Button buttonAddList = new Button();
            Button buttonAnnulerList = new Button();
            text.setPrefWidth(100);
            text.setStyle("-fx-background-color: transparent");
            titleField = new TextField();
            numberList += 1;
            titleField.setPromptText("Votre titre...");
            titleField.setId("title_list");
            titleField.setPrefWidth(100);
            titleField.setPrefHeight(50);
            buttonAddList.setText("Creer votre liste");
            buttonAnnulerList.setText("Annuler");
            buttonAnnulerList.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    checkAddList = true;
                    grid_new.getChildren().remove(text);
                    grid_new.getChildren().remove(titleField);
                    grid_new.getChildren().remove(buttonAddList);
                    grid_new.getChildren().remove(buttonAnnulerList);
                    selectList();
                }
            });
            buttonAddList.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ConnectionDb connectNow = new ConnectionDb();
                    Connection connectionDB = connectNow.connect();
                    if (!titleField.getText().isEmpty()) {
                        String connectQuery = "INSERT INTO list_trello (title,fk_id_table) VALUES ('" + titleField.getText() + "',1);";
                        try {
                            Statement statement = connectionDB.createStatement();
                            statement.executeUpdate(connectQuery);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        checkAddList = true;
                        grid_new.getChildren().remove(text);
                        grid_new.getChildren().remove(titleField);
                        grid_new.getChildren().remove(buttonAddList);
                        grid_new.getChildren().remove(buttonAnnulerList);
                        selectList();
                    }
                }
            });
            numberList += 1;
            grid_new.add(text, numberList, 0);
            numberList += 1;
            GridPane gridAddList = new GridPane();
            gridAddList.add(titleField, 0, 0);
            gridAddList.add(buttonAddList, 0, 1);
            gridAddList.add(buttonAnnulerList, 0, 2);
            grid_new.add(gridAddList, numberList, 0);
            scrollPane.setContent(grid_new);
        }
    }
}
