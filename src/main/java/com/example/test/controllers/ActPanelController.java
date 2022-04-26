package com.example.test.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.test.DB;
import com.example.test.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button newActBtn;

    @FXML
    private VBox panelVBox;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, IOException {
        DB db = new DB();
        ResultSet resultSet = db.getAct();

        while (resultSet.next()){
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("articles.fxml")));

            Label title = (Label) node.lookup("#title_news");
            Label intro = (Label) node.lookup("#intro_news");
            title.setText(resultSet.getString("title"));
            intro.setText(resultSet.getString("intro"));

            node.setOnMouseEntered(mouseEvent -> {
                node.setStyle("-fx-background-color: #707173");
            });

            node.setOnMouseExited(mouseEvent -> {
                node.setStyle("-fx-background-color: #343434");
            });

            panelVBox.getChildren().add(node);
            panelVBox.setSpacing(10);
        }

        exitButton.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newActBtn.setOnAction(event -> {
            try {
                newAct(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void newAct(Event event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("newAct.fxml", stage);
    }

    private void exitUser(ActionEvent event) throws IOException {
        File file = new File("user.setting");
        file.delete();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml", stage);
    }

}
