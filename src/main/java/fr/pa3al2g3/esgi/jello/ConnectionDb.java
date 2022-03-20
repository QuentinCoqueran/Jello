package fr.pa3al2g3.esgi.jello;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {


    public java.sql.Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/jello";
            String user = "root";
            String password = "root";

            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
            System.out.print("connect ok");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("connect ko" + e);
        }
        return null;
    }
}