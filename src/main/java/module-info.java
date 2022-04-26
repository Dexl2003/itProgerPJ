module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.test to javafx.fxml;
    exports com.example.test;
    exports com.example.test.controllers;
    opens com.example.test.controllers to javafx.fxml;
    exports com.example.test.models;
    opens com.example.test.models to javafx.fxml;
}