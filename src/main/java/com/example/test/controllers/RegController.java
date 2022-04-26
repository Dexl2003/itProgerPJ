package com.example.test.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.test.DB;
import com.example.test.HelloApplication;
import com.example.test.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authButton;

    @FXML
    private TextField authLogin;

    @FXML
    private PasswordField authPass;

    @FXML
    private Button regButton;

    @FXML
    private TextField regEmail;

    @FXML
    private TextField regLogin;

    @FXML
    private PasswordField regPass;

    @FXML
    private CheckBox regRights;

    private DB db = new DB();

    @FXML
    void initialize() {
        regButton.setOnAction(actionEvent -> {
            try {
                registrationUser();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        authButton.setOnAction(actionEvent -> {
            try {
                authUser(actionEvent);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void authUser(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String login = authLogin.getCharacters().toString();
        String password = authPass.getCharacters().toString();

        authLogin.setStyle("-fx-border-color: #fafafa");
        authPass.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1) {
            authLogin.setStyle("-fx-border-color: #e06249");
            authBtnOff();
        } else if (password.length() <= 5) {
            authPass.setStyle("-fx-border-color: #e06249");
            authBtnOff();
        } else if (!db.authUser(login,password)){
            authError();
        } else {
            successAuth();

            FileOutputStream fos = new FileOutputStream("user.setting");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articlesPanel.fxml", stage);
        }

        regRights.setOnAction(actionEvent -> {
            authBtnOn();

        });


    }

    public void authError(){
        authButton.setText("Не верные данные");
        authLogin.setStyle("-fx-border-color: #e06249");
        authLogin.setText(null);
        authPass.setStyle("-fx-border-color: #e06249");
        authPass.setText(null);
    }

    public void authBtnOn(){
        authButton.setText("Войти");
    }

    public void authBtnOff(){
        authButton.setText("Заполните все поля");
    }

    public void successAuth(){
        authLogin.setText(null);
        authPass.setText(null);
        authButton.setText("Успешная авторизация");

    }


    private void registrationUser() throws SQLException, ClassNotFoundException {
        String login = regLogin.getCharacters().toString();
        String email = regEmail.getCharacters().toString();
        String password = regPass.getCharacters().toString();


        regLogin.setStyle("-fx-border-color: #fafafa");
        regEmail.setStyle("-fx-border-color: #fafafa");
        regPass.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1) {
            regLogin.setStyle("-fx-border-color: #e06249");
            regBtnOff();

        } else if (email.length() <= 5 || !email.contains("@") || !email.contains(".")) {
            regEmail.setStyle("-fx-border-color: #e06249");
            regBtnOff();

        } else if (password.length() <= 5) {
            regPass.setStyle("-fx-border-color: #e06249");
            regBtnOff();
        } else if (!regRights.isSelected()) {
            regRights.setStyle("-fx-border-color: #e06249");
            regBtnOff();
        } else if (db.isExistsUser(login)){
            regBtnOff();
            regButton.setText("Логин занят");
        } else {
            db.regUser(login, email, password);
            successReg();
        }

        regRights.setOnAction(actionEvent -> {
            regBtnOn();

        });

    }

    public void regBtnOn(){
        regButton.setDisable(false);
        regButton.setText("Зарегистрироваться");
        regButton.setStyle("-fx-background-color: #d35339");
        regRights.setStyle("-fx-border-color: null");
    }

    public void regBtnOff(){
        regButton.setDisable(true);
        regButton.setText("Заполните все поля");
        regButton.setStyle("-fx-background-color: grey");
        regRights.setSelected(false);
    }

    public void successReg(){
        regEmail.setText("");
        regLogin.setText("");
        regPass.setText("");
        regRights.setSelected(false);
        regButton.setText("Успешная регистрация");
    }


}


