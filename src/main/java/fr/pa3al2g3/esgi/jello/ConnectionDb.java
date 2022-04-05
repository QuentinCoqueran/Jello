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
            URI dbUri = new URI("postgres://jkavgnriemveuq:46a94ca9420d54a24cc3327daa364566a98c84daf904e85bb0ee55fb1d7c7253@ec2-52-30-67-143.eu-west-1.compute.amazonaws.com:5432/dfeaibbjcf1tqi");

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException | URISyntaxException e){
            System.out.print("connect ko" + e);
        }

        return null;
    }
}