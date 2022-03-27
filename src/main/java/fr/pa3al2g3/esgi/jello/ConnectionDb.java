package fr.pa3al2g3.esgi.jello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

public class ConnectionDb {


    public java.sql.Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/jello";
            String user = "root";
            String password = "root";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("connect ko" + e);
        }
        return null;
    }
}