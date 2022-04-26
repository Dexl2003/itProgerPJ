package com.example.test;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "itproger_java";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean isExistsUser(String login) throws SQLException, ClassNotFoundException {
        String sql ="SELECT `id` FROM `users` WHERE `login` = ?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1,login);

        ResultSet res = preparedStatement.executeQuery();
        return res.next();
    }

    public void regUser(String login,String email, String pass) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1,login);
        preparedStatement.setString(2,email);
        preparedStatement.setString(3,pass);
        preparedStatement.executeUpdate();
    }

    public boolean authUser(String login, String pass) throws SQLException, ClassNotFoundException {
        String sql ="SELECT `id` FROM `users` WHERE `login` = ? AND `password` = ?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1,login);
        preparedStatement.setString(2,pass);

        ResultSet res = preparedStatement.executeQuery();
        return res.next();
    }

    public ResultSet getAct() throws SQLException, ClassNotFoundException {
        String sql ="SELECT `intro`,`title` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        return statement.executeQuery(sql);

    }

    public void arrArticle(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`) VALUES(?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1,title);
        preparedStatement.setString(2,intro);
        preparedStatement.setString(3,text);
        preparedStatement.executeUpdate();
    }
}
