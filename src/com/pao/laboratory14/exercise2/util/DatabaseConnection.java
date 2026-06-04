package com.pao.laboratory14.exercise2.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws IOException, SQLException {

        Properties props = new Properties();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("db.properties");

        if (is == null) {
            is = new FileInputStream(
                    "src/com/pao/laboratory14/exercise2/resources/db.properties"
            );
        }

        props.load(is);
        is.close();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user", "");
        String password = props.getProperty("db.password", "");

        // creează folderul output dacă nu există
        new java.io.File("output").mkdirs();

        this.connection = DriverManager.getConnection(
                url,
                user,
                password
        );
    }

    public static DatabaseConnection getInstance()
            throws IOException, SQLException {

        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}