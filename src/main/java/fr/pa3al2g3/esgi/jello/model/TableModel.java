package fr.pa3al2g3.esgi.jello.model;

import eu.hansolo.tilesfx.Alarm;
import fr.pa3al2g3.esgi.jello.ConnectionDb;
import fr.pa3al2g3.esgi.jello.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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
import java.util.Objects;

public class TableModel {

    private ScrollPane scrollPane;
    private GridPane grid_new = new GridPane();
    private GridPane gridList = new GridPane();
    private GridPane gridCard = new GridPane();
    private GridPane gridPaneDialogArr = new GridPane();
    private GridPane gridPaneLabel = new GridPane();

    private TextArea textAreaDialogDescription = new TextArea();


    private int numberList = 0;
    private int countCard = 0;
    private int countLabel = 0;

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

    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    public void dialogCard(Label textIdCardText, String textBtn) {
        GridPane gridCarteDialog = new GridPane();
        gridPaneDialogArr = new GridPane();
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();
        String connectQueryDescription = "select discription from card where id_card = " + Integer.parseInt(textIdCardText.getText());

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutputDesciption = statement.executeQuery(connectQueryDescription);
            while (queryOutputDesciption.next()) {
                textAreaDialogDescription.setText(queryOutputDesciption.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectLabel(textIdCardText);
        Text titleCardDialog = new Text(textBtn);
        titleCardDialog.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Text titleCardDescDialog = new Text("Description");
        titleCardDescDialog.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 15));

        Pane paneTitleCardDescDialog = new Pane();
        paneTitleCardDescDialog.setPrefHeight(30);

        Pane paneTitleCardDescDialog2 = new Pane();
        paneTitleCardDescDialog2.setPrefHeight(30);
        paneTitleCardDescDialog2.setPrefWidth(200);


        textAreaDialogDescription.setPrefHeight(100);
        textAreaDialogDescription.setPrefWidth(300);
        textAreaDialogDescription.setPromptText("Taper votre description...");


        for (int i = 0; i < 8; i++) {
            Button addDialog = new Button();
            addDialog.setText("+");
            addDialog.setStyle("-fx-background-color:transparent;-fx-border-width: 3px;-fx-border-color: black;-fx-border-radius:60;-fx-padding:5px;");
            addDialog.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            addDialog.setPrefWidth(25);

            Pane paneArr = new Pane();
            paneArr.setPrefHeight(50);
            paneArr.setPrefWidth(50);
            Text titleCardDialogMembre = new Text();
            titleCardDialogMembre.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 12));
            if (i == 0) {
                titleCardDialogMembre.setText("Membres");
            }
            if (i == 2) {
                gridPaneDialogArr.add(addDialog, i, 2);
                titleCardDialogMembre.setText("Labels");
                addDialog.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        colorPickerLabel(textIdCardText);
                    }
                });

            }
            if (i == 4) {
                titleCardDialogMembre.setText("Dead Line");
            }
            if (i == 6) {
                titleCardDialogMembre.setText("Importance");
            }
            gridPaneDialogArr.add(titleCardDialogMembre, i, 0);
            if (i != 2) {
                gridPaneDialogArr.add(addDialog, i, 1);
            }
            i += 1;
            gridPaneDialogArr.add(paneArr, i, 0);
        }

        Button btnSaveDescriptionDialog = new Button();
        btnSaveDescriptionDialog.setText("Enregistrer");
        btnSaveDescriptionDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String textDecription = textAreaDialogDescription.getText();
                saveDescriptionDialog(textDecription, Integer.parseInt(textIdCardText.getText()));
            }
        });
        Pane rightDialog = new Pane();
        rightDialog.setPrefWidth(200);
        rightDialog.setPrefHeight(10);

        Dialog<String> config_dialog = new Dialog<>();
        config_dialog.getDialogPane().setPrefWidth(1000);
        config_dialog.getDialogPane().setPrefHeight(500);
        config_dialog.setTitle("Configuration carte");
        config_dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane gridDescription = new GridPane();
        gridDescription.add(paneTitleCardDescDialog, 0, 0);
        gridDescription.add(titleCardDescDialog, 0, 1);
        gridDescription.add(paneTitleCardDescDialog2, 0, 2);
        gridDescription.add(textAreaDialogDescription, 0, 3);
        gridDescription.add(btnSaveDescriptionDialog, 0, 4);

        gridCarteDialog.add(titleCardDialog, 0, 0);
        gridCarteDialog.add(gridDescription, 0, 1);
        gridCarteDialog.add(rightDialog, 1, 1);
        gridCarteDialog.add(gridPaneDialogArr, 2, 1);


        config_dialog.getDialogPane().setContent(gridCarteDialog);
        config_dialog.show();
    }

    @FXML
    private void selectLabel(Label textIdCardText) {
        countLabel = 0;
        gridPaneLabel = new GridPane();
        String connectQueryDescription = "SELECT * FROM label " +
                "INNER JOIN label_card_union  ON label.id_label  = label_card_union.fk_id_label WHERE label_card_union.fk_id_card = " + Integer.parseInt(textIdCardText.getText());
        try {
            ConnectionDb connectNow = new ConnectionDb();
            Connection connectionDB = connectNow.connect();
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutputLabbel = statement.executeQuery(connectQueryDescription);
            while (queryOutputLabbel.next()) {
                final TextField lab = new TextField(queryOutputLabbel.getString(2));
                lab.setStyle("-fx-background-color: " + queryOutputLabbel.getString(3) + "; -fx-border-width: 1px;-fx-border-color: white;");
                lab.setEditable(false);
                gridPaneLabel.add(lab, 2, countLabel);
                countLabel += 1;
            }
            gridPaneDialogArr.add(gridPaneLabel, 2, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void colorPickerLabel(Label textIdCardText) {

        Button btnAnnulerLabelAdd = new Button("Annuler");
        Button btnSaveLabelAdd = new Button("Enregistrer");

        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.RED);

        final TextField lab = new TextField("");
        lab.setStyle("-fx-background-color: " + toRGBCode(colorPicker.getValue()));

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lab.setStyle("-fx-background-color: " + toRGBCode(colorPicker.getValue()));
            }
        });
        btnAnnulerLabelAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPaneDialogArr.getChildren().remove(colorPicker);
                gridPaneDialogArr.getChildren().remove(lab);
                gridPaneDialogArr.getChildren().remove(btnAnnulerLabelAdd);
                gridPaneDialogArr.getChildren().remove(btnSaveLabelAdd);
            }
        });
        btnSaveLabelAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String connectQueryFkLabel = "";
                String connectQuery = "INSERT INTO label (title,color) VALUES (' " + lab.getText() + "', '" + toRGBCode(colorPicker.getValue()) + "') ;";
                String lastIdInsert = "SELECT LAST_INSERT_ID();";
                try {
                    ConnectionDb connectNow = new ConnectionDb();
                    Connection connectionDB = connectNow.connect();
                    Statement statement = connectionDB.createStatement();
                    statement.executeUpdate(connectQuery);
                    ResultSet queryOutputLastId = statement.executeQuery(lastIdInsert);
                    while (queryOutputLastId.next()) {
                        connectQueryFkLabel = "INSERT INTO label_card_union (fk_id_label,fk_id_card) " +
                                "VALUES ( " + Integer.parseInt(queryOutputLastId.getString(1)) + " , " + Integer.parseInt(textIdCardText.getText()) + ") ;";
                    }
                    statement.executeUpdate(connectQueryFkLabel);

                    gridPaneDialogArr.getChildren().remove(colorPicker);
                    gridPaneDialogArr.getChildren().remove(lab);
                    gridPaneDialogArr.getChildren().remove(btnAnnulerLabelAdd);
                    gridPaneDialogArr.getChildren().remove(btnSaveLabelAdd);
                    selectLabel(textIdCardText);
                    selectList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        gridPaneDialogArr.add(colorPicker, 2, 3);

        gridPaneDialogArr.add(lab, 2, 4);

        gridPaneDialogArr.add(btnSaveLabelAdd, 2, 5);

        gridPaneDialogArr.add(btnAnnulerLabelAdd, 2, 6);


    }

    @FXML
    private void selectList() {
        grid_new.getChildren().remove(grid_new);
        grid_new = new GridPane();
        ConnectionDb connectNow = new ConnectionDb();
        Connection connectionDB = connectNow.connect();

/*        String connectQuery = "select list_trello.title,id_list, card.title, card.id_card from list_trello \n" +
                "inner join card on card.fk_id_list =list_trello.id_list\n" +
                "where fk_id_table = 1\n" +
                "UNION\n" +
                "select list_trello.title,id_list,'','' from list_trello\n" +
                "where fk_id_table = 1 order by id_list;";
                */
        String connectQueryIdList = "Select distinct id_list,title from list_trello;";


        String connectQueryLabelColor = "SELECT color ,label_card_union.fk_id_card FROM label \n" +
                "INNER JOIN label_card_union  ON label.id_label  = label_card_union.fk_id_label;";
        try {
            Statement statement = connectionDB.createStatement();
            Statement statement1 = connectionDB.createStatement();
            Statement statement2 = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQueryIdList);
            while (queryOutput.next()) {
                //LIST
                int idList = queryOutput.getInt(1);
                String connectQueryCard = "Select id_card, title from card where fk_id_list = " + idList;
                ResultSet queryOutputCard = statement1.executeQuery(connectQueryCard);
                Text titleList = new Text(queryOutput.getString(2));
                titleList.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 15));

                Label textIdList = new Label(String.valueOf(idList));
                textIdList.setPrefWidth(1);
                textIdList.setPrefHeight(1);
                textIdList.setStyle("fx-background-color: transparent");

                Pane paneList = new Pane();
                paneList.setPrefHeight(30);
                paneList.setPrefWidth(1);

                Pane paneLeftEnd = new Pane();
                paneLeftEnd.setPrefHeight(1);
                paneLeftEnd.setPrefWidth(40);
                paneLeftEnd.setStyle("-fx-background-color: transparent");

                Pane panebotEndAdd = new Pane();
                panebotEndAdd.setPrefHeight(30);
                panebotEndAdd.setPrefWidth(200);
                panebotEndAdd.setStyle("-fx-background-color: transparent");

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
                if (textIdList.getText() != null && textIdList.getText().equals(btnSelectedIdList)) {
                    btnMoveCardInThisList.setVisible(false);
                }

                Button addCard = new Button();
                addCard.setText("+");
                addCard.setStyle("-fx-background-color:transparent;-fx-border-width: 3px;-fx-border-color: black;-fx-border-radius:60;-fx-padding:5px;");
                addCard.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 17));
                addCard.setPrefWidth(37);

                Pane panebotEnd = new Pane();
                panebotEnd.setPrefHeight(10);
                panebotEnd.setPrefWidth(200);
                panebotEnd.setStyle("-fx-background-color: transparent");

                Pane paneRightEnd = new Pane();
                paneRightEnd.setPrefHeight(1);
                paneRightEnd.setPrefWidth(40);
                paneRightEnd.setStyle("-fx-background-color: transparent");

                Pane pane = new Pane();
                pane.setPrefHeight(100);
                pane.setPrefWidth(100);
                pane.setStyle("-fx-background-color: transparent");

                gridList = new GridPane();
                gridList.setPrefWidth(200);
                gridList.setStyle("-fx-background-color:#FFF1D7;-fx-background-radius:20;-fx-effect: dropshadow(three-pass-box, grey, 2, 0, 0, 5);");
                countCard = 0;
                gridList.add(paneLeftEnd, 0, 2);
                gridList.add(btnMoveCardInThisList, 1, 0);
                gridList.add(titleList, 1, 1);
                gridList.add(paneList, 1, 2);

                gridList.add(panebotEndAdd, 1, 4);
                gridList.add(addCard, 1, 5);
                gridList.add(panebotEnd, 1, 6);
                gridList.add(paneRightEnd, 2, 2);
                gridCard = new GridPane();
                gridList.add(gridCard, 1, 3);

                numberList += 1;
                grid_new.add(pane, numberList, 0);
                numberList += 1;
                grid_new.add(gridList, numberList, 0);

                GridPane.setHalignment(addCard, HPos.CENTER);
                GridPane.setHalignment(titleList, HPos.CENTER);
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
                while (queryOutputCard.next()) {
                    //CARD

                    ResultSet queryOutputLabelColor = statement2.executeQuery(connectQueryLabelColor);

                    Button btnCard = new Button();
                    btnCard.setPrefWidth(200);
                    btnCard.setPrefHeight(50);

                    btnCard.setText(queryOutputCard.getString(2));
                    InputStream stream = new FileInputStream("src/main/java/fr/pa3al2g3/esgi/jello/asset/share.png");

                    Image image = new Image(stream);
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);

                    GridPane gridImageMoveCard = new GridPane();
                    gridImageMoveCard.add(imageView, 0, 0);

                    Label textIdCard = new Label(queryOutputCard.getString(1));
                    textIdCard.setPrefWidth(1);
                    textIdCard.setPrefHeight(1);
                    textIdCard.setStyle("fx-background-color: transparent");

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

                    textAreaDialogDescription = new TextArea();
                    btnCard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            dialogCard(textIdCard, btnCard.getText());
                        }
                    });

                    if (!Objects.equals(btnCard.getText(), "")) {
                        int countLabelColor = 0;
                        gridCard.add(panebot, 0, countCard);
                        countCard += 1;
                        GridPane gridLabelColorCircle = new GridPane();
                        while (queryOutputLabelColor.next()) {
                            int idCardLabelColor = queryOutputLabelColor.getInt(2);
                            if (textIdCard.getText() != null && Integer.parseInt(textIdCard.getText()) == idCardLabelColor) {
                                final Circle circle = new Circle(10);
                                circle.setFill(Paint.valueOf(queryOutputLabelColor.getString(1)));
                                if (countLabelColor <= 7) {
                                    gridLabelColorCircle.add(circle, countLabelColor, 0);
                                } else {
                                    break;
                                }
                                countLabelColor += 1;
                            }
                        }
                        gridCard.add(gridLabelColorCircle, 0, countCard);
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

                    countCard += 1;
                    scrollPane.setContent(grid_new);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollPane.setContent(grid_new);
    }

    private void saveDescriptionDialog(String textDecription, int idCard) {
        String connectQueryUpdateDescription = "update card set discription = '" + textDecription + "' where id_card = " + idCard;
        try {
            ConnectionDb connectNow = new ConnectionDb();
            Connection connectionDB = connectNow.connect();
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(connectQueryUpdateDescription);
        } catch (SQLException e) {
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
                        System.out.println(idList);
                        String connectQuery = "INSERT INTO card (title,fk_id_list) VALUES ('" + cardTitle.getText() + "'," + idList + ");";
                        try {
                            ConnectionDb connectNow = new ConnectionDb();
                            Connection connectionDB = connectNow.connect();
                            Statement statement = connectionDB.createStatement();
                            statement.executeUpdate(connectQuery);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    checkAddCard = false;
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
            scrollPane.setContent(grid_new);
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
