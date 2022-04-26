package com.example.test.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.test.DB;
import com.example.test.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewActController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea newIntro;

    @FXML
    private TextArea newText;

    @FXML
    private TextField newTitle;

    @FXML
    void Exit(ActionEvent event) {
        try {
            exitlayer(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitlayer(ActionEvent event) throws IOException {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articlesPanel.fxml", stage);
    }

    @FXML
    void addAct(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String title = newTitle.getCharacters().toString();
        String intro = newIntro.getText();
        String text = newText.getText();


        newTitle.setStyle("-fx-border-color: #fafafa");
        newIntro.setStyle("-fx-border-color: #fafafa");
        newText.setStyle("-fx-border-color: #fafafa");


        if (title.length() <= 5) {
            newTitle.setStyle("-fx-border-color: #e06249");
        } else if (intro.length() <= 10) {
            newIntro.setStyle("-fx-border-color: #e06249");
        } else if (text.length() <= 15) {
            newText.setStyle("-fx-border-color: #e06249");
        } else {
            DB db = new DB();
            db.arrArticle(title,intro,text);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articlesPanel.fxml", stage);
        }
    }


    @FXML
    void initialize() {

    }

}
