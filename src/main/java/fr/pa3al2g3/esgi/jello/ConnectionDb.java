package fr.pa3al2g3.esgi.jello;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

public class ConnectionDb {


    public java.sql.Connection connect() {
        /*try {
            String url = "jdbc:mysql://localhost:3306/jello";
            String user = "root";
            String password = "root";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("connect ko" + e);
        }*/

        try {
            URI dbUri = new URI("mysql://b8c3bb6a076ba6:80789dda@eu-cdbr-west-02.cleardb.net/heroku_6c9a472678d54e1");

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException | URISyntaxException e){
            System.out.print("connect ko" + e);
        }

        return null;
    }
}