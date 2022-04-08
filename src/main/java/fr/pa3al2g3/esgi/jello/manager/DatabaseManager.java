package fr.pa3al2g3.esgi.jello.manager;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private URI uri;
    private String user;
    private String password;
    private String database;

    public DatabaseManager() throws URISyntaxException {
        this.uri = new URI("mysql://b8c3bb6a076ba6:80789dda@eu-cdbr-west-02.cleardb.net/heroku_6c9a472678d54e1");
        this.user = uri.getUserInfo().split(":")[0];
        this.password = uri.getUserInfo().split(":")[1];
        this.database = "jdbc:mysql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?sslmode=require";
    }

    public Connection getDb() throws SQLException {

        return DriverManager.getConnection(database, user, password);
    }
}
